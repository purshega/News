package com.dtek.portal.ui.fragment.newspaper;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.newspaper.Newspaper;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_NEWSPAPER;


public class TabNewsPaperFragment extends Fragment {

    public static final int TAB_RUS= 0;
    public static final int TAB_UKR = 1;

    private Unbinder unbinder;

    private Dialog waitDialog;
    private Context mContext;

    @BindView(R.id.pager_newspaper)
    ViewPager mViewPager;
    @BindView(R.id.tab_newspaper_layout)
    TabLayout tabLayout;
    @BindView(R.id.btn_archive)
    Button btn_archive;
    private int currentTab;

    public TabNewsPaperFragment() {
    }


    @SuppressLint("ValidFragment")
    public TabNewsPaperFragment(int currentTab) {
        this.currentTab = currentTab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
//        setRetainInstance(true); // сохранение состояния фрагмента ###
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newspaper_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Set up the ViewPager with the sections adapter.
        tabLayout.setupWithViewPager(mViewPager);
        mContext = getContext();
        initDialog(); // диалоги
        loadNewspaper();
        return view;
    }

    private void setupViewPager(List <Newspaper> list) {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        boolean newspaper_rus_is_download = false;
        boolean newspaper_ukr_is_download = false;


        for(Newspaper newspaper: list){
            if(newspaper.getLanguage().equals(Const.PDF_NEWSPAPER.RUS_LANGUAGE)) {
                pagerAdapter.addFragment(MainNewsPaperFragment.newInstance(newspaper), getString(R.string.title_rus));
                newspaper_rus_is_download = true;
                break;
            }
        }

        for(Newspaper newspaper: list){
            if(newspaper.getLanguage().equals(Const.PDF_NEWSPAPER.UKR_LANGUAGE)) {
                pagerAdapter.addFragment(MainNewsPaperFragment.newInstance(newspaper), getString(R.string.title_ukr));
                newspaper_ukr_is_download = true;
                break;
            }
        }

        if(!newspaper_rus_is_download)
            pagerAdapter.addFragment(new MainNewsPaperFragment(), getString(R.string.title_rus));
        if(!newspaper_ukr_is_download)
            pagerAdapter.addFragment(new MainNewsPaperFragment(), getString(R.string.title_ukr));

        mViewPager.setOffscreenPageLimit(2);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(currentTab); // запуск списка по умолчанию
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_newspaper));
        }
    }

    private void loadNewspaper(){
        waitDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getNewspaperList(map,API_NEWSPAPER)
                .enqueue(new Callback<List<Newspaper>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Newspaper>> call, @NonNull Response<List<Newspaper>> response) {
                        switch (response.code()) {
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                                break;
                        }

                        if (response.isSuccessful() && response.body() != null) {
                            List <Newspaper> list = response.body();
                            setupViewPager(list);
                        } else {
                        }
                        if(waitDialog!=null)
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Newspaper>> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(mContext);
    }


    @OnClick(R.id.btn_archive)
    public void onViewClicked() {
        ((MainActivity) getActivity()).switchToFragment(new TabNewsPaperArchiveFragment(), false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
