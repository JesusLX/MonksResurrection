package com.limox.jesus.teambeta.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jesus on 1/03/17.
 */

public class TeamBetaContract{
    public static final String AUTHORITY  = "com.limox.jesus.teambeta";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private TeamBetaContract(){}

    public static class Users implements BaseColumns {
        public static final String CONTENT_PATH ="user";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,CONTENT_PATH);

        public static final String NAME = "name";
        public static final String PASSWORD = "password";
        public static final String POSTS_LIKED = "posts_liked";
        public static final String BLOCKED = "blocked";
        public static final String DELETED = "deleted";
        public static final String EMAIL = "email";
        public static final String ICON = "icon";
        public static final String TYPE_USER = "type_user";

        public static final String[] PROJECTION_SET = new String[]{NAME,PASSWORD,POSTS_LIKED,BLOCKED,DELETED,EMAIL,ICON,TYPE_USER};
        public static final String[] PROJECTION_GET = new String[]{_ID,NAME,PASSWORD,POSTS_LIKED,BLOCKED,DELETED,EMAIL,ICON,TYPE_USER};
        public static final int ID_KEY = 0;
        public static final int NAME_KEY = 1;
        public static final int PASSWORD_KEY = 2;
        public static final int POSTS_LIKED_KEY = 3;
        public static final int BLOCKED_KEY = 4;
        public static final int DELETED_KEY = 5;
        public static final int EMAIL_KEY = 6;
        public static final int ICON_KEY = 7;
        public static final int TYPE_USER_KEY = 8;
    }

    public static class Posts implements BaseColumns{

        public static final String CONTENT_PATH ="post";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,CONTENT_PATH);
        public static final String ID_USER = "id_user";
        public static final String TITLE = "title";
        public static final String TEXT = "text";
        public static final String TAGS = "tags";
        public static final String IMAGES = "images";
        public static final String SCORE = "score";
        public static final String PUBLISHED = "published";
        public static final String FIXED = "fixed";
        public static final String CREATION_DATE = "date";
        public static final String DELETED = "deleted";
        public static final String[] PROJECTION_GET = new String[]{"p."+_ID,"p."+ID_USER,"p."+TITLE,"p."+TEXT,"p."+TAGS,"p."+IMAGES,"p."+SCORE,
                                                               "p."+PUBLISHED,"p."+FIXED,"p."+CREATION_DATE,"p."+DELETED,
                                                               "u."+Users.ICON,"u."+Users.NAME};
        public static final int ID_KEY = 0;
        public static final int USER_ID_KEY = 1;
        public static final int TITLE_KEY = 2;
        public static final int TEXT_KEY = 3;
        public static final int TAGS_KEY = 4;
        public static final int IMAGES_KEY = 5;
        public static final int SCORE_KEY = 6;
        public static final int PUBLISHED_KEY = 7;
        public static final int FIXED_KEY = 8;
        public static final int CREATION_DATE_KEY = 9;
        public static final int DELETED_KEY = 10;
        public static final int USER_ICON_KEY = 11;
        public static final int USER_NAME_KEY = 12;
        public static final int FORUM_ID_KEY = 13;
    }
    public static class Comments implements BaseColumns {
        public static final String CONTENT_PATH ="comment";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,CONTENT_PATH);

        public static final String ID_USER = "id_user";
        public static final String ID_POST = "id_post";
        public static final String TEXT = "text";
        public static final String SCORE = "score";
        public static final String CREATION_DATE = "date";
        public static final String DELETED = "deleted";

        public static final String[] PROJECTION = new String[]{"c."+ID_USER,"c."+ID_POST,"c."+TEXT,"c."+SCORE,"c."+CREATION_DATE,"c."+DELETED,
                "u."+Users.ICON,"u."+Users.NAME};

        public static final int ID_KEY = 0;
        public static final int ID_USER_KEY = 1;
        public static final int ID_POST_KEY = 2;
        public static final int TEXT_KEY = 3;
        public static final int SCORE_KEY = 4;
        public static final int CREATION_DATE_KEY = 5;
        public static final int DELETED_KEY = 6;
        public static final int USER_ICON_KEY = 7;
        public static final int USER_NAME_KEY = 8;
    }

}
