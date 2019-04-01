package com.dtek.portal.utils;

import android.content.Context;

import com.dtek.portal.Const;

import java.io.File;

public class NewspaperUtils {

    public static void deletePdfFileInStorage(Context mContext, String news_paper_name){
        File[] files = mContext.getExternalFilesDir(Const.PDF_NEWSPAPER.FILE_NAME).listFiles();
        for(int i = 0; i<files.length;i++){
            if(!files[i].getName().equals(news_paper_name))
                files[i].delete();
        }
    }

}
