package com.dtek.portal.ui.fragment.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.gallery.Folder;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.gallery.adapter.GalleryAdapter;
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

public class GalleryGridFragment extends Fragment implements GalleryAdapter.FolderListener {


    Unbinder unbinder;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmptyList;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_progress)
    ProgressBar mProgress;
    List<Folder> folderList;


    public static GalleryGridFragment newInstance() {
        GalleryGridFragment fragment = new GalleryGridFragment();
        return fragment;
    }

    public GalleryGridFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RestManager.getApi()
                .loadFolder(getHeaderMap())
                .enqueue(new Callback<List<Folder>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Folder>> call, @NonNull Response<List<Folder>> response) {
                        if(mProgress!=null)
                        mProgress.setVisibility(View.GONE);
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            folderList = response.body();
                            if (folderList.isEmpty()) {
                                mTvEmptyList.setVisibility(View.VISIBLE);
                            } else {
                                setItemsToList();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Folder>> call, @NonNull Throwable t) {
                        if(mProgress!=null)
                        mProgress.setVisibility(View.GONE);
                    }
                });
    }

    private void setItemsToList() {
        if(mRecyclerView!=null) {
            // используем linear layout manager
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            // создаем адаптер
            GalleryAdapter adapter = new GalleryAdapter(folderList);
            adapter.setFolderListener(this); // слушатель интерфейса
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemViewCacheSize(100);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(Folder currentFolder) {
//        Toast.makeText(getContext(), currentFolder.getId(), Toast.LENGTH_SHORT).show();
        ((MainActivity) getActivity()).switchToFragment(PhotoGridFragment.newInstance(currentFolder.getId()), false);
//        Intent intent = new Intent(getActivity(), GalleryActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_photo));
        }
    }
}
