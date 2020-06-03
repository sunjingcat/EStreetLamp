package com.zz.lamp.business.alarm.mvp.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.business.alarm.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by .
 */

public class AlarmAddPresenter extends MyBasePresenterImpl<Contract.IGetAlarmAddView> implements Contract.IsetAlarmAddPresenter {

    public AlarmAddPresenter(Contract.IGetAlarmAddView view) {
        super(view);
    }



    @Override
    public void submitData(String id, String alarmStatus, String handleDescription,  String handleFile) {
        if (TextUtils.isEmpty(handleFile)){
            submit(id,alarmStatus,handleDescription,"");
        }else {
            RxNetUtils.request(getCApi(ApiService.class).uploadImgs(handleFile), new RequestObserver<JsonT<List<Integer>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<Integer>> data) {
                    if (data.isSuccess()) {
                        submit(id, alarmStatus, handleDescription, new Gson().toJson(data.getData()));
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT<List<Integer>> userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showToast(userInfoJsonT.getMessage());
                }
            }, mDialog);
        }
    }
    public void submit(String id, String alarmStatus, String handleDescription,  String handleFile) {


        RxNetUtils.request(getCApi(ApiService.class).handleLightAlarm(id,alarmStatus,handleDescription,handleFile), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showResult();
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getData(Map<String, Object> map) {
        RxNetUtils.request(getCApi(ApiService.class).getLightAlarmById(map), new RequestObserver<JsonT<AlarmBean>>(this) {
            @Override
            protected void onSuccess(JsonT<AlarmBean> data) {
                if (data.isSuccess()) {
                    view.showDetailResult(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<AlarmBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getImage(String type,String modelId) {
        RxNetUtils.request(getCApi(ApiService.class).getImageBase64(type,modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    view.showImage(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

}

