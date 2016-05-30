package com.jiang.customprogressbar;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    private CustomProgressBar pb;
    private CustomProgressBar pb1;
    private CustomProgressBar pb2;
    private CustomProgressBar pb3;
    private CustomProgressBar pb4;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress =pb.getProgress();
            pb.setProgress(++progress);

            int progress1=pb1.getProgress();
            pb1.setProgress(++progress1);

            int progress2=pb2.getProgress();
            pb2.setProgress(++progress2);

            int progress3=pb3.getProgress();
            pb3.setProgress(++progress3);

            int progress4=pb4.getProgress();
            pb4.setProgress(++progress4);

            if(progress>=100)
            {
                handler.removeMessages(0x100);
            }else{
                handler.sendEmptyMessageDelayed(0x100,100);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(CustomProgressBar)findViewById(R.id.pb);
        pb1=(CustomProgressBar)findViewById(R.id.pb1);
        pb2=(CustomProgressBar)findViewById(R.id.pb2);
        pb3=(CustomProgressBar)findViewById(R.id.pb3);
        pb4=(CustomProgressBar)findViewById(R.id.pb4);

        btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0x100);
            }
        });
    }



}
