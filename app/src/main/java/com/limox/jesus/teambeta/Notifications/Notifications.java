package com.limox.jesus.teambeta.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.limox.jesus.teambeta.PostView_Activity;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;

/**
 * Class to send notifications
 * Created by jesus on 4/03/17.
 */
public class Notifications {
    public static void SentPublicationPostSent(Context context, Bundle post) {
        if (Preferences.getNotifications(context)) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(context.getResources().getString(R.string.publication_sended_title));
            builder.setContentText(context.getResources().getString(R.string.publication_sended_content));
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setDefaults(Notification.DEFAULT_VIBRATE);
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            builder.setAutoCancel(true);
            Intent resultIntent = new Intent(context, PostView_Activity.class);
            resultIntent.putExtras(post);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(resultPendingIntent);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(AllConstants.Notifications.POST_SENDED, builder.build());
        }
    }
}
