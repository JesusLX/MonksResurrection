package com.limox.jesus.teambeta.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.limox.jesus.teambeta.db.APIConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Jesus on 26/04/2017.
 */

public class Forum implements Parcelable {

    public static final int OWN = 1;
    public static final int ADMIN = 2;
    public static final int PARTAKER = 3;

    public static final int TYPE_COLLAPSED = 0;
    public static final int TYPE_TINTY = 1;

    private String key;
    private String name;
    private String imgUrl;
    private String ownerId;
    private String usersKey;
    private String adminsKey;
    private String description;
    private Date creationDate;
    private List<String> tags;
    private boolean deleted;

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
        this.deleted = false;
    }

    protected Forum(Parcel in) {
        key = in.readString();
        name = in.readString();
        imgUrl = in.readString();
        ownerId = in.readString();
        usersKey = in.readString();
        adminsKey = in.readString();
        description = in.readString();
        tags = in.createStringArrayList();
        deleted = in.readByte() != 0;
    }

    public static final Creator<Forum> CREATOR = new Creator<Forum>() {
        @Override
        public Forum createFromParcel(Parcel in) {
            return new Forum(in);
        }

        @Override
        public Forum[] newArray(int size) {
            return new Forum[size];
        }
    };

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

    public static Forum fromJSON(String response) {
        Forum forum = new Forum();
        try {
            JSONObject jsonForum = new JSONObject(response);
            forum = fromJSON(jsonForum);
        } catch (JSONException e) {
            e.printStackTrace();
            forum = null;
        }
        return forum;
    }

    public static Forum fromJSON(JSONObject jsonForum) {
        Forum forum = new Forum();
        forum.setKey(jsonForum.optString(APIConstants.Forums.FORUM_KEY));
        forum.setName(jsonForum.optString(APIConstants.Forums.FORUM_NAME));
        forum.setAdminsKey(jsonForum.optString(APIConstants.Forums.FORUM_ADM_KEY));
        forum.setUsersKey(jsonForum.optString(APIConstants.Forums.FORUM_USR_KEY));
        forum.setCreationDate(new Date(jsonForum.optLong(APIConstants.Forums.FORUM_CREATION_D)));
        forum.setDeleted(jsonForum.optBoolean(APIConstants.Forums.FORUM_DELETED));
        forum.setImgUrl(jsonForum.optString(APIConstants.Forums.FORUM_IMG_URL));
        forum.setOwnerId(jsonForum.optString(APIConstants.Forums.FORUM_OWNER_ID));

        return forum;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(imgUrl);
        dest.writeString(ownerId);
        dest.writeString(usersKey);
        dest.writeString(adminsKey);
        dest.writeString(description);
        dest.writeStringList(tags);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }

    public String getShortedDescription() {
        int maxLength = 30;
        if (description != null && description.length() > maxLength) {
            return description.substring(0, maxLength - 3).concat("...");
        } else
            return getDescription();
    }
}
