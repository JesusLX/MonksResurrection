package com.limox.jesus.teambeta.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by jesus on 10/11/16.
 */

public class Post implements Parcelable{
    private static final int DESCRIPTION_SHORTED_LENGTH = 47;

    public static final Comparator<Post> LAST_FIRST = new Comparator<Post>() {
        @Override
        public int compare(Post post, Post t1) {
            int comp =post.getCreationDate().compareTo(t1.getCreationDate());
            int result = 0;
            switch (comp){
                case -1:
                    result = 1;
                    break;
                case 1:
                    result = -1;
                    break;
            }
            return result;
        }
    };
    private String mForumsKey;

    protected Post(Parcel in) {
        mForumsKey = in.readString();
        mIdPost = in.readString();
        mIdUser = in.readString();
        mIdForum = in.readString();
        mScore = in.readInt();
        mState = in.readInt();
        mTitle = in.readString();
        mDescription = in.readString();
        mTags = in.readString();
        mDeleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mForumsKey);
        dest.writeString(mIdPost);
        dest.writeString(mIdUser);
        dest.writeString(mIdForum);
        dest.writeInt(mScore);
        dest.writeInt(mState);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mTags);
        dest.writeByte((byte) (mDeleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getForumsKey() {
        return mForumsKey;
    }

    public void setForumsKey(String forumsKey) {
        this.mForumsKey = forumsKey;
    }

    public String getIdForum() {
        return mIdForum;
    }

    public void setIdForum(String idForum) {
        mIdForum = idForum;
    }


    @IntDef({NOT_PUBLISHED,PUBLISHED,FIXED,ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE{}
    public static final int NOT_PUBLISHED = 0;
    public static final int PUBLISHED = 1;
    public static final int FIXED = 2;
    public static final int ALL = 3;

    private String mIdPost;
    private String mIdUser;
    private String mIdForum;
    private int mScore;
    private int mState;
    private String mTitle;
    private String mDescription;
    private String mTags;
    private boolean mDeleted;
    private Date mCreationDate;

    public Post() {
        this.mIdPost = "";
        this.mIdUser = "";
        this.mIdForum = "";
        this.mScore = 0;
        this.mState = 0;
        this.mTitle = "";
        this.mForumsKey = "";
        this.mDescription = "";
        this.mTags = "";
        this.mDeleted = false;
        this.mCreationDate = null;
    }

    public Post(String mIdUser, String mIdForum, String mTitle, String mDescriptions, String mTags) {
        this.mIdUser = mIdUser;
        this.mIdForum = mIdForum;
        this.mTitle = mTitle;
        this.mDescription = mDescriptions;
        this.mTags = mTags;
        this.mState = NOT_PUBLISHED;
        this.mDeleted = false;
        this.mCreationDate = new Date();
    }

    public Post(String mTitle, String mIdForum, String mIdUser, String mDescription, String mTags, String mIdPost) {
        this.mTitle = mTitle;
        this.mIdForum = mIdForum;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mTags = mTags;
        this.mIdPost = mIdPost;
        this.mState = NOT_PUBLISHED;
        this.mDeleted = false;
        this.mCreationDate = new Date();
    }


    public Post(String mIdPost, String mIdForum, String mTitle, String mIdUser, String mDescription, @STATE int state, String mTags) {
        this.mIdPost = mIdPost;
        this.mIdForum = mIdForum;
        this.mTitle = mTitle;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mState = state;
        this.mTags = mTags;
        this.mCreationDate = new Date();
        this.mDeleted = false;
    }

    public Post(String mIdPost) {
        this.mIdPost = mIdPost;
    }


    public void setState(@STATE int state){
        this.mState = state;
    }

    public @STATE int getState() {
        return mState;
    }

    public String getIdPost() {
        return mIdPost;
    }

    public void setIdPost(String mIdPost) {
        this.mIdPost = mIdPost;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String mIdUser) {
        this.mIdUser = mIdUser;
    }

    public String getDescription() {
        return mDescription;
    }
    public String getDescriptionShorted() {
        if (mDescription.length()>DESCRIPTION_SHORTED_LENGTH){
            return mDescription.substring(0,DESCRIPTION_SHORTED_LENGTH)+"...";
        }
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isPublicate() {
        return mState == PUBLISHED;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean mDeleted) {
        this.mDeleted = mDeleted;
    }

    public String getTags() {
        return mTags;
    }

    public void setTags(String mTags) {
        this.mTags = mTags;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    public boolean isFixed() {
        return mState == FIXED;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "mIdPost=" + mIdPost +
                ", mTitle='" + mTitle + '\'' +
                ", mIdUser=" + mIdUser +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return mIdPost == post.mIdPost;

    }

}
