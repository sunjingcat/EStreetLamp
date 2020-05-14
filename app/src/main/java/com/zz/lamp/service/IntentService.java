package com.zz.lamp.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zz.lamp.bean.PushBean;
import com.zz.lib.commonlib.utils.CacheUtility;

public class IntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看 SDK demo
        Log.e(TAG, "onReceiveMessageData -> " + msg);
    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        CacheUtility.spSave("cId",clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG, "onReceiveMessageData -> " + cmdMessage);
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onNotificationMessageArrived -> " + msg);
        String content = msg.getContent();
        PushBean pushBean = new Gson().fromJson(content, PushBean.class);
        Log.e(TAG, "content -> " + content);
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        Log.e(TAG, "onNotificationMessageClicked -> " + msg);
        String content = msg.getContent();
        PushBean pushBean = new Gson().fromJson(content, PushBean.class);
        if (pushBean.getModel().equals("alarm")){
//            startActivity(new Intent());
        }
        Log.e(TAG, "content -> " + content);
    }
}