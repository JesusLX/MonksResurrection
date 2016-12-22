package com.limox.jesus.monksresurrection.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jesus on 10/11/16.
 */

public class Post implements Parcelable{
    private static final int DESCRIPTION_SHORTED_LENGTH = 47;
    int mIdPost;
    String mTitle;
    int mIdUser;
    String mDescription;
    boolean mPublicate;
    boolean mDeleted;
    boolean mFixed;
    String mTags;
    Date mCreationDate;
    int mScore;

    public Post(String mTitle, int mIdUser, String mDescription, String mTags, int mIdPost) {
        this.mTitle = mTitle;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mTags = mTags;
        this.mIdPost = mIdPost;
        this.mFixed = false;
        this.mDeleted = false;
    }

    public Post(int mIdPost, String mTitle, int mIdUser, String mDescription, boolean publicate, boolean mFixed, String mTags) {
        this.mIdPost = mIdPost;
        this.mTitle = mTitle;
        this.mIdUser = mIdUser;
        this.mDescription = mDescription;
        this.mPublicate = publicate;
        this.mFixed = mFixed;
        this.mTags = mTags;
        this.mCreationDate = new Date();
        this.mDeleted = false;
    }

    public Post(int mIdPost) {
        this.mIdPost = mIdPost;
    }

    protected Post(Parcel in) {
        mIdPost = in.readInt();
        mTitle = in.readString();
        mIdUser = in.readInt();
        mDescription = in.readString();
        mPublicate = in.readByte() != 0;
        mDeleted = in.readByte() != 0;
        mFixed = in.readByte() != 0;
        mTags = in.readString();
        mScore = in.readInt();
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
        return mPublicate;
    }

    public void setPublicate(boolean mPublicate) {
        this.mPublicate = mPublicate;
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
        return mFixed;
    }

    public void setFixed(boolean mFixed) {
        this.mFixed = mFixed;
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

    @Override
    public int hashCode() {
        return mIdPost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdPost);
        dest.writeString(mTitle);
        dest.writeInt(mIdUser);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mPublicate ? 1 : 0));
        dest.writeByte((byte) (mDeleted ? 1 : 0));
        dest.writeByte((byte) (mFixed ? 1 : 0));
        dest.writeString(mTags);
        dest.writeInt(mScore);
    }
}
