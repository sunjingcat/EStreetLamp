package com.zz.lamp.business.entry.mvp.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.bean.LightDevice;
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
        RxNetUtils.request(getCApi(ApiService.class).postLamp(params), new RequestObserver<JsonT<String>>(this) {
            @Override
            protected void onSuccess(JsonT<String> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<String> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showError(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void getLampEstimateForm(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getLampEstimateForm(id), new RequestObserver<JsonT<LightDevice>>(this) {
            @Override
            protected void onSuccess(JsonT<LightDevice> data) {
                if (data.isSuccess()&&data!=null) {
                    view.showLightDetail(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<LightDevice> userInfoJsonT) {
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

    @Override
    public void postImage(String id, String files,List<Integer> ids) {
        if (TextUtils.isEmpty(files)){
            if (ids.size()==0){
                view.showPostImage();
            }else {
                postImageIDs(id, new Gson().toJson(ids));
            }
        }else {
            RxNetUtils.request(getCApi(ApiService.class).uploadImgs(files), new RequestObserver<JsonT<List<Integer>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<Integer>> data) {
                    if (data.isSuccess()) {
                        ids.addAll(data.getData());
                        postImageIDs(id, new Gson().toJson(ids));
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

    public void postImageIDs(String id, String files) {
        RxNetUtils.request(getCApi(ApiService.class).uploadLightDeviceImgs(id, files), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showPostImage();
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
    public void getImage(String type, String modelId) {
        RxNetUtils.request(getCApi(ApiService.class).getImageBase64(type, modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    view.showImage(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }


}
