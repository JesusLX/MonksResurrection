package com.limox.jesus.teambeta.Model;

import java.util.Date;

/**
 * Created by Jesus on 26/04/2017.
 */

public class Forum {
    private String key;
    private String name;
    private String imgUrl;
    private String usersKey;
    private String adminsKey;
    private String description;
    private Date creationDate;
    private String[] tags;

    public Forum() {
    }

    public Forum(String name, String imgUrl, String usersKey, String adminsKey, String description, String[] tags) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.usersKey = usersKey;
        this.adminsKey = adminsKey;
        this.description = description;
        this.tags = tags;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
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
}
