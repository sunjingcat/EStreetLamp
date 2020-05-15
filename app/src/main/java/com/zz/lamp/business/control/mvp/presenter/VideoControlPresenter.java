package com.zz.lamp.business.control.mvp.presenter;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.YsConfig;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class VideoControlPresenter extends MyBasePresenterImpl<Contract.IGetVideoControlView> implements Contract.IsetVideoControlPresenter {

    public VideoControlPresenter(Contract.IGetVideoControlView view) {
        super(view);
    }


    @Override
    public void getYsConfig() {
        RxNetUtils.request(getCApi(ApiService.class).getYsConfig(), new RequestObserver<JsonT<YsConfig>>(this) {
            @Override
            protected void onSuccess(JsonT<YsConfig> data) {
                if (data.isSuccess()) {
                    view.showYsConfig(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<YsConfig> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

    @Override
    public void startControl(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).startControl(params), new RequestObserver<JsonT>(this) {
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
//                view.showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

    @Override
    public void shopControl(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).stopControl(params), new RequestObserver<JsonT>(this) {
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
//                view.showToast(userInfoJsonT.getMessage());
            }
        }, null);

    }
}
