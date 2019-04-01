package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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

import butterknife.Unbinder;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.*;
import static com.dtek.portal.ui.activity.MainActivity.REQUEST_CODE_LOGIN;

public abstract class Base2LineFragment extends Fragment {

    protected Unbinder unbinder;
    protected Context mContext;
    protected ProgressDialog waitDialog;
    protected String loginUser = PreferenceUtils.getLogin();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //явно указываем FragmentManager что есть OptionsMenu
        mContext = getContext();
        initDialog();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideKeyboard();
    }

    public void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager)
                Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getView())
                .getWindowToken(), 0);
    }

    public void initDialog() {
        waitDialog = WaitDialog.showDialog(mContext, getString(R.string.dialog_wait));
    }

    @NonNull
    public Map<String, String> mapHeader(String url, String fileReq, String fileResp, String contentType) {
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());
        if (url != null) {
            map.put(ITSM_KEY_URL, url);
        }
        if (fileReq != null) {
            map.put(ITSM_KEY_REQUEST, fileReq);
        }
        if (fileResp != null) {
            map.put(ITSM_KEY_RESPONSE, fileResp);
        }
        if (contentType != null) {
            map.put(ITSM_KEY_CONTENT_TYPE, contentType);
        }
        return map;
    }

    public void restError(@NonNull Response<?> response) {
        RestManager.printResponseLog(response);
        switch (response.code()) {
            case 400:
            case 401:
                Toast.makeText(mContext, response.code()+"", Toast.LENGTH_LONG).show();
                ((MainActivity) Objects.requireNonNull(getActivity())).logout();
                break;
            case 500:
                Toast.makeText(mContext, response.code()+"", Toast.LENGTH_LONG).show();
                new MyDialog(response.message()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                break;
            case 502:
                Toast.makeText(mContext, response.code()+"", Toast.LENGTH_LONG).show();
                new MyDialog(response.message()).show(Objects.requireNonNull(getFragmentManager()), "fragmentDialog");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
