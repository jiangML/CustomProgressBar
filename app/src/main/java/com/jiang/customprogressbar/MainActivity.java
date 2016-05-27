package com.jiang.customprogressbar;

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
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(CustomProgressBar)findViewById(R.id.pb);
        a();
    }




    private void a()
    {
       new Thread(){
           @Override
           public void run() {
               super.run();
               Message msg=handler.obtainMessage();
               try{
                   for(int i=0;i<=100;i++)
                   {
                       msg.what=i;
                       Thread.sleep(1000);
                       msg.sendToTarget();
                   }
               }catch (InterruptedException e){

               }

           }
       }.start();
    }


}
