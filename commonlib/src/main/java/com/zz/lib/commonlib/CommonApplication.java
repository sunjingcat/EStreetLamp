package com.zz.lib.commonlib;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2018/4/21.
 */

public class CommonApplication extends Application {
    public static AppCompatActivity activity;
    private static Context context;

    public static Context getAppContext() {
        return CommonApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApplication.context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());

    }
}
