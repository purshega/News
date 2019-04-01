package com.dtek.portal.ui.fragment.news;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.news.News;
import com.dtek.portal.models.news.NewsComment;
import com.dtek.portal.models.news.NewsID;
import com.dtek.portal.ui.activity.LoginActivity;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.news.NewsCommentAdapter;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_NEWS;
import static com.dtek.portal.Const.HTTP.NEWS_BASE64;
import static com.dtek.portal.Const.HTTP.NEWS_LIKE;

public class NewsDetailsFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "NewsDetailsFragment";
    private static final String ARG_NEWS_ID = "news";
    public static final int REQUEST_CODE = 100;

    private Context mContext;
    //    private ImageView ivPhoto;
    private TextView tvTitle, tvDate, tvFulltext, tvLikes, tvComments;
    private LinearLayout llSocial;
    private LinearLayout llLogin;
    private ToggleButton mIvLike;
    private ImageView mIvSend;
    private EditText mEtComment;
    private News news;
    private NewsID mNewsID;
    private List<NewsComment> mCommentsList;
    private RecyclerView mRvComments;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsCommentAdapter mCommentAdapter;

    public static NewsDetailsFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS_ID, news);

        NewsDetailsFragment fragment = new NewsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu

        news = new News(Parcel.obtain());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            news = bundle.getParcelable(ARG_NEWS_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);

        initViews(v);

        if (Utils.isNetworkAvailable(getContext())) {
            loadNews();
            if (!PreferenceUtils.getToken().equals("")) {
                loadComments();
            }
        } else {
            Toast.makeText(getActivity(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private void initViews(View v) {
//        ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);
        tvTitle = v.findViewById(R.id.tvTitle);
        tvDate = v.findViewById(R.id.tvDate);
        tvFulltext = v.findViewById(R.id.tvFulltext);
        tvLikes = v.findViewById(R.id.tv_likes);
        tvComments = v.findViewById(R.id.tv_comments);
        llSocial = v.findViewById(R.id.llSocial);
        llLogin = v.findViewById(R.id.llLogin);
        mRvComments = v.findViewById(R.id.rv_comments);
        mEtComment = v.findViewById(R.id.et_comment);
        mIvLike = v.findViewById(R.id.iv_likes);
        mIvSend = v.findViewById(R.id.iv_send_comment);

        llLogin.setOnClickListener(this);
        mIvLike.setOnClickListener(this);
        mIvSend.setOnClickListener(this);
    }

    private void loadNews() {
//        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getIdNews(map, API_NEWS + String.valueOf(news.getId()))
                .enqueue(new Callback<NewsID>() {
                    @Override
                    public void onResponse(@NonNull Call<NewsID> call, @NonNull Response<NewsID> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            mNewsID = response.body();
                            setNewsContent();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NewsID> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void loadComments() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getListComment(map, API_NEWS + String.valueOf(news.getId()))
                .enqueue(new Callback<List<NewsComment>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<NewsComment>> call, @NonNull Response<List<NewsComment>> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                                break;
                        }
                        if (response.isSuccessful() && response.body() != null) {
                            mCommentsList = response.body();
                            setCommentsToList();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<NewsComment>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private void sendComments() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        NewsComment comment = new NewsComment();
        comment.setPostId(news.getId());
        comment.setBody(mEtComment.getText().toString());

        RestManager.getApi()
                .sendComment(map, comment)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                                break;
                        }
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().equals("true")) {
                                loadComments();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private void sendLike() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .sendLike(map, API_NEWS + NEWS_LIKE + String.valueOf(news.getId()))
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                                break;
                        }
//                        if (response.isSuccessful() && response.body() != null) {
//                                mIvLike.setImageResource(R.drawable.ic_likes);
//                                tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString()) + 1));
//                            }else{
//                                mIvLike.setImageResource(R.drawable.ic_dislikes);
//                                tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString()) - 1));
//                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private void setNewsContent() {
        tvTitle.setText(news.getTitle());
        tvDate.setText(news.getPublishedDate());

        String html = mNewsID.getBody();
        Spanned spanned = Html.fromHtml(html, imgGetter, null);
        tvFulltext.setText(spanned);

        tvLikes.setText(String.valueOf(mNewsID.getLikesCount()));
        tvComments.setText(String.valueOf(mNewsID.getNumComments()));

        if (PreferenceUtils.getToken().equals("")) {
            llLogin.setVisibility(View.VISIBLE);
            llSocial.setVisibility(View.GONE);
        } else {
            llLogin.setVisibility(View.GONE);
            llSocial.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLogin:
                Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                intentLogin.putExtra("back", true);
                startActivityForResult(intentLogin, REQUEST_CODE);
                break;
            case R.id.iv_likes:
                if (Utils.isNetworkAvailable(getContext())) {
                    sendLike();
                    if (mIvLike.isChecked()) {
                        mIvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_likes));
                        tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString()) + 1));
                    } else {
                        mIvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_dislikes));
                        tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString()) - 1));
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_send_comment:
                if (Utils.isNetworkAvailable(getContext())) {
                    sendComments();
                    mEtComment.setText("");
                } else {
                    Toast.makeText(getActivity(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setCommentsToList() {
        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvComments.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mCommentAdapter = new NewsCommentAdapter(mCommentsList);
        mRvComments.setAdapter(mCommentAdapter);
    }

    private Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable mDrawable = new LevelListDrawable();
            if (source != null && !source.equals("")) {
                loadImage(source, mDrawable);
            }
            return mDrawable;
        }
    };

    public void loadImage(String source, LevelListDrawable mDrawable) {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getImageHtml(map, NEWS_BASE64 + source + "&maxwidth=" + tvFulltext.getWidth())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        RestManager.printResponseLog(response);
                        if (response.isSuccessful() && response.body() != null) {
                            String imageAsBase64 = response.body();
                            Bitmap bitmap = Utils.getBitmapFromByte(Utils.getBytesFromBase64(imageAsBase64));

                            if (bitmap != null) {
                                BitmapDrawable d = new BitmapDrawable(mContext.getResources(), bitmap);
                                mDrawable.addLevel(1, 1, d);

                                int width = tvFulltext.getWidth();
                                int height = bitmap.getHeight() * width / bitmap.getWidth();
                                mDrawable.setBounds(0, 0, width, height);
                                mDrawable.setLevel(1);

                                CharSequence t = tvFulltext.getText();
                                tvFulltext.setText(t);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (PreferenceUtils.getToken().equals("")) {
                llLogin.setVisibility(View.VISIBLE);
                llSocial.setVisibility(View.GONE);
            } else {
                llLogin.setVisibility(View.GONE);
                llSocial.setVisibility(View.VISIBLE);
                loadComments();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
        }
    }
}
