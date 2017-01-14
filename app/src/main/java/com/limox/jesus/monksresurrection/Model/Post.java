package com.limox.jesus.monksresurrection.Model;

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

    protected Post(Parcel in) {
        mIdPost = in.readInt();
        mIdUser = in.readInt();
        mScore = in.readInt();
        mState = in.readInt();
        mTitle = in.readString();
        mDescription = in.readString();
        mTags = in.readString();
        mDeleted = in.readByte() != 0;
    }
    public static final Comparator<Post> LAST_FIRST = new Comparator<Post>() {
        @Override
        public int compare(Post post, Post t1) {
            return post.getCreationDate().compareTo(t1.getCreationDate());
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIdPost);
        parcel.writeInt(mIdUser);
        parcel.writeInt(mScore);
        parcel.writeInt(mState);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mTags);
        parcel.writeByte((byte) (mDeleted ? 1 : 0));
    }


    @IntDef({NOT_PUBLISHED,PUBLISHED,FIXED,ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE{}
    public static final int NOT_PUBLISHED = 0;
    public static final int PUBLISHED = 1;
    public static final int FIXED = 2;
    public static final int ALL = 3;

    private int mIdPost;
    private int mIdUser;
    private int mScore;
    private int mState;
    private String mTitle;
    private String mDescription;
    private String mTags;
    private boolean mDeleted;
    private Date mCreationDate;

    public Post(String mTitle, int mIdUser, String mDescription, String mTags, int mIdPost) {
        this.mTitle = mTitle;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mTags = mTags;
        this.mIdPost = mIdPost;
        this.mState = NOT_PUBLISHED;
        this.mDeleted = false;
    }



    public Post(int mIdPost, String mTitle, int mIdUser, String mDescription, @STATE int state, String mTags) {
        this.mIdPost = mIdPost;
        this.mTitle = mTitle;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mState = state;
        this.mTags = mTags;
        this.mCreationDate = new Date();
        this.mDeleted = false;
    }

    public Post(int mIdPost) {
        this.mIdPost = mIdPost;
    }

    public void setState(@STATE int state){
        this.mState = state;
    }

    public @STATE int getState() {
        return mState;
    }
    public int getIdPost() {
        return mIdPost;
    }

    public void setIdPost(int mIdPost) {
        this.mIdPost = mIdPost;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int mIdUser) {
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
        return "Post{" +
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
