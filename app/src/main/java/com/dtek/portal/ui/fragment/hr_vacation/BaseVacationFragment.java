package com.dtek.portal.ui.fragment.hr_vacation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.dtek.portal.App;
import com.dtek.portal.R;
import com.dtek.portal.api.RestManager;
import com.dtek.portal.ui.activity.LoginActivity;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.dialog.MyDialog;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.ui.activity.MainActivity.REQUEST_CODE_LOGIN;

public class BaseVacationFragment extends Fragment {

    protected Context mContext;
    protected ProgressDialog waitDialog;
    protected String loginUser = PreferenceUtils.getLogin();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        initDialog();
    }

    public void initDialog() {
        waitDialog = WaitDialog.showDialog(mContext, getString(R.string.dialog_wait));
    }

    @NonNull
    public Map<String, String> fillMapHeader() {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        return map;
    }

    public void restError(@NonNull Response<?> response) {
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
    }
}
