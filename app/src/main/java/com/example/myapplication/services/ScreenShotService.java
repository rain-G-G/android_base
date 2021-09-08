package com.example.myapplication.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.utils.ImageSaveUtil;

import java.nio.ByteBuffer;

public class ScreenShotService extends Service {
    private int mResultCode;
    private Intent mResultData;
    private MediaProjection mediaProjection;
    private int mWindowWidth;
    private int mWindowHeight;
    private int mWindowDensity;
    private ImageReader imageReader;
    private MediaProjectionManager projectionManager;
    Image image;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int mResultCode = intent.getIntExtra("code", -1);
        mResultData = intent.getParcelableExtra("data");
        Log.v("wangxin1", "res = " + mResultData.getData());

        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        mediaProjection = projectionManager.getMediaProjection(mResultCode,  mResultData);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mWindowDensity = metrics.densityDpi;
        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWindowWidth= size.x;
        mWindowHeight= size.y;


                //Log.v("wangxin1","width: "+mWindowWidth+ "   height：" +mWindowHeight);
        // start capture reader
        //mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGB_8888, 2);
        imageReader = ImageReader.newInstance(mWindowWidth, mWindowHeight, 0x1, 2);
        //mVirtualDisplay = mMediaProjection.createVirtualDisplay(SCREENCAP_NAME, mWidth, mHeight, mDensity, VIRTUAL_DISPLAY_FLAGS, mImageReader.getSurface(), null, null);
        mediaProjection.createVirtualDisplay("screencap", mWindowWidth, mWindowHeight, 400,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR , imageReader.getSurface(), null, null);

        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader imageReader) {
                image = imageReader.acquireLatestImage();
                if (image != null) {
                    Log.v("wangxin1","width: "+ image.getWidth()+ "   height：" + image.getHeight());
                    image.close();
                }
            }
        }, null);
        //int w = imageReader.getWidth();
        //Log.v("wangxin1"," h " + w);
        return super.onStartCommand(intent, flags, startId);

    }

    private void saveJpeg(Image image,String name) {

        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();

        int rowPadding = rowStride - pixelStride * image.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(image.getWidth() + rowPadding / pixelStride, image.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        ImageSaveUtil.saveBitmap2file(bitmap, getApplicationContext(), name);
    }


    private void createNotificationChannel() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, ScreenShotService.class); //点击后跳转的界面，可以设置跳转数据

        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                //.setContentTitle("SMI InstantView") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("运行中......") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        /*以下是对Android 8.0的适配*/
        //普通notification适配
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("notification_id");
        }
        //前台服务notification适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build(); // 获取构建好的Notification
        //notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageReader.close();
    }
}
