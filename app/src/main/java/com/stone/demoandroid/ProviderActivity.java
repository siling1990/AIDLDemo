package com.stone.demoandroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.stone.demoandroid.entity.Student;
import com.stone.demoandroid.entity.User;
import com.stone.demoandroid.provider.StudentProvider;

public class ProviderActivity extends AppCompatActivity {
    private static final String TAG = "ProviderActivity";

    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        tvResult = findViewById(R.id.tv_result);

        Uri uri = Uri.parse("content://com.stone.demoandroid.provider");
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);

        Uri StudentUri = StudentProvider.STUDENT_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "Delta");
        getContentResolver().insert(StudentUri, values);
        Cursor StudentCursor = getContentResolver().query(StudentUri, new String[]{"_id", "name"}, null, null, null);
        while (StudentCursor.moveToNext()) {
            Student student = new Student();
            student.id = StudentCursor.getInt(0);
            student.name = StudentCursor.getString(1);
            Log.d(TAG, " query Student:" + student.toString());
            tvResult.append("\nquery Student:" + student.toString());
        }
        StudentCursor.close();
        Uri userUri = StudentProvider.USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.id = userCursor.getInt(0);
            user.name = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.d(TAG, " query user:" + user.toString());
            tvResult.append("\nquery Student:" + user.toString());
        }
        userCursor.close();
    }
}
