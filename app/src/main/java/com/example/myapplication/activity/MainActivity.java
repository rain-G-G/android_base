package com.example.myapplication.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = "channel id";
        String name = "name" ;
        //Toast.makeText(getBaseContext(), "size = " +  uri.getPathSegments(), Toast.LENGTH_LONG).show();
        NotificationChannel notificationChannel = null;
        NotificationManager notionManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            notionManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channelId1")
                .setChannelId(id)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weixin))
                .setSmallIcon(R.drawable.notification_icon);//小图标
        notionManager.notify(111, mBuilder.build());
        Looper.myLooper();

        /**ImageView imageView = findViewById(R.id.image);
        Drawable drawable = imageView.getDrawable();
        drawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);

        Toast.makeText(getBaseContext(), "aaaaaaaaa",Toast.LENGTH_LONG).show();*/
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }
    };

}