package com.zz.lamp.business.control.mvp.presenter;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class LightControlPresenter extends MyBasePresenterImpl<Contract.IGetLightControlView> implements Contract.IsetLightControlPresenter {

    public LightControlPresenter(Contract.IGetLightControlView view) {
        super(view);
    }


    @Override
    public void getLightList(String id,Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).getRealTimeCtrlLightDeviceList(id,params), new RequestObserver<JsonT<List<LightDeviceConBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<LightDeviceConBean>> data) {
                if (data.isSuccess()) {
                    view.showLightList(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<LightDeviceConBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void realTimeCtrLight(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).realTimeCtrlLightDevice(params), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showIntent();
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
}
