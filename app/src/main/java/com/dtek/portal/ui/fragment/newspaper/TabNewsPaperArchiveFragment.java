package com.dtek.portal.ui.fragment.newspaper;


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

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.newspaper.Newspaper;
import com.dtek.portal.ui.adapter.BasePagerAdapter;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.ArrayList;
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
import static com.dtek.portal.Const.HTTP.API_NEWSPAPER;
import static com.dtek.portal.Const.HTTP.NEWSPAPER_ARCHIVE;

public class TabNewsPaperArchiveFragment extends Fragment {

    @BindView(R.id.pager_newspaper_2)
    ViewPager mViewPager;
    @BindView(R.id.tab_newspaper_layout_2)
    TabLayout tabLayout;


    private Dialog waitDialog;
    private Context mContext;

    private Unbinder unbinder;

    public TabNewsPaperArchiveFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
//        setRetainInstance(true); // сохранение состояния фрагмента ###
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newspaper_archive_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        tabLayout.setupWithViewPager(mViewPager);

        mContext = getContext();
        initDialog(); // диалоги

        loadNewspaperArchiveList();

        return view;
    }

    private void initDialog() {
        waitDialog = WaitDialog.setDialog(mContext);
    }

    private void loadNewspaperArchiveList(){
        waitDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getNewspaperList(map,API_NEWSPAPER + NEWSPAPER_ARCHIVE)
                .enqueue(new Callback<List<Newspaper>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Newspaper>> call, @NonNull Response<List<Newspaper>> response) {
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

    private void setupViewPager (List<Newspaper> list){
        List <Newspaper> rus_list = new ArrayList<>();
        List <Newspaper> ukr_list = new ArrayList<>();


        for(Newspaper newspaper: list) {
            if (newspaper.getLanguage() != null && newspaper.getNewspaper_name()!=null && newspaper.getNewspaper_url()!=null) {
                if (newspaper.getLanguage().equals(getString(R.string.language_rus)))
                    rus_list.add(newspaper);
                if (newspaper.getLanguage().equals(getString(R.string.language_ukr)))
                    ukr_list.add(newspaper);
            }
        }

        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(ArchiveNewsPaperFragment.newInstance(rus_list), getString(R.string.title_rus));
        pagerAdapter.addFragment(ArchiveNewsPaperFragment.newInstance(ukr_list), getString(R.string.title_ukr));

        mViewPager.setOffscreenPageLimit(1);// загружаем по 2 экрана с каждой стороны, default=1, меньше нельзя
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0); // запуск списка по умолчанию

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
