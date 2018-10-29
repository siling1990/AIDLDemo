package com.stone.demoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button btAidl, btMessenger, btProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAidl = findViewById(R.id.bt_aidl);
        btMessenger = findViewById(R.id.bt_messenger);
        btProvider = findViewById(R.id.bt_provider);

        btAidl.setOnClickListener(this);
        btMessenger.setOnClickListener(this);
        btProvider.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_aidl:
                gotoActivity(AidlActivity.class.getName());
                break;
            case R.id.bt_messenger:
                gotoActivity(MessengerActivity.class.getName());
                break;
            case R.id.bt_provider:
                gotoActivity(ProviderActivity.class.getName());
                break;
            default:
                break;
        }
    }

    private void gotoActivity(String className) {

        Intent intent = new Intent();
        intent.setClassName(this, className);
        startActivity(intent);
    }
}
