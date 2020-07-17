package com.zz.lamp.business.main.mvp.presenter;

import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceKind;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.main.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by .
 */

public class MapPresenter extends MyBasePresenterImpl<Contract.IGetMapView> implements Contract.IsetMapPresenter {

    public MapPresenter(Contract.IGetMapView view) {
        super(view);
    }


    @Override
    public void deviceKindList() {
        RxNetUtils.request(getCApi(ApiService.class).getDeviceKindList(), new RequestObserver<JsonT<List<DeviceKind>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DeviceKind>> data) {
                if (data.isSuccess()) {
                    view.showDeviceKindList(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<DeviceKind>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }



    @Override
    public void getTerminalData(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getMapTerminalDetail(id), new RequestObserver<JsonT<ConcentratorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ConcentratorBean> data) {
                if (data.isSuccess()) {
                    view.showTerminalData(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<ConcentratorBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getLightDeviceData(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getMapLampDetail(id), new RequestObserver<JsonT<LightDevice>>(this) {
            @Override
            protected void onSuccess(JsonT<LightDevice> data) {
                if (data.isSuccess()) {
                    view.showLightDeviceData(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<LightDevice> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getData(Map<String, Object> map) {
        RxNetUtils.request(getCApi(ApiService.class).getMapList(map), new RequestObserver<JsonT<List<MapListBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<MapListBean>> data) {
                if (data.isSuccess()) {
                    view.showResult(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<MapListBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getUserInfoData() {
        RxNetUtils.request(getCApi(ApiService.class).getUserDetail(), new RequestObserver<JsonT<UserBasicBean>>(this) {
            @Override
            protected void onSuccess(JsonT<UserBasicBean> data) {
                if (data.isSuccess()) {
                    view.showUserInfo(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<UserBasicBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

}

