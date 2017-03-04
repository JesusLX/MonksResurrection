package com.limox.jesus.teambeta_sqlite.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.limox.jesus.teambeta_sqlite.Provider.TeamBetaContract;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;
import com.limox.jesus.teambeta_sqlite.db.DatabaseContract;

/**
 * Created by jesus on 8/11/16.
 */

public class User implements Parcelable {
    private int mId;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mIcon;
    private boolean mBlocked;
    private boolean mDeleted;
    private int mUserType;
    private String mPostsLiked_URL;

    public User(int idUser, String nick, String email, String password, String profilePicture, boolean profileBlocked, boolean userDeleted, int userType) {
        this.mId = idUser;
        this.mName = nick;
        mEmail = email;
        mPassword = password;
        this.mIcon = profilePicture;
        this.mBlocked = profileBlocked;
        this.mDeleted = userDeleted;
        this.mUserType = userType;
    }

    public User(int mId, String mNick, String mEmail, String mPassword, String mIcon, int mUserType) {
        this.mId = mId;
        this.mName = mNick;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mIcon = mIcon;
        this.mUserType = mUserType;
        this.mBlocked = false;
        this.mDeleted = false;
    }

    public User(String mName, String mEmail, String mPassword) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mIcon = DatabaseContract.UserEntry.DEFAULT_ICON;
        this.mUserType = 1;
        this.mBlocked = false;
        this.mDeleted = false;
    }

    public User(String nick) {
        this.mName = nick;
    }

    /**
     * This is just fot find a user by id at the lists
     *
     * @param mId A user's id
     */
    public User(int mId) {
        this.mId = mId;
    }

    protected User(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mIcon = in.readString();
        mBlocked = in.readByte() != 0;
        mDeleted = in.readByte() != 0;
        mUserType = in.readInt();
        mPostsLiked_URL = in.readString();
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

    public User() {

    }

    public int getIdUser() {
        return mId;
    }

    public void setIdUser(int mIdUser) {
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

    public String getProfilePicture() {
        return mIcon;
    }

    public void setIcon(String mProfilePicture) {
        this.mIcon = mProfilePicture;
    }

    public String getIcon() {
        return this.mIcon;
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

    public int getTypeUser() {
        return mUserType;
    }

    public void setUserType(int mUserType) {
        this.mUserType = mUserType;
    }

    public boolean isAdmin() {

        return getTypeUser() == AllConstants.ADMIN_TYPE_ID;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mId == user.mId;

    }

    @Override
    public int hashCode() {
        return mId;
    }

    public String getPostsLiked_URL() {
        return mPostsLiked_URL;
    }

    public void setPostsLiked_URL(String postsLiked_URL) {
        this.mPostsLiked_URL = postsLiked_URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mEmail);
        parcel.writeString(mPassword);
        parcel.writeString(mIcon);
        parcel.writeByte((byte) (mBlocked ? 1 : 0));
        parcel.writeByte((byte) (mDeleted ? 1 : 0));
        parcel.writeInt(mUserType);
        parcel.writeString(mPostsLiked_URL);
    }
}
