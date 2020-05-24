package com.zz.lamp.business.entry.mvp.presenter;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.List;
import java.util.Map;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class LampDetailPresenter extends MyBasePresenterImpl<Contract.IGeLampDetailView> implements Contract.IsetLampDetailPresenter {

    public LampDetailPresenter(Contract.IGeLampDetailView view) {
        super(view);
    }


    @Override
    public void getLightDetail(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getLampDetail(id), new RequestObserver<JsonT<LightDevice>>(this) {
            @Override
            protected void onSuccess(JsonT<LightDevice> data) {
                if (data.isSuccess()) {
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
    public void deleteLight(String id) {
        RxNetUtils.request(getCApi(ApiService.class).lightDelete(id), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showDeleteIntent();
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
