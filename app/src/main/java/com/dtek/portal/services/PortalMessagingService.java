package com.dtek.portal.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dtek.portal.Const;
import com.dtek.portal.R;
import com.dtek.portal.ui.activity.MainActivity;
import com.dtek.portal.utils.NotificationHelper;
import com.dtek.portal.utils.PreferenceUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Map;

public class PortalMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        PreferenceUtils.savePushToken(s);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        PendingIntent pIntent = getPendingIntent(remoteMessage.getData());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            showNotificationLevel26(title, body, pIntent);
        else
            showNotification(title, body, pIntent);
    }

    private PendingIntent getPendingIntent(Map<String, String> data) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        try {
            notifyIntent.putExtra(Const.PUSH.DATA_TYPE, data.get(Const.PUSH.DATA_TYPE));
            notifyIntent.putExtra(Const.PUSH.JSON_BODY, data.get(Const.PUSH.JSON_BODY));
        } catch (NullPointerException ignored) {
        }
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationLevel26(String title, String body, PendingIntent pIntent) {
        NotificationHelper helper = new NotificationHelper(getBaseContext());
        Notification.Builder builder = helper.getChannel(title, body, pIntent);
        int id = getNotificationId();
        helper.getManager().notify(id , builder.build());
    }

    private int getNotificationId(){
        int id = PreferenceUtils.getNotificationId();
        PreferenceUtils.saveNotificationId(++id);
        return id;
    }

    @SuppressWarnings("deprecation")
    // до 26-ой версии метод не deprecation, а при 26-ой вызывается showNotificationLevel26
    private void showNotification(String title, String body, PendingIntent pIntent) {
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_push)
                .setColor(getResources().getColor(R.color.color_push_icon))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setSound(defaultSound);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notificationBuilder.build());
    }
}
