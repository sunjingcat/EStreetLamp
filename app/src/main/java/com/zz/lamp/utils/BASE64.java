package com.zz.lamp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class BASE64 {
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }
    public static String generateImage(String imgStr){
        if (imgStr == null) {
            return "";
        }

        // 文件输出流对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;

        try {
            byte[] decodeByte = Base64.decode(imgStr,Base64.DEFAULT);
            File photoFile = new File(Environment.getExternalStorageDirectory()+"/myphoto");
            if (!photoFile.exists()){
                photoFile.mkdirs();
            }

            File f = new File(photoFile,"upload.png");
            if(f.exists()){
                f.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            f.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(f);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(decodeByte);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
            return f.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return "";
    }

    public static String saveBitmap(Context context,String id, Bitmap bmp) {

        if (bmp==null)return "";
        FileOutputStream out;
        String bitmapName = "termial_"+id+".jpg";
        try { // 获取SDCard指定目录下
            String sdCardDir = context.getCacheDir() + "/zhongzhi/light/";
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, bitmapName);// 在SDcard的目录下创建图片文,以当前时间为其命名
            if(file.exists()){
                file.delete();
            }
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
//        Toast.makeText(HahItemActivity.this,"保存已经至"+Environment.getExternalStorageDirectory()+"/CoolImage/"+"目录文件夹下", Toast.LENGTH_SHORT).show();
    }
}
