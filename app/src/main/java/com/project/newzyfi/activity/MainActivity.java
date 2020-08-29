package com.project.newzyfi.activity;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.newzyfi.R;


public class MainActivity extends AppCompatActivity  {

 public static final int TIME_TO_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);

            }
        },TIME_TO_DELAY);


    }


}
