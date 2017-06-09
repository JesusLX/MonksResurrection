package com.limox.jesus.teambeta.Model;

import java.util.Date;

/**
 * Created by jesus on 10/11/16.
 */

public class Commentary {
    private int mKey;
    private int mUserImgProf;
    private int mKeyPost;
    private int mKeyUser;
    private boolean mDeleted;
    private Date mCreationDate;
    private String mContent;

    public Commentary() {

    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public int getKeyPost() {
        return mKeyPost;
    }

    public void setKeyPost(int keyPost) {
        mKeyPost = keyPost;
    }

    public int getKeyUser() {
        return mKeyUser;
    }

    public void setKeyUser(int keyUser) {
        mKeyUser = keyUser;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean deleted) {
        mDeleted = deleted;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getUserImgProf() {
        return mUserImgProf;
    }

    public void setUserImgProf(int userImgProf) {
        mUserImgProf = userImgProf;
    }
}
