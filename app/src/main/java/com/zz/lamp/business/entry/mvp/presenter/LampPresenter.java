package com.zz.lamp.business.entry.mvp.presenter;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class LampPresenter extends MyBasePresenterImpl<Contract.IGetLampView> implements Contract.IsetLampPresenter {

    public LampPresenter(Contract.IGetLampView view) {
        super(view);
    }
    @Override
    public void getLampList(Map<String, Object> params,String id) {
        RxNetUtils.request(getCApi(ApiService.class).getLightDeviceList(params,id), new RequestObserver<JsonT<List<LightDevice>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<LightDevice>> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<LightDevice>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getTerminalDetail(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getTerminalDetail(id), new RequestObserver<JsonT<ConcentratorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ConcentratorBean> data) {
                if (data.isSuccess()) {
                    view.showTerminalDetail(data.getData());
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
}
