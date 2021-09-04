package com.example.myapplication.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.myapplication.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String id = "channel id";
        String name = "通知类别" ;
        NotificationChannel notificationChannel = null;
        NotificationManager notionManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            notionManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, id)
                .setChannelId(id) //8.0up need to channelId
                .setContentTitle("title")
                .setContentText("ContentText")
                .setContentIntent(contentIntent)
                .setAutoCancel(true) //clicked will dismiss
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.weixin)))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weixin))
                .addAction(R.drawable.weixin, "button", contentIntent) //most can add 4 ge
                //.setProgress(100, 30, true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                //.setTimeoutAfter(2000)
                .setSmallIcon(R.mipmap.ic_launcher);//小图标/home/wangxin/图片/moji.jpg

        Drawable drawable = getDrawable(R.drawable.pause);

        drawable.getMinimumWidth();
        /*
            public static final int TRANSLUCENT = -3; 半透明
            public static final int TRANSPARENT = -2;
            public static final int OPAQUE = -1; no 透明
         */
        Toast.makeText(getBaseContext(), "color = " + drawable.getIntrinsicHeight(), Toast.LENGTH_LONG).show();

        notionManager.notify(111, mBuilder.build());
        doDiyNotification();

    }

    private void doDiyNotification(){
        NotificationManager notionManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chanel = new NotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
            notionManager.createNotificationChannel(chanel);
        }
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.custom_notification_item);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.custom_notification_large);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "my_channel_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager notionManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notionManager.cancel(111);
    }

}