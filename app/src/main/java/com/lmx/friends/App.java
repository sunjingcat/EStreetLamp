package com.lmx.friends;
import android.os.Handler;

import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import com.lmx.friends.net.RxNetUtils;
import com.lmx.friends.net.URLs;
import com.lmx.lib.core.BaseApplication;

import com.orhanobut.logger.Logger;


public class App extends BaseApplication {
    public static AppCompatActivity context;
    public static App mApplication;

    private static Handler mHandler;  // 全局的Handler 可以防止内存泄露

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mApplication = this;
        mHandler = new Handler();
        RxNetUtils.init(this);
        RxNetUtils.defaultConfig(URLs.LMX);
        if(!BuildConfig.LOG_DEBUG){
            Logger.clearLogAdapters();
        }



    }

    public static App getmApplication() {
        return mApplication;
    }

}
