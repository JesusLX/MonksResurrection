package com.limox.jesus.teambeta.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.limox.jesus.teambeta.Chats_Activity;
import com.limox.jesus.teambeta.Model.PushNotification;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.TeamBetaApplication;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;

import java.util.Date;
import java.util.Map;

/**
 * Created by Jesus on 04/06/2017.
 */
public class MessagesService extends FirebaseMessagingService {

    private static final String TAG = "FIREBASEEEE";
    public static final String ACTION_LAUNCH_CHAT = "chat";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String currentActivity = TeamBetaApplication.getCurrentActivity();
        Map<String, String> data = remoteMessage.getData();
        PushNotification pushNotification = PushNotification.parseReceivedNotif(data);
        Toast.makeText(TeamBetaApplication.getContext(), "NOTIIIII", Toast.LENGTH_LONG).show();
        if (Users_Repository.get().getCurrentUser().getId().equals(pushNotification.getToUser())) {
            String title = "",
                    message = "";
            Intent intent = new Intent(this, Chats_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            switch (pushNotification.getType()) {

                case PushNotification.TYPE_NEW_MESSAGE:
                    title = getString(R.string.app_name) + ": " + pushNotification.getMessage().getFromUserName();
                    message = pushNotification.getMessage().getText();
                    Bundle c = new Bundle();
                    c.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, remoteMessage.getFrom());
                    intent.putExtras(c);
                    break;

            }
            if (!(currentActivity.startsWith("MessagesList_Fragment_" + remoteMessage.getFrom())) &&
                    (Users_Repository.get().getCurrentUser() != null && Users_Repository.get().getCurrentUser().getId().equals(pushNotification.getToUser()))) {

                // if (true){
                intent.putExtra("to", pushNotification.getToUser());
                intent.putExtra(AllConstants.Keys.SimpleBundle.ID_USER_KEY, pushNotification.getFromUser());
                intent.putExtra(AllConstants.Keys.SimpleBundle.ID_CHAT_KEY, pushNotification.getMessage().getChatKey());
                intent.putExtra(AllConstants.Keys.SimpleBundle.ID_FORUM_KEY, pushNotification.getMessage().getForumKey());

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                sendNotification(notificationId, title, message, pendingIntent);
            }
        }
    }

    private void sendNotification(int notificationId, String title, String messageBody, PendingIntent pendingIntent) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Boolean vibrationCode = Preferences.getNotifications(TeamBetaApplication.getContext());
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(2) //Max
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        android.app.Notification defaultsValuesNotif = new android.app.Notification();
        long[] vibration;
        if (vibrationCode) { //Default
            defaultsValuesNotif.defaults |= android.app.Notification.DEFAULT_VIBRATE;
        } else {
           /* switch (vibrationCode) {
                case "0":  //Deactivated*/
            vibration = new long[]{0};
                 /*   break;
                case "2":  //Short
                    vibration = new long[]{0, 300, 100, 300};
                    break;
                default:  //Long
                    vibration = new long[]{0, 700, 300, 700};
                    break;
            }*/
            notificationBuilder.setVibrate(vibration);
        }
        defaultsValuesNotif.defaults |= android.app.Notification.DEFAULT_LIGHTS;
        defaultsValuesNotif.defaults |= android.app.Notification.DEFAULT_SOUND;
        notificationBuilder.setDefaults(defaultsValuesNotif.defaults);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            if (ACTION_LAUNCH_CHAT.equals(action)) {
                Intent aintent = new Intent(this, Chats_Activity.class);
                startActivity(aintent);
            }
        }
    }

}
