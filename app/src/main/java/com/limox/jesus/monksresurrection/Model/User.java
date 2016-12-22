package com.limox.jesus.monksresurrection.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.limox.jesus.monksresurrection.Utils.AllConstants;

/**
 * Created by jesus on 8/11/16.
 */

public class User implements Parcelable{
    int mGameCode;
    int mIdUser;
    String mName;
    String mEmail;
    String mPassword;
    int mProfilePicture;
    boolean mProfileBlocked;
    boolean mUserDeleted;
    int mUserType;

    public User(int gameCode, int idUser, String nick, String email, String password, int profilePicture, boolean profileBlocked, boolean userDeleted, int userType) {
        this.mGameCode = gameCode;
        this.mIdUser = idUser;
        this.mName = nick;
        mEmail = email;
        mPassword = password;
        this.mProfilePicture = profilePicture;
        this.mProfileBlocked = profileBlocked;
        this.mUserDeleted = userDeleted;
        this.mUserType = userType;
    }

    public User(int gameCode, int mIdUser, String mNick, String mEmail, String mPassword, int mProfilePicture, int mUserType) {
        this.mGameCode = gameCode;
        this.mIdUser = mIdUser;
        this.mName = mNick;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mProfilePicture = mProfilePicture;
        this.mUserType = mUserType;
        this.mProfileBlocked = false;
        this.mUserDeleted = false;
    }

    public User(String nick) {
        this.mName = nick;
    }

    /**
     * This is just fot find a user by id at the lists
     *
     * @param mIdUser A user's id
     */
    public User(int mIdUser) {
        this.mIdUser = mIdUser;
    }

    protected User(Parcel in) {
        mGameCode = in.readInt();
        mIdUser = in.readInt();
        mName = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mProfilePicture = in.readInt();
        mProfileBlocked = in.readByte() != 0;
        mUserDeleted = in.readByte() != 0;
        mUserType = in.readInt();
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

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int mIdUser) {
        this.mIdUser = mIdUser;
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

    public int getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(int mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

    public boolean isProfileBlocked() {
        return mProfileBlocked;
    }

    public void setProfileBlocked(boolean mProfileBlocked) {
        this.mProfileBlocked = mProfileBlocked;
    }

    public boolean isUserDeleted() {
        return mUserDeleted;
    }

    public void setUserDeleted(boolean mUserDeleted) {
        this.mUserDeleted = mUserDeleted;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int mUserType) {
        this.mUserType = mUserType;
    }

    public boolean isAdmin() {

        return getUserType() == AllConstants.ADMIN_TYPE_ID;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mIdUser == user.mIdUser;

    }

    @Override
    public int hashCode() {
        return mIdUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mGameCode);
        dest.writeInt(mIdUser);
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mPassword);
        dest.writeInt(mProfilePicture);
        dest.writeByte((byte) (mProfileBlocked ? 1 : 0));
        dest.writeByte((byte) (mUserDeleted ? 1 : 0));
        dest.writeInt(mUserType);
    }
}
