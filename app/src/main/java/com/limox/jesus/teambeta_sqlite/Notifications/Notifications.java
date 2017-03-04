package com.limox.jesus.teambeta_sqlite.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;

import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;

/**
 * Created by jesus on 4/03/17.
 */

public class Notifications {
    public static void SentPublicationPostSended(Context context){
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(context.getResources().getString(R.string.publication_sended_title));
        builder.setContentText(context.getResources().getString(R.string.publication_sended_content));
        builder.setSmallIcon(R.drawable.isologo);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(AllConstants.Notifications.POST_SENDED,builder.build());
    }
}
