package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.itsm.twoline.ItRootDetail;
import com.dtek.portal.models.itsm.twoline.ItRootList;
import com.dtek.portal.models.itsm.twoline.ItTaskList;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.it.ItTwoLineTaskAdapter;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.ITSM_FILE_DETAIL_INCIDENT;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_DETAIL_OPERATION;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_DETAIL_WORK;
import static com.dtek.portal.Const.HTTP.ITSM_URL_DETAIL_INCIDENT;
import static com.dtek.portal.Const.HTTP.ITSM_URL_DETAIL_OPERATION;
import static com.dtek.portal.Const.HTTP.ITSM_URL_DETAIL_WORK;

public abstract class ListTwoLineFragment extends Base2LineFragment
        implements ItTwoLineTaskAdapter.TaskClickListener {
    private static final String TAG = "ListTwoLineFragment";

    //    @BindView(R.id.et_address)
//    EditText mEtAddress;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_list)
    TextView mTvEmpty;
    private List<ItTaskList> mTaskList; // список заявок
    private ItTwoLineTaskAdapter mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View v = inflater.inflate(R.layout.layout_list_filter, container, false);
        final View v = inflater.inflate(R.layout.layout_list, container, false);
        unbinder = ButterKnife.bind(this, v);

        initAdapter();
        getData(v);
        initSwipe(v);
        return v;
    }

    private void initAdapter() { // создаем адаптер
        mAdapter = new ItTwoLineTaskAdapter(mTaskList, this);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemViewCacheSize(50);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSwipe(View v) {
        mSwipeRefresh.setOnRefreshListener(() -> {
            getData(v);
            mSwipeRefresh.setRefreshing(false);
        });
    }

    private void getData(View v) {
        if (Utils.isNetworkAvailable(mContext)) {
            loadData();
        } else {
            Snackbar.make(v, R.string.error_msg_no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvEmpty.setVisibility(View.GONE);

        RestManager.getApi()
                .loadItStringJson(getHeaders())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (ListTwoLineFragment.this.isResumed()) {
                            mProgressBar.setVisibility(View.GONE);
                            RestManager.printResponseLog(response);
                            if (response.isSuccessful() && response.body() != null) {
                                ItRootList rootList = new Gson().fromJson(response.body(), ItRootList.class);

                                if (rootList.getRoot() != null) {
                                    List<ItTaskList> taskList = rootList.getRoot().getOrdersList();
                                    taskList.subList(taskList.size() - 2, taskList.size()).clear(); // удаляем 2 последних элемента

                                    if (!taskList.isEmpty()) {
                                        setItemsToList(taskList); //результат положительного запроса
                                    } else {
                                        mTvEmpty.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        if (ListTwoLineFragment.this.isResumed()) {
                            mProgressBar.setVisibility(View.GONE);
                            new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                        }
                    }
                });
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return null;
    }

    private void setItemsToList(List<ItTaskList> items) {

        mTaskList.clear();
        mTaskList.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

//    @OnTextChanged(R.id.et_address)
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        mAdapter.getFilter().filter(s);
//    }
//
//    @OnClick(R.id.iv_clear_address)
//    void onButtonClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_clear_address:
//                mEtAddress.setText("");
//                break;
//        }
//    }

    @Override
    public void onItemClick(ItTaskList task) {
        if (Utils.isNetworkAvailable(mContext)) {
            loadTaskDetail(task);
        } else {
            Toast.makeText(mContext, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    public static final String WORK = "Рабочее задание";
    public static final String OPERATION = "Операция";
    public static final String INCIDENT = "Инцидент";

    @NonNull
    private String[] getLink(String task) {
        switch (task) {
            case WORK:
                return new String[]{ITSM_URL_DETAIL_WORK, ITSM_FILE_DETAIL_WORK};
            case OPERATION:
                return new String[]{ITSM_URL_DETAIL_OPERATION, ITSM_FILE_DETAIL_OPERATION};
            case INCIDENT:
                return new String[]{ITSM_URL_DETAIL_INCIDENT, ITSM_FILE_DETAIL_INCIDENT};
            default:
                return new String[]{ITSM_URL_DETAIL_WORK, ITSM_FILE_DETAIL_WORK};
        }
    }

    private void loadTaskDetail(ItTaskList task) {
        waitDialog.show();
        RestManager.getApi()
                .loadItStringJson(mapHeader(
                        getLink(task.getTypeNumber())[0] + task.getNumber(),
                        null,
                        getLink(task.getTypeNumber())[1],
                        null))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        waitDialog.dismiss();
                        restError(response);
                        if (response.isSuccessful() && response.body() != null) {
                            ItRootDetail taskRoot = new Gson().fromJson(response.body(), ItRootDetail.class);

                            if (taskRoot.getRoot() != null) {
                                ItRootDetail.TaskDetail taskDetail = taskRoot.getRoot();
                                ((MainActivity) Objects.requireNonNull(getActivity())).switchToFragment(DetailTwoLineFragment.newInstance(taskDetail), false);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        waitDialog.dismiss();
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_search, menu);
        initSearchView(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initSearchView(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getString(R.string.text_hint_filter_address));
//        searchView.setIconifiedByDefault(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setIconified(false);

        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.color_status_black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorGreyTransparent));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) { // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                return true;
            case R.id.menu_it_service:
                Objects.requireNonNull(getActivity()).onBackPressed(); // шаг назад
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
