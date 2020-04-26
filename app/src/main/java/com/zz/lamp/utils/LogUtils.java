package com.zz.lamp.utils;

import android.util.Log;

import com.zz.lamp.BuildConfig;


/**
 * Log统一管理类
 */
public class LogUtils {

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isDebug = BuildConfig.DEBUG;// 是否需要打印bug
    private static final String TAG = "building";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg + "");
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg + "");
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg + "");
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg + "");
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg + "");
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg + "");
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg + "");
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg + "");
    }

    // 下面是传入自定义tag，异常信息的函数
    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.d(tag, msg + "", tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.e(tag, msg + "", tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.i(tag, msg + "", tr);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.v(tag, msg + "", tr);
    }

    // 下面是传入自定义tag,超长日志分段打印的函数
    public static void longE(String tag, String content) {
        if (isDebug) {
            int p = 2000;
            long length = content.length();
            if (length < p || length == p)
                Log.e(tag, content + "");
            else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e(tag, content);
            }
        }
    }

    public static void longI(String tag, String content) {
        if (isDebug) {
            int p = 2000;
            long length = content.length();
            if (length < p || length == p)
                Log.i(tag, content + "");
            else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.i(tag, logContent);
                }
                Log.i(tag, content);
            }
        }
    }

    public static void longD(String tag, String content) {
        if (isDebug) {
            int p = 2000;
            long length = content.length();
            if (length < p || length == p)
                Log.d(tag, content + "");
            else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.d(tag, logContent);
                }
                Log.d(tag, content);
            }
        }
    }

    public static void longV(String tag, String content) {
        if (isDebug) {
            int p = 2000;
            long length = content.length();
            if (length < p || length == p)
                Log.v(tag, content + "");
            else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.v(tag, logContent);
                }
                Log.v(tag, content);
            }
        }
    }
}
