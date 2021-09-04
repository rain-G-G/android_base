package com.example.myapplication.webtrc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity2 extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.v("wangxin1", "w = " + getApplicationContext().getResources().getDisplayMetrics().widthPixels + " h = " + getApplicationContext().getResources().getDisplayMetrics().heightPixels );

    }
}