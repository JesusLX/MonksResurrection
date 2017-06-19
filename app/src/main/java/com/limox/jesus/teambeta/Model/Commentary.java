package com.limox.jesus.teambeta.Model;

import java.util.Date;

/**
 * Class to contain commentary data
 * Created by jesus on 10/11/16.
 */
public class Commentary {
    private String mKey;
    private String mUserImgProf;
    private String mKeyPost;
    private String mKeyUser;
    private boolean mDeleted;
    private long mCreationDate;
    private String mContent;
    private String userName;

    public Commentary() {

    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getKeyPost() {
        return mKeyPost;
    }

    public void setKeyPost(String keyPost) {
        mKeyPost = keyPost;
    }

    public String getKeyUser() {
        return mKeyUser;
    }

    public void setKeyUser(String keyUser) {
        mKeyUser = keyUser;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean deleted) {
        mDeleted = deleted;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(long creationDate) {
        mCreationDate = creationDate;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUserImgProf() {
        return mUserImgProf;
    }

    public void setUserImgProf(String userImgProf) {
        mUserImgProf = userImgProf;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
