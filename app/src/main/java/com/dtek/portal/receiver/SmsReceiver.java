//package com.dtek.portal.receiver;
//
//
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.telephony.SmsMessage;
//
//public class SmsReceiver extends BroadcastReceiver {
//
//    //interface
//    private static SmsListener sSmsListener;
//
//    public static void registerReceiver(Context context, SmsListener smsListener) {
//        setComponentState(context, SmsReceiver.class, true);
//        sSmsListener = smsListener;
//    }
//
//    public static void unregisterReceiver(Context context) {
//        setComponentState(context, SmsReceiver.class, false);
//    }
//
//    public static void setComponentState(Context context, Class<?> componentClassName, boolean enabled) {
//        PackageManager pm = context.getPackageManager();
//        ComponentName receiver = new ComponentName(context, componentClassName);
//        int state = enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
//        pm.setComponentEnabledSetting(receiver,
//                state,
//                PackageManager.DONT_KILL_APP);
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Bundle bundle = intent.getExtras();
//
//        Object[] pdus = new Object[0];
//        if (bundle != null) {
//            pdus = (Object[]) bundle.get("pdus");
//        }
//
//        if (pdus != null) {
//            for (Object pdu : pdus) {
//                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
//
//                String sender = smsMessage.getDisplayOriginatingAddress();
//                //Check the sender to filter messages which we require to read
//
//                if (sender.equals("DTEK-info")) {
//                    String messageBody = smsMessage.getMessageBody();
//
//                    //Pass the message text to interface
//                    if (context != null && sSmsListener != null) {
//                        sSmsListener.messageReceived(messageBody);
//                    }
//                }
//            }
//        }
//    }
//}

