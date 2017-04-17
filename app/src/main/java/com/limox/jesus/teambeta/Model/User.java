package com.limox.jesus.teambeta.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.limox.jesus.teambeta.db.DatabaseContract;

import java.util.ArrayList;

/**
 * Created by jesus on 8/11/16.
 */

public class User implements Parcelable {
    private static final String DEF_IMAGE = "https://jesuslx.ncatz.com/wp-apps/teambeta/user-icons/def_icon.png";
    private String mId;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String profilePicture;
    private boolean mBlocked;
    private boolean mDeleted;
    private ArrayList<String> mPostsLiked;
    private ArrayList<String> mForumsAdmin;


    public User(String idUser, String nick, String email, String password, String profilePicture, boolean profileBlocked, boolean userDeleted) {
        this.mId = idUser;
        this.mName = nick;
        mEmail = email;
        mPassword = password;
        this.profilePicture = profilePicture;
        this.mBlocked = profileBlocked;
        this.mDeleted = userDeleted;
    }

    public User(String mId, String mNick, String mEmail, String mPassword, String profilePicture) {
        this.mId = mId;
        this.mName = mNick;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.profilePicture = profilePicture;
        this.mBlocked = false;
        this.mDeleted = false;
    }

    public User(String mName, String mEmail, String mPassword) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.profilePicture = DatabaseContract.UserEntry.DEFAULT_ICON;
        this.mBlocked = false;
        this.mDeleted = false;
    }


    /**
     * This is just fot find a user by id at the lists
     *
     * @param mId A user's id
     */
    public User(String mId) {
        this.mId = mId;
    }

    public User() {

    }

    public User(String name, String email) {
        this.setName(name);
        this.setEmail(email);
        this.setProfilePicture(DEF_IMAGE);
        this.setBlocked(false);
        this.setDeleted(false);
        this.setForumsAdmin(new ArrayList<String>());
        this.setPostsLiked(new ArrayList<String>());
    }


    protected User(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        profilePicture = in.readString();
        mBlocked = in.readByte() != 0;
        mDeleted = in.readByte() != 0;
        mPostsLiked = in.createStringArrayList();
        mForumsAdmin = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mPassword);
        dest.writeString(profilePicture);
        dest.writeByte((byte) (mBlocked ? 1 : 0));
        dest.writeByte((byte) (mDeleted ? 1 : 0));
        dest.writeStringList(mPostsLiked);
        dest.writeStringList(mForumsAdmin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getIdUser() {
        return mId;
    }

    public void setIdUser(String mIdUser) {
        this.mId = mIdUser;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mNick) {
        this.mName = mNick;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setProfilePicture(String mProfilePicture) {
        this.profilePicture = mProfilePicture;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public boolean isBlocked() {
        return mBlocked;
    }

    public void setBlocked(boolean mProfileBlocked) {
        this.mBlocked = mProfileBlocked;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean mUserDeleted) {
        this.mDeleted = mUserDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mId == user.mId;

    }

    public ArrayList<String> getPostsLiked() {
        return mPostsLiked;
    }

    public void setPostsLiked(ArrayList<String> postsLiked_URL) {
        this.mPostsLiked = postsLiked_URL;
    }


    public ArrayList<String> getForumsAdmin() {
        return mForumsAdmin;
    }

    public void setForumsAdmin(ArrayList<String> mForumsAdmin) {
        this.mForumsAdmin = mForumsAdmin;
    }

}
