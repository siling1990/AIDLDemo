package com.stone.demoandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stone.demoandroid.entity.IOnNewStudentArrivedListener;
import com.stone.demoandroid.entity.IStudentManager;
import com.stone.demoandroid.entity.Student;
import com.stone.demoandroid.service.StudentManagerService;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IStudentManager mRemoteStudentManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, " receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

    };
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            IStudentManager studentManager = IStudentManager.Stub.asInterface(service);
            try {
                mRemoteStudentManager = studentManager;
                List<Student> list = studentManager.getStudentList();
                Log.i(TAG, " query book list, list type:" + list.getClass().getCanonicalName());
                Log.i(TAG, " query book list:" + list.toString());
                Student newBook = new Student(3, " Android 进阶");
                studentManager.addStudent(newBook);
                Log.i(TAG, " add book:" + newBook);
                List<Student> newList = studentManager.getStudentList();
                Log.i(TAG, " query book list:" + newList.toString());
                studentManager.registerListener(mOnNewStudentArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            mRemoteStudentManager = null;
        }
    };

    private IOnNewStudentArrivedListener mOnNewStudentArrivedListener = new IOnNewStudentArrivedListener.Stub() {

        @Override
        public void onNewStudentArrived(Student newStudent) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newStudent).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, StudentManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {

        if (mRemoteStudentManager != null && mRemoteStudentManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, " unregister listener:" + mOnNewStudentArrivedListener);
                mRemoteStudentManager.unregisterListener(mOnNewStudentArrivedListener);
            } catch (RemoteException e) {
            }
        }

        unbindService(mConnection);
        super.onDestroy();
    }

}
