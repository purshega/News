package com.dtek.portal.api;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.dtek.portal.R;
import com.dtek.portal.models.qr.QrResp;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.fragment.itsm.CreateItsmFragment;
import com.dtek.portal.ui.fragment.itsm.room.CreateRoomFragment;
import com.dtek.portal.ui.fragment.qr.QrScanFragment;
import com.dtek.portal.utils.PreferenceUtils;
import com.dtek.portal.utils.QrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dtek.portal.Const.HTTP.API_AUTHORITY;
import static com.dtek.portal.Const.HTTP.API_QR_SERVICE;

public class QrRequest {

    public static final String ITSM = "ITSM";
    public static final String AHO = "AHO";

    private ZXingScannerView mScannerView;
    private QrScanFragment qrScanFragment;
    private Context context;

    public QrRequest(Context context) {
        this.context = context;
    }

    public QrRequest(Context context, ZXingScannerView mScannerView, QrScanFragment qrScanFragment) {
        this.context = context;
        this.mScannerView = mScannerView;
        this.qrScanFragment = qrScanFragment;
    }

    public void getActionByQrCode(String codeContent, Dialog waitDialog) {
        String codeContentForRequest = QrUtils.getCodeFromParse(codeContent);
        if (codeContentForRequest.equals(""))
            resetQr();
        else
            getActionFromServer(codeContentForRequest, waitDialog);
    }

    private void resetQr() {
        QrUtils.wrongQqCode(context);
        if (mScannerView != null) {
            mScannerView.setResultHandler(qrScanFragment);
            mScannerView.startCamera();
        }
        else
            ((MainActivity) Objects.requireNonNull(context)).showMenu();
    }


    private void getActionFromServer(String codeContent, Dialog waitDialog) {
        waitDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(API_AUTHORITY, PreferenceUtils.getToken());

        RestManager.getApi()
                .getQR(map, API_QR_SERVICE + "/" + codeContent)
                .enqueue(new Callback<QrResp>() {
                    @Override
                    public void onResponse(@NonNull Call<QrResp> call, @NonNull Response<QrResp> response) {
                        RestManager.printResponseLog(response);
                        switch (response.code()) {
                            case 200:
                                if (response.isSuccessful() && response.body() != null) {
                                    try {
                                        QrResp qrResp = response.body();
                                        if (qrResp.getStatus())
                                            initNextService(qrResp);
                                        else resetQr();
                                    } catch (NullPointerException ignored) {
                                    }
                                }
                                break;
                            case 400:
                            case 401:
                                ((MainActivity) Objects.requireNonNull(context)).logout();
                                break;
                        }
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call<QrResp> call, Throwable t) {
                        t.printStackTrace();
                        waitDialog.dismiss();
                    }
                });
    }

    private void initNextService(QrResp qrResp) {
        switch (qrResp.getService()) {
            case ITSM:
                ((MainActivity) Objects.requireNonNull(context)).mToolbarTitle.setText(context.getResources().getString(R.string.title_it_service));
                ((MainActivity) Objects.requireNonNull(context)).switchToFragment(CreateItsmFragment.newInstance(qrResp), true);
                break;
            case AHO:
                ((MainActivity) Objects.requireNonNull(context)).mToolbarTitle.setText(context.getResources().getString(R.string.title_room_service));
                ((MainActivity) Objects.requireNonNull(context)).switchToFragment(CreateRoomFragment.newInstance(qrResp), true);
                break;
        }
    }


}
