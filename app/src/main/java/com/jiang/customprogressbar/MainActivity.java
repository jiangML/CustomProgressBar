package com.jiang.customprogressbar;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBar pb;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pb.setProgress(msg.what);
            if(msg.what<100)
            {
                handler.sendEmptyMessageDelayed(msg.what++,200);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(CustomProgressBar)findViewById(R.id.pb);
        handler.sendEmptyMessageDelayed(0,100);
    }



}
