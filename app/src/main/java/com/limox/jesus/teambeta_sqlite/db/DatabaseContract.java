package com.limox.jesus.teambeta_sqlite.db;

import android.provider.BaseColumns;

/**
 * This class contais the subclass of columns who has the names of tables and columns
 * Created by jesus on 1/03/17.
 */
public class DatabaseContract {
    private DatabaseContract() {

    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_POSTS_LIKED = "posts_liked";
        public static final String COLUMN_BLOCKED = "blocked";
        public static final String COLUMN_DELETED = "deleted";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_TYPE_USER = "type_user";
        public static final String DEFAULT_ICON = "'https://www.ncatz.com/jesuslx/wp-apps/teambeta/user-icons/def_icon.png'";

        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT," +
                        "%s TINYINT DEFAULT 0," +
                        "%s TINYINT DEFAULT 0," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT DEFAULT %s," +
                        "%s INTEGER DEFAULT 0)",
                        TABLE_NAME,
                        _ID,
                        COLUMN_NAME,
                        COLUMN_PASSWORD,
                        COLUMN_POSTS_LIKED,
                        COLUMN_BLOCKED,
                        COLUMN_DELETED,
                        COLUMN_EMAIL,
                        COLUMN_ICON, DEFAULT_ICON,
                        COLUMN_TYPE_USER);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE %s ", TABLE_NAME);

        public static final String SQL_INSERT_ENTRIES = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (%s,%s,%s,%s)", TABLE_NAME,COLUMN_NAME,
                COLUMN_PASSWORD,COLUMN_EMAIL,COLUMN_TYPE_USER,"'Admin'","'123'","'admin1@ncatz.com'",1
                );


    }

    public static class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_ID_USER = "id_user";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_TAGS = "tags";
        public static final String COLUMN_IMAGES = "images";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_PUBLISHED = "published";
        public static final String COLUMN_FIXED = "fixed";
        public static final String COLUMN_CREATION_DATE = "date";
        public static final String COLUMN_DELETED = "deleted";

        public static final String REFERENCES_USER = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                UserEntry.TABLE_NAME, _ID);

        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NOT NULL %s," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s INTEGER DEFAULT 0," +
                        "%s TYNYINT DEFAULT 0," +
                        "%s TYNYINT DEFAULT 0," +
                        "%s TEXT NOT NULL," +
                        "%s TINYINT DEFAULT 0)",
                        TABLE_NAME,
                        _ID,
                        COLUMN_ID_USER, REFERENCES_USER,
                        COLUMN_TITLE,
                        COLUMN_TEXT,
                        COLUMN_TAGS,
                        COLUMN_IMAGES,
                        COLUMN_SCORE,
                        COLUMN_PUBLISHED,
                        COLUMN_FIXED,
                        COLUMN_CREATION_DATE,
                        COLUMN_DELETED);

        public static final String ASC_SORT = COLUMN_CREATION_DATE + " ASC";
        public static final String DESC_SORT = COLUMN_CREATION_DATE + " DESC";

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE %s ", TABLE_NAME);

        public static final String POST_JOIN = String.format(" p INNER JOIN %s u ON p.%s = u.%s",
                UserEntry.TABLE_NAME, COLUMN_ID_USER, _ID);

    }

    public static class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_ID_USER = "id_user";
        public static final String COLUMN_ID_POST = "id_post";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_CREATION_DATE = "date";
        public static final String COLUMN_DELETED = "deleted";

        public static final String REFERENCES_USER = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                UserEntry.TABLE_NAME, _ID);
        public static final String REFERENCES_POST = String.format("REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                PostEntry.TABLE_NAME, _ID);

        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NOT NULL %s," +
                        "%s INTEGER NOT NULL %s," +
                        "%s TEXT NOT NULL," +
                        "%s INTEGER DEFAULT 0," +
                        "%s TEXT NOT NULL," +
                        "%s TINYINT DEFAULT 0)",
                TABLE_NAME, _ID, COLUMN_ID_USER, REFERENCES_USER, COLUMN_ID_POST, REFERENCES_POST, COLUMN_TEXT,
                COLUMN_SCORE, COLUMN_CREATION_DATE, COLUMN_DELETED);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE %s ", TABLE_NAME);

        public static final String DEFAULT_SORT = COLUMN_CREATION_DATE + " ASC";

        public static final String COMMENT_JOIN = String.format(" c INNER JOIN %s u ON c.%s = u.%s",
                UserEntry.TABLE_NAME, COLUMN_ID_USER, _ID);
    }
}
