package com.dtek.portal.ui.fragment.newspaper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.newspaper.Newspaper;
import com.dtek.portal.ui.activity.NewsPaperActivity;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.NewspaperUtils;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;


public class MainNewsPaperFragment extends Fragment {

    private static final String ARG_NEWSPAPER = "newspaper";

    private Dialog waitDialog;
    private Context mContext;

    private Unbinder unbinder;

    private Newspaper newspaper;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.newspaper_null)
    TextView newspaper_null;

    public MainNewsPaperFragment() {
    }

    public static MainNewsPaperFragment newInstance(Newspaper newspaper) {
        if(newspaper==null)
            return null;
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWSPAPER, newspaper);
        MainNewsPaperFragment fragment = new MainNewsPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        initDialog();
        waitDialog.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newspaper, container, false);
        unbinder = ButterKnife.bind(this, v);

        if (getArguments() != null) {
            newspaper = getArguments().getParcelable(ARG_NEWSPAPER);
            loadFirstPageFromNewspaper(newspaper);
        } else {
            imageView.setVisibility(View.GONE);
            newspaper_null.setVisibility(View.VISIBLE);
            waitDialog.dismiss();
        }
        return v;
    }


    private void loadFirstPageFromNewspaper(Newspaper newspaper){
        waitDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        RestManager.getApi()
                .getImgForFirstPageNewspaper(map,API_BASE_URL + NEWS_BASE64 + newspaper.getPic_url())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        String strBase64 = response.body();
                        if (response.isSuccessful() && response.body() != null) {
                            byte[] imageBytes = Utils.getBytesFromBase64(strBase64);
                            Bitmap bitmap = Utils.getBitmapFromByte(imageBytes);
                            imageView.setImageBitmap(bitmap);
                        }
                        if(waitDialog!=null)
                            waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        if(waitDialog!=null)
                            waitDialog.dismiss();
                    }
                });
    }

    @OnClick (R.id.imageView)
    public void onItemClick() {
        if(newspaper!=null) {
            NewspaperUtils.deletePdfFileInStorage(mContext, newspaper.getNewspaper_name()+".pdf");
            Intent intent = new Intent(getActivity(), NewsPaperActivity.class);
            intent.putExtra(Const.HTTP.NEWSPAPER_URL, newspaper.getNewspaper_url());
            intent.putExtra(Const.PDF_NEWSPAPER.NEWSPAPER_NAME, newspaper.getNewspaper_name()+".pdf");
            getActivity().startActivity(intent);
        }
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(mContext);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
