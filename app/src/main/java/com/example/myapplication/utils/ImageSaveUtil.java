package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaveUtil {

    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/MediaProjection/";

    public static void saveBitmap2file(Bitmap bmp, Context context, String num) {
        String savePath;
        String fileName = num + ".JPEG";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            //GYLog.log("wangxin1 "+  "保存失败");
            return ;
        }
        File filePic = new File(savePath + fileName);
        try {
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //GYLog.log("wangxin1" + "保存成功,位置:" + filePic.getAbsolutePath());
        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), filePic.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + savePath+fileName)));

    }



}
