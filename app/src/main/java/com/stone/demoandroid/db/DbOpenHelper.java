package com.stone.demoandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stone on 2018/8/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "student_provider.db";
    public static final String STUDENT_TABLE_NAME = "student";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VERSION = 1;
    // 图书 和 用户 信息 表
    private String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS " + STUDENT_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT)";
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT," + "sex INT)";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
