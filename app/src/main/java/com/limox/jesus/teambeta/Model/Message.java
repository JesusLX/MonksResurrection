package com.limox.jesus.teambeta.Model;

/**
 * Created by Jesus on 04/06/2017.
 */

public class Message {

    private String mKey;
    private String mText;
    private String mUserKey;
    private String mPhotoUrl;
    private long mDate;

    public Message() {
    }

    public Message(String text, String userKey, String photoUrl, long date) {
        this.mText = text;
        this.mUserKey = userKey;
        this.mPhotoUrl = photoUrl;
        this.mDate = date;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String userKay) {
        this.mUserKey = userKay;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getText() {
        return mText;
    }

    public void setPhotoUrl(String photoUrl) {
        this.mPhotoUrl = photoUrl;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }
}