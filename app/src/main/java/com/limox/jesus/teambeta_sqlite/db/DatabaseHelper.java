package com.limox.jesus.teambeta_sqlite.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.limox.jesus.teambeta_sqlite.TeamBetaApplication;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class has the database methods to create and modify
 * Created by jesus on 1/03/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "teambeta.db";
    private static volatile DatabaseHelper mInstance;
    private AtomicInteger mAIOpenCounter;
    private SQLiteDatabase mDatabase;

    public static synchronized DatabaseHelper getInstance(){
        if (mInstance == null){
            mInstance = new DatabaseHelper();
        }
        return mInstance;
    }

    private DatabaseHelper() {
        super(TeamBetaApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        mAIOpenCounter = new AtomicInteger();
    }

    public synchronized SQLiteDatabase openDatabase(){
        if (mAIOpenCounter.incrementAndGet() == 1){
            mDatabase = open();
        }
        return mDatabase;
    }
    public synchronized void closeDatabase(){
        if (mAIOpenCounter.decrementAndGet() == 0){
            mDatabase.close();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            db.setForeignKeyConstraintsEnabled(true);
        }else
            db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(DatabaseContract.UserEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.UserEntry.SQL_INSERT_ENTRIES);
            db.execSQL(DatabaseContract.PostEntry.SQL_CREATE_ENTRIES);
            Log.i("post",DatabaseContract.PostEntry.SQL_INSERT_ENTRIES);
            db.execSQL(DatabaseContract.PostEntry.SQL_INSERT_ENTRIES);
            db.execSQL(DatabaseContract.CommentEntry.SQL_CREATE_ENTRIES);

            db.setTransactionSuccessful();
        }catch (SQLiteException e){
            Log.e("Error al crear bd","Error al crear la base de datos: "+e.getMessage());
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.beginTransaction();
        try {
            db.execSQL(DatabaseContract.UserEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.PostEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.CommentEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
            db.setTransactionSuccessful();
        }catch (SQLiteException e) {
            Log.e("Error al actualizar bd", "Error al actualizar la base de datos: " + e.getMessage());
        }
        catch (Exception e){
            Log.e("Errooor",e.getMessage());
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);

    }

    private SQLiteDatabase open(){
        return getWritableDatabase();
    }

}
