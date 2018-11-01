package com.stone.demoandroid;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button btAidl, btMessenger, btProvider;
    private ListView lvAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAidl = (Button) findViewById(R.id.bt_aidl);

        btAidl.setBackgroundResource(R.drawable.frame_animation);

        AnimationDrawable animationDrawable = (AnimationDrawable) btAidl.getBackground();

        //animationDrawable.start();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_animation);

        //btAidl.startAnimation(animation);

        btMessenger = findViewById(R.id.bt_messenger);
        btProvider = findViewById(R.id.bt_provider);

        btAidl.setOnClickListener(this);
        btMessenger.setOnClickListener(this);
        btProvider.setOnClickListener(this);

        lvAnim = findViewById(R.id.lv_anim);
        String[] data = {"Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lvAnim.setAdapter(adapter);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.animation_layout);
        LayoutAnimationController controller = new LayoutAnimationController(animation1);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);

        //lvAnim.setLayoutAnimation(controller);

        Window window;
        WindowManager windowManager = getWindowManager();
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
