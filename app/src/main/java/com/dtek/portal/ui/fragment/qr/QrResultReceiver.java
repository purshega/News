package com.dtek.portal.ui.fragment.qr;

public interface QrResultReceiver {
    /**
     * function to receive scanresult
     * @param codeFormat format of the barcode scanned
     * @param codeContent data of the barcode scanned
     */
    public void scanResultData(String codeFormat, String codeContent);

    public void scanResultData(NoScanResultException noScanData);
}
