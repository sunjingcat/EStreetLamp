package com.zz.lamp.utils;
import android.content.Context;
import android.util.Base64;
import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class JsonCompress {

    // 压缩
    public static String zipString(String unzip) {

        Deflater deflater = new Deflater(9); // 0 ~ 9 压缩等级 低到高

        deflater.setInput(unzip.getBytes());

        deflater.finish();

        final byte[] bytes = new byte[256];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);

        while (!deflater.finished()) {
            int length = deflater.deflate(bytes);
            outputStream.write(bytes, 0, length);
        }

        deflater.end();

        return Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);

    }

	// 解压缩
    public static String unzipString(String zip) throws IOException {

        byte[] decode = decode = Base64.decode(zip,Base64.DEFAULT);
        Inflater inflater = new Inflater();
        inflater.setInput(decode);
        final byte[] bytes = new byte[256];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        try {
            while (!inflater.finished()) {
                int length = inflater.inflate(bytes);
                outputStream.write(bytes, 0, length);
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
            return null;
        } finally {
            inflater.end();
        }

        return outputStream.toString();
    }
    public static String getFromAssets(Context context, String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
