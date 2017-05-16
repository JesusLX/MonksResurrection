package com.limox.jesus.teambeta.Model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Jesus on 26/04/2017.
 */

public class Forum {

    public static final int OWN = 1;
    public static final int ADMIN = 2;
    public static final int PARTAKER = 3;

    private String key;
    private String name;
    private String imgUrl;
    private String ownerId;
    private String usersKey;
    private String adminsKey;
    private String description;
    private Date creationDate;
    private List<String> tags;

    public Forum() {
    }

    public Forum(String name, String imgUrl, String ownerId, String usersKey, String adminsKey, String description, List<String> tags) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.ownerId = ownerId;
        this.usersKey = usersKey;
        this.adminsKey = adminsKey;
        this.description = description;
        this.tags = tags;
        this.creationDate = new Date();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getUsersKey() {
        return usersKey;
    }

    public void setUsersKey(String usersKey) {
        this.usersKey = usersKey;
    }

    public String getAdminsKey() {
        return adminsKey;
    }

    public void setAdminsKey(String adminsKey) {
        this.adminsKey = adminsKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getKey().equals(((Forum) obj).getKey());
    }
}
