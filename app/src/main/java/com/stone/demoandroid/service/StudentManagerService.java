package com.stone.demoandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stone.demoandroid.entity.IOnNewStudentArrivedListener;
import com.stone.demoandroid.entity.IStudentManager;
import com.stone.demoandroid.entity.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Stone on 2018/8/15.
 */
public class StudentManagerService extends Service {

    private static final String TAG = "StudentMS";
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Student> mStudentList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IOnNewStudentArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewStudentArrivedListener>();
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

        @Override
        public void registerListener(IOnNewStudentArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, " already exists.");
            }
            Log.d(TAG, " registerListener, size:" + mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewStudentArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.d(TAG, " unregister listener succeed.");
            } else {
                Log.d(TAG, " not found, can not unregister.");
            }
            Log.d(TAG, " unregisterListener, current size:" + mListenerList.size());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mStudentList.add(new Student(1, "Alice"));
        mStudentList.add(new Student(2, "Bob"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }

    private void onNewBookArrived(Student student) throws RemoteException {

        mStudentList.add(student);
        Log.d(TAG, " onNewBookArrived, notify listeners:" + mListenerList.size());
        for (int i = 0; i < mListenerList.size(); i++) {
            IOnNewStudentArrivedListener listener = mListenerList.get(i);
            Log.d(TAG, " onNewBookArrived, notify listener:" + listener);
            listener.onNewStudentArrived(student);
        }
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            // do background processing here.....
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int studentId = mStudentList.size() + 1;
                Student newStudent = new Student(studentId, "StudentName" + studentId);
                try {
                    onNewBookArrived(newStudent);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
