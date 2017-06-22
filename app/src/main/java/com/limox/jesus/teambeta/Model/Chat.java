package com.limox.jesus.teambeta.Model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

/**
 * Class to contain chat data
 * Created by Jesus on 08/06/2017.
 */
public class Chat implements Parcelable {
    private String key;
    private ArrayList<User> usersData;
    private ArrayList<String> softInfo;

    public Chat(String chatKey) {
        this.key = chatKey;
        this.usersData = new ArrayList<>();
    }

    public Chat() {
        this.key = "";
        this.usersData = new ArrayList<>();
    }

    public Chat(String chatKey, ArrayList<User> arrayLists) {
        this.key = chatKey;
        this.usersData = arrayLists;
    }

    protected Chat(Parcel in) {
        key = in.readString();
        usersData = in.createTypedArrayList(User.CREATOR);
        softInfo = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeTypedList(usersData);
        dest.writeStringList(softInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<User> getUsersData() {
        if (usersData == null) {
            usersData = new ArrayList<>();
        }
        return usersData;
    }

    public void setUsersData(ArrayList<User> usersData) {
        this.usersData = usersData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chat)
            return this.getKey().equals(((Chat) obj).getKey());
        if (obj instanceof String)
            return this.getKey().equals(obj);
        return false;
    }

    public String optName() {
        String name = "";
        for (User data : getUsersData()) {
            name += name.equals("") ? data.getName() : " + " + data.getName();
        }
        return name;
    }

    public ArrayList<String> getSoftInfo() {
        return softInfo;
    }

    public void setSoftInfo(ArrayList<String> softInfo) {
        this.softInfo = softInfo;
    }

    public Bundle optBundle() {
        Bundle me = new Bundle();
        me.putParcelable(AllConstants.Keys.Parcelables.CHAT_KEY, this);
        return me;
    }

    public String optOtherUserKey(String ignoredId) {
        for (User tmp :
                getUsersData()) {
            if (!tmp.getId().equals(ignoredId))
                return tmp.getId();
        }
        return null;
    }

    public String optOtherUserToken(String ignoredId) {
        for (User tmp :
                getUsersData()) {
            if (!tmp.getId().equals(ignoredId))
                return tmp.getToken();
        }
        return null;
    }
}
