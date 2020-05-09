package com.zz.lamp;

import android.os.Handler;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.ezvizuikit.open.EZUIKit;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.net.URLs;
import com.zz.lib.core.BaseApplication;

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
        if (!BuildConfig.LOG_DEBUG) {
            Logger.clearLogAdapters();
        }
        SDKInitializer.initialize(this);

        SDKInitializer.setCoordType(CoordType.BD09LL);

        EZUIKit.initWithAppKey(this, "5edf4821985d4127ad7489b50b21b166");

        PushManager.getInstance().initialize(this);
        PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
            @Override
            public void log(String s) {
                Log.i("PUSH_LOG",s);
            }
        });
    }

    public static App getmApplication() {
        return mApplication;
    }

    /**
     * 全局的Handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

}
