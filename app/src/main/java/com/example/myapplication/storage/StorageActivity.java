package com.example.myapplication.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.File;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        File path = Environment.getDataDirectory();//这个方法是获取内部存储的根路径

        String pat = getFilesDir().getAbsolutePath();
        Toast.makeText(getBaseContext(), "path = " + pat, Toast.LENGTH_LONG).show();
    }
}