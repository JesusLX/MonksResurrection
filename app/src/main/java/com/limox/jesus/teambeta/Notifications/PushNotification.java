package com.limox.jesus.teambeta.Notifications;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.limox.jesus.teambeta.Model.Message;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.RestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Class who contain an manage notification's data
 * Created by Jesus on 18/06/2017.
 */
public class PushNotification {
    public static final int TYPE_NEW_MESSAGE = 1;

    public static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String FCM_APIKEY = "AAAAYqf_saU:APA91bFXZ4EYDGYio-BxMvadrVQ1Yif3Fc7LpwzX3f3NblkTGL3I7zlYxrY3_gD_Vb79pYPEBa9yOIwI_PF9sTgHL-mZSMu6zSKIoewYF0-I_u03_WZs95SQsbYtwy5tq4KuaplYjPS5";

    private int type;
    private String fromUser;
    private String toUser;
    private MessageNotif message;

    public MessageNotif getMessage() {
        return message;
    }

    public void setChatMessage(Message chatMessage, String fromUserName, String forumKey, String chatKey) {
        message = new MessageNotif();
        message.idUser = chatMessage.getUserKey();
        message.imgUrl = chatMessage.getPhotoUrl();
        message.text = chatMessage.getText();
        message.datetime = chatMessage.getDate();
        message.fromUserName = fromUserName;
        message.fromUserName = fromUserName;
        message.forumKey = forumKey;
        message.chatKey = chatKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }


    public class MessageNotif {
        private String idUser,
                imgUrl,
                text;
        long datetime;
        private String fromUserName;

        public String forumKey;

        public String chatKey;

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setText(String message) {
            this.text = message;
        }

        public String getText() {
            return this.text;
        }

        public void setForumKey(String forumKey) {
            this.forumKey = forumKey;
        }

        public String getForumKey() {
            return this.forumKey;
        }

        public void setChatKey(String chatKey) {
            this.chatKey = chatKey;
        }

        public String getChatKey() {
            return this.chatKey;
        }

        public long getDatetime() {
            return datetime;
        }

        public void setDatetime(long datetime) {
            this.datetime = datetime;
        }

        public Message getMessage() {
            return new Message(text, Users_Repository.get().getCurrentUser().getId(), Users_Repository.get().getCurrentUser().getProfilePicture(), new Date().getTime());
        }

        public String getFromUserName() {
            return fromUserName;
        }

        public void setFromUserName(String fromUserName) {
            this.fromUserName = fromUserName;
        }
    }

    public static PushNotification parseReceivedNotif(Map<String, String> data) {

        Gson gson = new Gson();
        PushNotification pushNotification = new PushNotification();
        pushNotification.type = Integer.valueOf(data.get("type"));
        pushNotification.fromUser = data.get("fromUser");
        pushNotification.toUser = data.get("toUser");
        String message = data.get("message");
        if (message != null) {
            try {
                JSONObject messageJSON = new JSONObject(message);
                pushNotification.message = gson.fromJson(messageJSON.toString(), MessageNotif.class);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pushNotification;
    }

    public void pushNotification(String deviceId) {
        try {
            JSONObject root = new JSONObject();
            // JSONArray j = this.toJSON();
            try {
                JSONObject j = toJSON();
                root.put("data", j);
                root.put("to", deviceId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringEntity entity = new StringEntity(root.toString());

            RestClient.post(FCM_URL, FCM_APIKEY, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private JSONObject toJSON() {
        JSONObject jsonArray = null;
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        try {
            jsonArray = new JSONObject(jsonString);
            //jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}

