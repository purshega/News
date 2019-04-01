package com.dtek.portal;


import android.app.Application;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

public class App extends Application {

//    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
//        sContext = this;

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);

        //SharedPref
        Hawk.init(this).build();

    }

    //    @NonNull
//    public static Context getContext() {
//        return sContext;
//    }

//    public static File getCacheDirectory() {
//        return getContext().getCacheDir();
//    }
}
