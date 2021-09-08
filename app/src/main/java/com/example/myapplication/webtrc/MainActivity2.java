package com.example.myapplication.webtrc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {
    private final static String TAG = "wangxin222";
    String command = "am broadcast -a ADB_INPUT_TEXT --es msg mrk蛇魔你好!@#$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.doBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleKeyBroadCmd(command);
            }
        });
    }

    private void handleKeyBroadCmd(String cmd) {
        if (cmd == null) {
            return;
        }
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(cmd);
            InputStream errorInput = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String error = "";
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(errorInput));
            while ((line = bufferedReader.readLine()) != null) {
                error += line;
            }
            if (result.equals("Success")) {
                Log.v(TAG, " install: Success");
            } else {
                Log.v(TAG, " install: error" + error + ";result=" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "install: error" + e);
        }
    }


}

