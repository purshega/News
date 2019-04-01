package com.dtek.portal.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.callback.NewsFetchListener;
import com.dtek.portal.callback.PaginationAdapterCallback;
import com.dtek.portal.callback.PaginationScrollListener;
import com.dtek.portal.database.DatabaseHelper;
import com.dtek.portal.database.RemoveTableDatabase;
import com.dtek.portal.database.SaveIntoDatabase;
import com.dtek.portal.models.news.News;
import com.dtek.portal.models.news.NewsList;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.news.NewsNoMissAdapter;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.DATABASE.TABLE_NAME_NEWS_NO_MISS;
import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_NEWS;
import static com.dtek.portal.Const.HTTP.NEWS_CAT_NO_MISS;


public class NewsNoMissListFragment extends Fragment
        implements PaginationAdapterCallback, NewsNoMissAdapter.PostListener, NewsFetchListener {

    private static final String TAG = "NewsNoMissListFragment";
    private static final int PAGE_START = 1;

    private RecyclerView rv;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private Button btnRetry;
    private TextView txtError;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsNoMissAdapter adapter;
    private DatabaseHelper mDatabase;

    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    private NewsList newsList;

    public NewsNoMissListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);

        mSwipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        rv = v.findViewById(R.id.main_recycler);
        progressBar = v.findViewById(R.id.main_progress);
        errorLayout = v.findViewById(R.id.error_layout);
        btnRetry = v.findViewById(R.id.error_btn_retry);
        txtError = v.findViewById(R.id.error_txt_cause);

        adapter = new NewsNoMissAdapter(this);
        adapter.setPostListener(this);

//        loadNewsFeed();
        getFeedFromDatabase();

        if (rv.getAdapter().getItemCount() == 0) {
            loadNewsFeed();
        }

        btnRetry.setOnClickListener(view -> loadNewsFeed());

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = PAGE_START;
            loadNewsFeed();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return v;
    }

    private void loadNewsFeed() {
        adapter.reset();
        progressBar.setVisibility(View.VISIBLE);

        if (Utils.isNetworkAvailable(getContext())) {
            loadFirstPage();
        } else {
            Toast.makeText(getContext(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            getFeedFromDatabase();
        }
    }

    public void getFeedFromDatabase() {
        mDatabase.fetchPosts(this, TABLE_NAME_NEWS_NO_MISS);
        setItemsToList();
    }

    private void setItemsToList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);

        //анимация
//        LayoutAnimationController controller = AnimationUtils
//                .loadLayoutAnimation(getActivity(), R.anim.list_layout_controller);
//        rv.setLayoutAnimation(controller);
//        rv.setItemAnimator(new DefaultItemAnimator());

//        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(50);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (newsList != null && newsList.getNextPage()) {
                    isLoading = true;
                    currentPage += 1;
                    loadNextPage();
                }
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: " + currentPage);
//        hideErrorView();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getListNews(map,API_NEWS + String.valueOf(currentPage) + NEWS_CAT_NO_MISS)
                .enqueue(new Callback<NewsList>() {
                    @Override
                    public void onResponse(@NonNull Call<NewsList> call, @NonNull Response<NewsList> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful()) {

                            RemoveTableDatabase taskDel = new RemoveTableDatabase(mDatabase);
                            taskDel.execute(TABLE_NAME_NEWS_NO_MISS);

                            newsList = response.body();
                            List<News> newses = null;
                            if (response.code() == 200 && newsList != null) {
                                newses = newsList.getNewses();
                                for (News news : newses) {
                                    news.setCategory(NEWS_CAT_NO_MISS);

                                    SaveIntoDatabase task = new SaveIntoDatabase(mDatabase, TABLE_NAME_NEWS_NO_MISS);
                                    task.execute(news);

                                    adapter.addNews(news);
//                                    adapter.addAll(newses);
                                }
                                if (newsList.getNextPage()) {
                                    adapter.addLoadingFooter();
                                }
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        setItemsToList();
                    }

                    @Override
                    public void onFailure(@NonNull Call<NewsList> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void loadNextPage() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getListNews(map,API_NEWS + String.valueOf(currentPage) + NEWS_CAT_NO_MISS)
                .enqueue(new Callback<NewsList>() {
                    @Override
                    public void onResponse(@NonNull Call<NewsList> call, @NonNull Response<NewsList> response) {
                        RestManager.printResponseLog(response);
                        adapter.removeLoadingFooter();
                        isLoading = false;

                        newsList = response.body();
                        List<News> newses = null;
                        if (response.code() == 200 && newsList != null) {
                            newses = newsList.getNewses();
                            for (News news : newses) {
                                news.setCategory(NEWS_CAT_NO_MISS);

                                SaveIntoDatabase task = new SaveIntoDatabase(mDatabase, TABLE_NAME_NEWS_NO_MISS);
                                task.execute(news);

                                adapter.addNews(news);
                            }
                            if (newsList.getNextPage()) {
                                adapter.addLoadingFooter();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsList> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    @Override
    public void retryPageLoad() {
        loadNextPage();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_news));
        }
    }

    @Override
    public void onItemClick(News currentNews) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).addToFragment(NewsDetailsFragment.newInstance(currentNews));
        }
    }

    @Override
    public void onDeliverAllPosts(List<News> news) {

    }

    @Override
    public void onDeliverPost(News news) {
        adapter.addNews(news);
    }

    @Override
    public void onHideDialog() {
//        progressBar.setVisibility(View.GONE);
    }
}
