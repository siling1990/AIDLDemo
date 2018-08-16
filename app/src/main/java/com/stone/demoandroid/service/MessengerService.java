package com.stone.demoandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Stone on 2018/8/16.
 */
public class MessengerService extends Service {


    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler

    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                Log.i(TAG, "receive msg from Client:" + msg.getData().getString("msg"));
                break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger=new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
