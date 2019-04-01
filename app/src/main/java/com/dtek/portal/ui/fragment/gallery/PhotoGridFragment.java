package com.dtek.portal.ui.fragment.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.ui.activity.GalleryActivity;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.gallery.adapter.PhotoAdapter;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;

public class PhotoGridFragment extends Fragment implements PhotoAdapter.PhotoListener {

    private static final String ARG_PHOTOS = "data_photos";

    private Call call;


    Unbinder unbinder;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmptyList;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_progress)
    ProgressBar mProgress;
    private List<Photo> photoList;
    private String id;


    public static PhotoGridFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(ARG_PHOTOS, id);

        PhotoGridFragment fragment = new PhotoGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoGridFragment() {
        // Required empty public constructor
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString(ARG_PHOTOS);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_list, container, false);
        unbinder = ButterKnife.bind(this, v);

        getData(v);
        initSwipe(v);
        return v;
    }

    private void initSwipe(View v) {
        mSwipeRefresh.setOnRefreshListener(() -> {
            // перезагруза контента(фото), при этом очищается кэш фото
            getData(v);
            mSwipeRefresh.setRefreshing(false);
        });
    }

    private void getData(View v) {
        if (Utils.isNetworkAvailable(getContext())) {
            loadFolder();
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    @NonNull
    protected Map<String, String> getHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        return map;
    }

    private void loadFolder() {

        mProgress.setVisibility(View.VISIBLE);
        call =
                RestManager.getApi()
                .loadPhoto(getHeaderMap(), id);
                call.enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Photo>> call, @NonNull Response<List<Photo>> response) {
                        mProgress.setVisibility(View.GONE);
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            photoList = response.body();
                            if (photoList.isEmpty()) {
                                mTvEmptyList.setVisibility(View.VISIBLE);
                            } else {
                                setItemsToList();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Photo>> call, @NonNull Throwable t) {
                        if(mProgress!=null)
                        mProgress.setVisibility(View.GONE);
                    }
                });
    }


    private void setItemsToList() {
        // используем linear layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        // создаем адаптер
        PhotoAdapter adapter = new PhotoAdapter(photoList);
        adapter.setPhotoListener(this); // слушатель интерфейса
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemViewCacheSize(100);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if(call!=null)
        call.cancel();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton();
        }
    }

    @Override
//    public void onItemClick(Photo currentPhoto) {
    public void onItemClick(int position) {
//        Toast.makeText(getContext(), "позиция: " + position, Toast.LENGTH_SHORT).show();

        startActivity(GalleryActivity.createIntent(getActivity(), position, photoList));
    }
}
