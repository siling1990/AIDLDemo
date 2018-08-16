package com.stone.demoandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.stone.demoandroid.entity.IStudentManager;
import com.stone.demoandroid.entity.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Stone on 2018/8/15.
 */
public class StudentManagerService extends Service {

    private static final String TAG = "StudentMS";
    private CopyOnWriteArrayList<Student> mStudentList = new CopyOnWriteArrayList<>();
    private Binder mBinder = new IStudentManager.Stub() {
        @Override
        public List<Student> getStudentList() throws RemoteException {

            synchronized (mStudentList) {
                return mStudentList;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {

            synchronized (mStudentList) {
                if (!mStudentList.contains(student)) {
                    mStudentList.add(student);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mStudentList.add(new Student(1, "Alice"));
        mStudentList.add(new Student(2, "Bob"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
