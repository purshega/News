package com.dtek.portal.ui.fragment.youtube;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.R;
import com.dtek.portal.models.youtube.VideoYouTube;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.youtube.adapter.YouTubeAdapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class YouTubeFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;

    private YouTubeAdapter youTubeAdapter;

    public YouTubeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_youtube, container, false);
        unbinder = ButterKnife.bind(this, v);
        initRecyclerView();
        loadVideos();
        return v;
    }

    public void initRecyclerView(){
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        youTubeAdapter = new YouTubeAdapter();
        videoRecyclerView.setAdapter(youTubeAdapter);
    }

    private void loadVideos(){
        Collection<VideoYouTube> videos = getVideos();
        youTubeAdapter.setVideos(videos);
    }

    private Collection <VideoYouTube> getVideos(){
        return Arrays.asList(
                new VideoYouTube ("aTUm1VMh8Fk","Инновационная энергетика ДТЭК. Упрощение процедуры подключения"),
                new VideoYouTube ("LMcQx3NXPPs","STRUM by DTEK"),
                new VideoYouTube ("D3PAK28QDQY","Yuliya Nosulko`s Blog - ep.1")
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) Objects.requireNonNull(getActivity())).mToolbarTitle.setText(getString(R.string.title_video));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
