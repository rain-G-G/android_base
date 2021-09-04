package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ScanAppNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_app_name);
        ApplicationInfo applicationInfo = null;
        try {
            PackageManager packageManager = getPackageManager();
            List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                applicationInfo = packageManager.getApplicationInfo(packageInfo.packageName, 0);
                String appName = (String) packageManager.getApplicationLabel(applicationInfo);
                String versionName = packageInfo.versionName;
                int versionCode = packageInfo.versionCode;
                String packageName = packageInfo.packageName;
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    Log.v("Rain", "FLAG_SYSTEM appName = " + appName + " packageName = " + packageName);
                    continue;
                }
                Log.v("Rain", "application appName = " + appName);
            }
        } catch (Exception e) {
            Log.v("WANGXIN", "获取应用包信息失败：" + e);
        }
        Drawable drawable = getIconFromPackageName("com.cadothy.xlive", getBaseContext());
        ImageView imageView = null;
        imageView.setImageDrawable(drawable);

        //drawableToFile(drawable, Environment.getExternalStorageDirectory().getPath() + File.separator + "wangxin", Bitmap.CompressFormat.JPEG);
        Drawable drawable1 = imageView.getDrawable();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wx);
        saveBitmap(bitmap, "p5.jpg", Bitmap.CompressFormat.JPEG);

        String id = "channel id";
        String name = "name";
        //Toast.makeText(getBaseContext(), "size = " +  uri.getPathSegments(), Toast.LENGTH_LONG).show();
        NotificationChannel notificationChannel = null;
        NotificationManager notionManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            notionManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channelId1")
                .setChannelId(id)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.weixin))
                .setSmallIcon(R.id.image);//小图标
        notionManager.notify(111, mBuilder.build());

    }

    public void saveBitmap(Bitmap bitmap, String name, Bitmap.CompressFormat format) {
        // 创建一个位于SD卡上的文件
        File file = new File(Environment.getExternalStorageDirectory(),
                name);
        Toast.makeText(getBaseContext(), "file = " + file, Toast.LENGTH_LONG).show();
        FileOutputStream out = null;
        try{
            // 打开指定文件输出流
            out = new FileOutputStream(file);
            // 将位图输出到指定文件
            bitmap.compress(format, 100,
                    out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Drawable getIconFromPackageName(String packageName, Context context) {

        PackageManager pm = context.getPackageManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {

            try {

                PackageInfo pi = pm.getPackageInfo(packageName, 0);

                Context otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);

                int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};

                for (int displayMetric : displayMetrics) {

                    try {

                        Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);

                        if (d != null) {

                            return d;

                        }

                    } catch (Resources.NotFoundException e) {

                        continue;

                    }

                }

            } catch (Exception e) {

// Handle Error here

            }

        }

        ApplicationInfo appInfo = null;

        try {

            appInfo = pm.getApplicationInfo(packageName, 0);

        } catch (PackageManager.NameNotFoundException e) {

            return null;

        }

        return appInfo.loadIcon(pm);

    }
}
