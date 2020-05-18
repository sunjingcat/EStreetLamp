package com.zz.lamp.business.entry.mvp.presenter;

import android.text.TextUtils;

import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LampAddPresenter extends MyBasePresenterImpl<Contract.IGetLampAddView> implements Contract.IsetLampAddPresenter {

    public LampAddPresenter(Contract.IGetLampAddView view) {
        super(view);
    }

    @Override
    public void postTerminal(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).postLamp(params), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showIntent();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void getLightDeviceType() {
        RxNetUtils.request(getCApi(ApiService.class).getLightDeviceTypet(), new RequestObserver<JsonT<List<DeviceType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DeviceType>> data) {
                if (data.isSuccess()) {
                    view.showLightDeviceType(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<DeviceType>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void getLightType() {
        Map<String, Object> map = new HashMap<>();
        map.put("dictType", "light_type");
        RxNetUtils.request(getCApi(ApiService.class).getLighTypet(map), new RequestObserver<JsonT<List<DictBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DictBean>> data) {
                if (data.isSuccess()) {
                    view.showLightType(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<DictBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void getLightPoleType() {
        Map<String, Object> map = new HashMap<>();
        map.put("dictType", "light_pole_type");
        RxNetUtils.request(getCApi(ApiService.class).getLighTypet(map), new RequestObserver<JsonT<List<DictBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DictBean>> data) {
                if (data.isSuccess()) {
                    view.showLightPoleType(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<DictBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void checkDeviceAddr(String id, Map<String, Object> params) {
        if (TextUtils.isEmpty(id)) {
            RxNetUtils.request(getCApi(ApiService.class).checkDeviceAddr(params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckAddrIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckAddrIntent(userInfoJsonT);
                }
            }, mDialog);
        } else {
            RxNetUtils.request(getCApi(ApiService.class).checkDeviceAddr(id, params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckAddrIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckAddrIntent(userInfoJsonT);
                }
            }, mDialog);
        }
    }

    @Override
    public void checkDeviceName(String id, Map<String, Object> params) {
        if (TextUtils.isEmpty(id)) {
            RxNetUtils.request(getCApi(ApiService.class).checkDeviceName(params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckNameIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckNameIntent(userInfoJsonT);
                }
            }, mDialog);
        } else {
            RxNetUtils.request(getCApi(ApiService.class).checkDeviceName(id, params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckNameIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckNameIntent(userInfoJsonT);
                }
            }, mDialog);
        }
    }


}
