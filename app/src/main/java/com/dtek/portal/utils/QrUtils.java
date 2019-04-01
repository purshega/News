package com.dtek.portal.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.dtek.portal.Const;

public class QrUtils {

    public static String getCodeFromParse(String codeContentFormQrPicture ) {
        String result = "";
        if(codeContentFormQrPicture.length()>Const.QR.QR_LINK.length()) {
            String validation = codeContentFormQrPicture.substring(0, Const.QR.QR_LINK.length());
            if(validation.equals(Const.QR.QR_LINK)){
                Uri uri = Uri.parse(codeContentFormQrPicture);
                if(uri.getQueryParameter("qr")!=null) {
                    String form_url_result = uri.getQueryParameter("qr");
                    for (int i = 0; i < form_url_result.length(); i++) {
                        if (Character.toString(form_url_result.charAt(i)).matches("[0-9]+"))
                            result = result + form_url_result.charAt(i);
                        else return result;
                    }
                }
            }
        }
        return result;
    }

    public static void wrongQqCode(Context context){
//        Toast.makeText(context,"QR код не подходит к этому приложению", Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Не корректный QR код", Toast.LENGTH_SHORT).show();
    }
}
