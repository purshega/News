package com.dtek.portal.ui.fragment.itsm.it;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.models.itsm.twoline.ItManager;
import com.dtek.portal.ui.activity.LoginActivity;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.fragment.itsm.TabItsmFragment;
import com.dtek.portal.ui.fragment.itsm.it.twoline.Tab2LineFragment;
import com.dtek.portal.utils.PreferenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.ITSM_FILE_MANAGER;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_RESPONSE;
import static com.dtek.portal.Const.HTTP.ITSM_KEY_URL;
import static com.dtek.portal.Const.HTTP.ITSM_URL_MANAGER;
import static com.dtek.portal.ui.activity.MainActivity.REQUEST_CODE_LOGIN;


@SuppressLint("ValidFragment")
public class TabItFragment extends TabItsmFragment {

    public TabItFragment() {
    }

    public TabItFragment(int currentTab) {
        super(currentTab);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) getActivity()).mToolbarTitle.setText(getString(R.string.title_it_service));
        }
    }

    @NonNull
    public Map<String, String> mapHeader(String url, String fileResp) {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        map.put(ITSM_KEY_URL, url);
        map.put(ITSM_KEY_RESPONSE, fileResp);
        return map;
    }

    private void loadIsTwoLine(Menu menu) {
        RestManager.getApi()
                .loadItStringJson(mapHeader(ITSM_URL_MANAGER + PreferenceUtils.getLogin(), ITSM_FILE_MANAGER))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                                break;
                            case 500:
                                new MyDialog(response.message()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                                break;
                        }
                        if (response.isSuccessful() && response.body() != null) {
                            ItManager root = new Gson().fromJson(response.body(), ItManager.class);
                            if (root != null) {
                                ItManager.Manager manager = root.getRoot();
                                PreferenceUtils.saveLoginId(manager.getPersonId());
                                if (manager.isManager()) {
                                    try {
                                        menu.findItem(R.id.menu_2line).setVisible(true);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        new MyDialog(t.getMessage()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_itsm_2line, menu);
        loadIsTwoLine(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_2line:
                ((MainActivity) Objects.requireNonNull(getActivity()))
                        .switchToFragment(new Tab2LineFragment(Tab2LineFragment.TAB_NEW), false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
