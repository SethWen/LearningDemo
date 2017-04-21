package com.djsz.sqldemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * author: Shawn
 * time  : 2017/4/6 15:31
 * desc  :
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "aaaaa";

    public static final String CREATE_NEWS = "create table news ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "publishdate integer,"
            + "commentcount integer)";

    public static final String CREATE_COMMENT = "create table comment ("
            + "id integer primary key autoincrement, "
            + "content text)";

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
        db.execSQL(CREATE_COMMENT);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: oldVersion = " + oldVersion + "--newVersion = " + newVersion);
        switch (newVersion) {
            case 1:
                db.execSQL(CREATE_COMMENT);
                break;
            case 2:
                db.execSQL("alter table comment add column publishdate integer");
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
