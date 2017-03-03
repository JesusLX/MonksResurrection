package com.limox.jesus.teambeta_sqlite.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.db.DatabaseContract;
import com.limox.jesus.teambeta_sqlite.db.DatabaseHelper;

/**
 * Created by jesus on 1/03/17.
 */

public class TeamBetaProvider extends ContentProvider {

    private static final int USER = 1;
    private static final int USER_ID = 2;
    private static final int POST = 3;
    private static final int POST_ID = 4;
    private static final int COMMENT = 5;
    private static final int COMMENT_ID = 6;

    private SQLiteDatabase sqLiteDatabase;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Users.CONTENT_PATH, USER);
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Users.CONTENT_PATH + "/#", USER_ID);
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Posts.CONTENT_PATH, POST);
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Posts.CONTENT_PATH + "/#", POST_ID);
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Comments.CONTENT_PATH, COMMENT);
        uriMatcher.addURI(TeamBetaContract.AUTHORITY, TeamBetaContract.Comments.CONTENT_PATH + "/#", COMMENT_ID);
    }

    @Override
    public boolean onCreate() {
        sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        return sqLiteDatabase.isOpen();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        String rowId = null;
        switch (uriMatcher.match(uri)){
            case USER:
                builder.setTables(DatabaseContract.UserEntry.TABLE_NAME);
                break;
            case USER_ID:
                builder.setTables(DatabaseContract.UserEntry.TABLE_NAME);
                rowId = uri.getPathSegments().get(1);
                selection = DatabaseContract.UserEntry._ID+"=?";
                selectionArgs = new String[] {rowId};
                break;
            case POST:
                builder.setTables(DatabaseContract.PostEntry.TABLE_NAME+DatabaseContract.PostEntry.POST_JOIN);
                if (TextUtils.isEmpty(sortOrder)){
                    sortOrder = DatabaseContract.PostEntry.DESC_SORT;
                }
                break;
            case POST_ID:
                builder.setTables(DatabaseContract.PostEntry.TABLE_NAME+DatabaseContract.PostEntry.POST_JOIN);
                rowId = uri.getPathSegments().get(1);
                selection = DatabaseContract.PostEntry._ID+"=?";
                selectionArgs = new String[] {rowId};
                break;
            case COMMENT:
                builder.setTables(DatabaseContract.CommentEntry.TABLE_NAME+DatabaseContract.CommentEntry.COMMENT_JOIN);
                if (TextUtils.isEmpty(sortOrder)){
                    sortOrder = DatabaseContract.CommentEntry.DEFAULT_SORT;
                }break;
            case COMMENT_ID:
                builder.setTables(DatabaseContract.CommentEntry.TABLE_NAME+DatabaseContract.CommentEntry.COMMENT_JOIN);
                rowId = uri.getPathSegments().get(1);
                selection = DatabaseContract.CommentEntry._ID+"=?";
                selectionArgs = new String[] {rowId};
                break;
            case UriMatcher.NO_MATCH:
                throw new IllegalArgumentException("La uri no encaja");
        }

        String query = builder.buildQuery(projection,selection,null,null,sortOrder,null);
        Log.i("Query",query);
        Cursor cursor = builder.query(sqLiteDatabase,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String mimeType = null;
        switch (uriMatcher.match(uri)){
            case USER:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.user";
                break;
            case USER_ID:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.user";
                break;
            case POST:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.post";
                break;
            case POST_ID:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.post";
                break;
            case COMMENT:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.comment";
                break;
            case COMMENT_ID:
                mimeType = "vnd.android.cursor.dir/vnd.limox.jesus.teambeta_sqlite.comment";
                break;
        }
        return mimeType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        long regId = 0;

        switch (uriMatcher.match(uri)){
            case USER:
                regId = sqLiteDatabase.insert(DatabaseContract.UserEntry.TABLE_NAME,null,values);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
            case POST:
                String tableName = DatabaseContract.PostEntry.TABLE_NAME;
                regId = sqLiteDatabase.insert(tableName,null,values);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
            case COMMENT:
                regId = sqLiteDatabase.insert(DatabaseContract.CommentEntry.TABLE_NAME,null,values);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
        }

        if (regId != -1){
            //Notificar a los observadores que se ha moificado una uri
            getContext().getContentResolver().notifyChange(newUri,null);
        }else {
            throw new SQLException(getContext().getResources().getString(R.string.error_insert));
        }

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Uri newUri = null;
        int regId = 0;
        switch (uriMatcher.match(uri)){
            case USER:
                regId = sqLiteDatabase.delete(DatabaseContract.UserEntry.TABLE_NAME,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
            case POST:
                regId = sqLiteDatabase.delete(DatabaseContract.PostEntry.TABLE_NAME,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
            case COMMENT:
                regId = sqLiteDatabase.delete(DatabaseContract.CommentEntry.TABLE_NAME,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,regId);
                break;
        }
        if (regId!= -1){
            //Notificar a los observadores que se ha moificado una uri
            getContext().getContentResolver().notifyChange(newUri,null);
        }else {
            throw new SQLException(getContext().getResources().getString(R.string.error_delete));
        }

        return regId;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int affected = 0;
        Uri newUri = null;
        String rowId;
        Log.e("Uri",uri.toString());
        switch (uriMatcher.match(uri)){
            case USER:
                affected = sqLiteDatabase.update(DatabaseContract.UserEntry.TABLE_NAME,values,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,affected);
                break;
            case USER_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);
                selection=DatabaseContract.UserEntry._ID+"=?";
                selectionArgs = new String[]{rowId};
                affected = sqLiteDatabase.update(DatabaseContract.UserEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case POST:
                affected = sqLiteDatabase.update(DatabaseContract.PostEntry.TABLE_NAME,values,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,affected);
                break;
            case POST_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);
                selection=DatabaseContract.PostEntry._ID+"=?";
                selectionArgs = new String[]{rowId};
                affected = sqLiteDatabase.update(DatabaseContract.PostEntry.TABLE_NAME,values,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,affected);
                break;
            case COMMENT:
                affected = sqLiteDatabase.update(DatabaseContract.CommentEntry.TABLE_NAME,values,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,affected);
                break;
            case COMMENT_ID:
                //rowId = uri.getLastPathSegment();
                rowId = uri.getPathSegments().get(1);
                selection=DatabaseContract.CommentEntry._ID+"=?";
                selectionArgs = new String[]{rowId};
                affected = sqLiteDatabase.update(DatabaseContract.CommentEntry.TABLE_NAME,values,selection,selectionArgs);
                newUri = ContentUris.withAppendedId(uri,affected);
                break;
        }
        // if (affected > 0){
        //Notificar a los observadores que se ha moificado una uri
        getContext().getContentResolver().notifyChange(newUri,null);
        /*}else {
            throw new SQLException(getContext().getResources().getString(R.string.error_update));
        }*/

        return affected;
    }


}
