package com.jiang.customprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(CustomProgressBar)findViewById(R.id.pb);
        a();
    }

    private void a()
    {
        for(int i=0;i<=100;i++)
        {
             pb.setProgress(i);
            try{
                Thread.sleep(1500);
            }catch (InterruptedException e)
            {

            }

        }
    }


}
