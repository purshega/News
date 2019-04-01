package com.dtek.portal.ui.adapter.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtek.portal.R;
import com.dtek.portal.callback.PaginationAdapterCallback;
import com.dtek.portal.models.news.News;
import com.dtek.portal.utils.PicassoSingleton;
import com.dtek.portal.utils.Utils;
import com.squareup.picasso.MemoryPolicy;

import java.util.ArrayList;
import java.util.List;

import static com.dtek.portal.Const.HTTP.API_BASE_URL;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;
import static com.dtek.portal.Const.HTTP.NEWS_PARAM_IMAGE;


public class BaseNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "BaseNewsAdapter";

    private static final int TYPE_ITEM = 2;
    private static final int TYPE_LOADING = 3;

    private List<News> mNewsList;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private PaginationAdapterCallback mCallback;

    public BaseNewsAdapter(PaginationAdapterCallback callback) {
        this.mCallback = callback;
        mNewsList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mNewsList.size() - 1 && isLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.i(TAG, "onCreateViewHolder: ");

        switch (viewType) {
            case TYPE_ITEM:
                View viewItem = inflater.inflate(getLayoutItem(), parent, false);
                viewHolder = new ItemVH(viewItem);
                break;
            case TYPE_LOADING:
                View viewLoading = inflater.inflate(R.layout.item_news_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    protected int getLayoutItem() {
        return R.layout.item_news_dtek_list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                final News currentNews = mNewsList.get(position); // News
                ItemVH itemVH = (ItemVH) holder;

                itemVH.mTitle.setText(currentNews.getTitle());
                itemVH.mDate.setText(currentNews.getPublishedDate());

                // load news thumbnail
                if (currentNews.getPicture() != null) {
                    itemVH.mPoster.setImageBitmap(currentNews.getPicture());
                } else {
                    if (currentNews.getImageLink() != null && !currentNews.getImageLink().equals("") && Utils.isNetworkAvailable(holder.itemView.getContext())) {
                        loadImageFromInet(currentNews, itemVH);
                    } else {
                        itemVH.mPoster.setImageResource(R.drawable.img_no_photo);
                    }
                }

                holder.itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onItemClick(currentNews);
                    }
                });

                break;
            case TYPE_LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                    loadingVH.mErrorTxt.setText(errorMsg != null ?
                            errorMsg : loadingVH.itemView.getResources().getString(R.string.error_msg_unknown));
                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void loadImageFromInet(News currentNews, ItemVH itemVH) {
        PicassoSingleton.getInstance(itemVH.mPoster.getContext())
                .load(API_BASE_URL + NEWS_BASE64 + currentNews.getImageLink() + NEWS_PARAM_IMAGE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit().centerCrop()
                .error(R.drawable.img_no_photo)
                .into(itemVH.mPoster);
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

    public void addNews(News news) {
        mNewsList.add(news);
        notifyDataSetChanged();
    }

    public void reset() {
        mNewsList.clear();
        notifyDataSetChanged();
    }


    public void add(News news) {
        mNewsList.add(news);
        notifyItemInserted(mNewsList.size() - 1);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new News());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mNewsList.size() - 1;
        News item = getSelectedItem(position);
        if (item != null) {
            mNewsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private News getSelectedItem(int position) {
        News news = null;
        if (!mNewsList.isEmpty()) {
            news = mNewsList.get(position);
        }
        return news;
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mNewsList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    protected class ItemVH extends RecyclerView.ViewHolder {
        private ImageView mPoster;
        private ProgressBar mProgress;
        private TextView mTitle;
        //        private TextView mDesc;
        private TextView mDate;

        public ItemVH(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDate = itemView.findViewById(R.id.tv_date);
            mPoster = itemView.findViewById(R.id.iv_poster);
            mProgress = itemView.findViewById(R.id.pb_progress);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }

    //интерфейс
    private PostListener listener; //переменная

    public void setPostListener(PostListener listener) {//кот. содержит в себе объект
        this.listener = listener;
    }

    public interface PostListener { //этот объект реализует этот интерфейс
        void onItemClick(News currentNews);
    }

}
