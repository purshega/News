package com.dtek.portal.ui.fragment.youtube.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.models.youtube.VideoYouTube;
import com.dtek.portal.ui.activity.YouTubePlayerFragmentActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.VideoViewHolder> {

    private List<VideoYouTube> videosList = new ArrayList<>();

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_youtube_video, parent, false);
        final VideoViewHolder holder = new VideoViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, YouTubePlayerFragmentActivity.class);
            intent.putExtra(Const.YOUTUBE.URL_VIDEO,videosList.get(holder.getAdapterPosition()).getVideo_url());
            context.startActivity(intent);
        });
        return holder;
    }

    public void setVideos(Collection<VideoYouTube> videos){
        videosList.addAll(videos);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int position) {
        videoViewHolder.bind(videosList.get(position));
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_title)
        TextView videoTitle;
        @BindView(R.id.iv_video)
        ImageView videoImegeView;
        @BindView(R.id.iv_play)
        ImageView videoImegePlay;


        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(VideoYouTube videoYouTube){
            videoTitle.setText(videoYouTube.getTitle());
            String url = "https://img.youtube.com/vi/"+videoYouTube.getVideo_url()+"/0.jpg";
            Picasso.with(videoImegeView.getContext()).load(url).into(videoImegeView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    videoImegePlay.setVisibility(View.VISIBLE);
                }
                @Override
                public void onError() {
                }
            });

        }
    }

}
