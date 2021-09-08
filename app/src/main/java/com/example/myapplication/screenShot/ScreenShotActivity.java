package com.example.myapplication.screenShot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.services.ScreenShotService;

public class ScreenShotActivity extends AppCompatActivity {
    private static final int REQUEST_MEDIA_PROJECTION = 999;
    private MediaProjectionManager projectionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_direction);

        //requestScreenShotPmission();
    }

    public void requestScreenShotPmission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图
            return;
        }
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(projectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_MEDIA_PROJECTION:
                Toast.makeText(getApplicationContext(), "getMediaProjection", Toast.LENGTH_LONG).show();
                if (resultCode != RESULT_OK){
                    return;
                }else {
                    Intent intent = new Intent(this, ScreenShotService.class);
                    intent.putExtra("code", resultCode);
                    intent.putExtra("data", data);
                    Log.v("wangxin1", "getData = " + data.getExtras().toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, ScreenShotService.class);
        stopService(intent);
    }
}