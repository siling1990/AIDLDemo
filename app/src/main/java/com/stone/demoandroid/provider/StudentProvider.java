package com.stone.demoandroid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stone.demoandroid.db.DbOpenHelper;

/**
 * Created by Stone on 2018/8/16.
 */
public class StudentProvider extends ContentProvider {

    private static final String TAG = "StudentProvider";

    public static final String AUTHORITY = "com.stone.demoandroid.provider";
    public static final Uri STUDENT_CONTENT_URI = Uri.parse(" content://" + AUTHORITY + "/student");
    public static final Uri USER_CONTENT_URI = Uri.parse(" content://" + AUTHORITY + "/user");
    public static final int STUDENT_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, " student", STUDENT_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, " user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG, " onCreate, current thread:" + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.STUDENT_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into student values( 3,'Alice');");
        mDb.execSQL("insert into student values( 4,'Bob');");
        mDb.execSQL("insert into student values( 5,'Charle');");
        mDb.execSQL("insert into user values( 1,'jake', 1);");
        mDb.execSQL("insert into user values( 2,'jasmine', 0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, " query, current thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException(" Unsupported URI: " + uri);
        }
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, " getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, " insert");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException(" Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, " delete");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException(" Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, " update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException(" Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case STUDENT_URI_CODE:
                tableName = DbOpenHelper.STUDENT_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}
