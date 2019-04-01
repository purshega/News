package com.dtek.portal.ui.fragment.qr;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtek.portal.R;
import com.dtek.portal.api.QrRequest;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.ui.dialog.WaitDialog;
import com.dtek.portal.utils.Utils;
import com.google.zxing.Result;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScanFragment extends Fragment implements ZXingScannerView.ResultHandler  {
    public static final String ARG_QR_DATA = "qr_data";
    private static final int CAMERA_REQUEST_CODE = 100;

    private Context mContext;
    private ZXingScannerView mScannerView;

    public QrScanFragment() {
    }

    public static QrScanFragment newInstance() {
        return new QrScanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        checkPermission();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(mContext , Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    @Override
    public void handleResult(Result result) {
        if (Utils.isNetworkAvailable(mContext.getApplicationContext())) {
            QrRequest qrRequest = new QrRequest(mContext, mScannerView, this);
            qrRequest.getActionByQrCode(result.getText(), WaitDialog.setDialog(mContext));
        } else {
            Toast.makeText(mContext.getApplicationContext(), R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show();
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showBurgerButton();
            ((MainActivity) Objects.requireNonNull(getActivity())).mToolbarTitle.setText(getString(R.string.title_qr));
        }
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show();
                onResume();
            } else {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }}
}
