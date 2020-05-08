package com.zz.lamp.business.alarm.mvp.presenter;

import com.zz.lamp.bean.AlarmBean;
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
    public void submitData(String id,Map<String, Object> map,String [] handleFile) {
        RxNetUtils.request(getCApi(ApiService.class).handleLightAlarm(id,map,handleFile), new RequestObserver<JsonT>(this) {
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

}

