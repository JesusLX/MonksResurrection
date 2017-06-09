package com.limox.jesus.teambeta.Model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jesus on 08/06/2017.
 */

public class Chat {
    private String key;
    private ArrayList<ArrayList<String>> usersData;

    public Chat(String chatKey) {
        this.key = chatKey;
        this.usersData = new ArrayList<>();
    }

    public Chat() {
        this.key = "";
        this.usersData = new ArrayList<>();
    }

    public Chat(String chatKey, ArrayList<ArrayList<String>> arrayLists) {
        this.key = chatKey;
        this.usersData = arrayLists;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<ArrayList<String>> getUsersData() {
        return usersData;
    }

    public void setUsersData(ArrayList<ArrayList<String>> usersData) {
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
        for (ArrayList<String> data : getUsersData()) {
            name = name.equals("") ? data.get(1) : " + " + data.get(1);
        }
        return name;
    }
}
