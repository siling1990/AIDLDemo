package com.stone.demoandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.stone.demoandroid.service.MessengerService;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = " MessengerActivity";
    private TextView tvResult;
    private Messenger mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("msg", " hello, this is client.");
            msg.setData(data);
            //添加处理回复的Messenger
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        public void onServiceDisconnected(ComponentName className) {
        }
    };
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    private  class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Log.i(TAG, " receive msg from Service:" + msg.getData().getString(" reply"));
                    tvResult.append("\n receive msg from Service:" + msg.getData().getString(" reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        tvResult = findViewById(R.id.tv_result);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
