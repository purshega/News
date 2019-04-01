package com.dtek.portal.ui.fragment.gallery;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.ui.fragment.RetainFragment;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;

public class PhotoFragment extends Fragment {

    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final String ARG_PHOTO = "this_photo";
    public static final String IMAGE_BITMAP = "imageBitmap";
    Unbinder unbinder;
    @BindView(R.id.pv_image)
    PhotoView mImage;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    private Photo photo;
//    private byte[] imageBytes;
    private Bitmap bitmap;

    private LruCache<String, Bitmap> mMemoryCache;



    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(Photo photo) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        RetainFragment retainFragment = RetainFragment.findOrCreateRetainFragment(getFragmentManager());
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = retainFragment.mRetainedCache;
        if (mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                // Initialize cache here as usual
            };
            retainFragment.mRetainedCache = mMemoryCache;
        }

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        unbinder = ButterKnife.bind(this, v);

//        if (savedInstanceState != null) {
//            bitmap = savedInstanceState.getParcelable(IMAGE_BITMAP);
////            imageBytes = savedInstanceState.getByteArray(IMAGE_BITMAP);
////            Bitmap bitmap = Utils.getBitmapFromByte(imageBytes);
//            mImage.setImageBitmap(bitmap);
//            mIvDownload.setVisibility(View.VISIBLE);
//        } else {


            if (getArguments() != null) {
                photo = getArguments().getParcelable(ARG_PHOTO);
                Bitmap bitmap = mMemoryCache.get(photo.getUrl());
                if (bitmap!=null) {
                    mImage.setImageBitmap(bitmap);
                    mIvDownload.setVisibility(View.VISIBLE);
                }
                else
                    loadPhoto(photo);
            }
//        }
        return v;
    }


    private void loadPhoto(Photo photo) {
        mProgress.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .getImageHtml(map, API_BASE_URL + NEWS_BASE64 + photo.getUrl())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        String strBase64 = response.body();
                        // проверки на null связаны с переворотом экрана,
                        // решили не отменять call,
                        // это приводит к бесконечной загрузке фото с сервера
                        if(mProgress!=null)
                            mProgress.setVisibility(View.INVISIBLE);

                            byte[] imageBytes = Utils.getBytesFromBase64(strBase64);
                            bitmap = Utils.getBitmapFromByte(imageBytes);
                            mMemoryCache.put(photo.getUrl(),bitmap);
                            try {
                                mImage.setImageBitmap(bitmap);
                                mIvDownload.setVisibility(View.VISIBLE);
                            } catch (NullPointerException e){

                            }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        if(mProgress!=null)
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @OnClick(R.id.iv_download)
    public void onViewClicked() {
        if (isCheckPermission()) {
            saveImage(bitmap);
        } else {
            requestPermission();
        }
    }


    private String saveImage(Bitmap image) {
        mProgress.setVisibility(View.VISIBLE);
        String savedImagePath = null;

        String imageFileName = photo.getName() + ".jpg";
        String pathName = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        File storageDir = new File(pathName);
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Фото сохранено", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean isStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (isStoragePermission) {
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                        saveImage(bitmap);
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean isCheckPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(IMAGE_BITMAP, bitmap);
////        outState.putByteArray(IMAGE_BITMAP, imageBytes);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
