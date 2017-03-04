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
        public static final String DEFAULT_ICON = "https://jesuslx.ncatz.com/wp-apps/teambeta/user-icons/def_icon.png";

        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT," +
                        "%s TINYINT DEFAULT 0," +
                        "%s TINYINT DEFAULT 0," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT DEFAULT '%s'," +
                        "%s INTEGER DEFAULT 1)",
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
                COLUMN_PASSWORD,COLUMN_EMAIL,COLUMN_TYPE_USER,"'Admin'","'123'","'admin1@ncatz.com'",0
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

        public static final String SQL_INSERT_ENTRIES = String.format(
                "INSERT INTO %s " +
                        "(%s,%s,%s,%s,%s,%s) VALUES " +
                        "(%s,'%s','%s','%s',%s,'%s')," +
                        "(%s,'%s','%s','%s',%s,'%s')," +
                        "(%s,'%s','%s','%s',%s,'%s')",
                TABLE_NAME,
                COLUMN_ID_USER,COLUMN_TITLE,COLUMN_TEXT,COLUMN_TAGS,COLUMN_PUBLISHED,COLUMN_CREATION_DATE,
                1,"No se actualizan las listas","Cuando se hace una acción de CRUD aunque notifique a las listas no se modifican, (YA SI LO HACEN, MANDA ESTO A ARREGLADOS DESDE AHÍ ARRIBA)","error,real",1,"1488636094911",
                1,"No están listos los comentarios aun","Como he sido muy tonto en vez de hacerte caso y hacer los comentarios con los cursores he hecho todo lo demas, el crear usuarios e iniciar sesión va con la base de datos y todo lo de las publicaciones también","error,real",0,"1488636095911",
                1,"La foto de usuario no cambia","Cuando intento coger al usuario para pillar su foto cuando te vas a su perfil me utiliza ese cursor por algun motivo para las publicaciones que se hacen a la vez","error,real",0,"1488636195911"
        );


        public static final String ASC_SORT = "p."+COLUMN_CREATION_DATE + " ASC";
        public static final String DESC_SORT = "p."+COLUMN_CREATION_DATE + " DESC";

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
