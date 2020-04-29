package com.zz.lamp.utils;

import android.content.Context;
import android.util.Log;

import com.zz.lib.commonlib.utils.CacheUtility;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

public class PushUtils {
    public static void register(Context context){
        PushAgent mPushAgent = PushAgent.getInstance(context);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.v("====",deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                Log.v("====",s);
            }
        });
        UMConfigure.setLogEnabled(true);
        PushAgent.getInstance(context).onAppStart();
    }
    public static void setAlis(Context context){
        String userId = CacheUtility.getUserId();
        PushAgent.getInstance(context).addAlias(userId, "lamp", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
            }
        });
    }
    public static void deletetAlis(Context context){
        String userId = CacheUtility.getUserId();
        PushAgent.getInstance(context).deleteAlias(userId, "lamp", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
            }
        });
    }
}
