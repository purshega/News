package com.dtek.portal.utils;


import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

public class LockTest {

    /**
     * <p>Checks to see if the lock screen is set up with either a PIN / PASS / PATTERN</p>
     * <p>
     * <p>For Api 16+</p>
     *
     * @return true if PIN, PASS or PATTERN set, false otherwise.
     */
//    public static boolean doesDeviceHaveSecuritySetup(Context context) {
//        return isPatternSet(context) || isPassOrPinSet(context);
//    }

    public static boolean isDeviceScreenLocked(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return isDeviceLocked(context);
        } else {
            return isPatternSet(context) || isPassOrPinSet(context);
        }
    }

    /**
     * @param context
     * @return true if pattern set, false if not (or if an issue when checking)
     */
    private static boolean isPatternSet(Context context) {
        ContentResolver cr = context.getContentResolver();
        try {
            int lockPatternEnable = Settings.Secure.getInt(cr, Settings.Secure.LOCK_PATTERN_ENABLED);
            return lockPatternEnable == 1;
        } catch (Settings.SettingNotFoundException e) {
//            Log.d("tag", e.getMessage());
            return false;
        }
    }

    /**
     * @param context
     * @return true if pass or pin set
     */
    private static boolean isPassOrPinSet(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE); //api 16+
        return keyguardManager.isKeyguardSecure();
    }

    /**
     * @return true if pass or pin or pattern loacks screen
     */
    @TargetApi(23)
    private static boolean isDeviceLocked(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE); //api 23+
        return keyguardManager.isDeviceSecure();
    }
}
