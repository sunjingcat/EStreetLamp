package com.zz.lamp.business.control.mvp.presenter;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class TerminalControlPresenter extends MyBasePresenterImpl<Contract.IGetTerminalControlView> implements Contract.IsetTerminalControlPresenter {

    public TerminalControlPresenter(Contract.IGetTerminalControlView view) {
        super(view);
    }

    @Override
    public void getTerminalDetail(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getTerminalDetail(id), new RequestObserver<JsonT<ConcentratorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ConcentratorBean> data) {
                if (data.isSuccess()) {
                    view.showDetail(data.getData());
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
    public void getLineList(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getRealTimeCtrlLineList(id), new RequestObserver<JsonT<List<LineBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<LineBean>> data) {
                if (data.isSuccess()) {
                    view.showLineList(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<LineBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void realTimeCtrlLine(String id,Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).realTimeCtrlLine(id,params), new RequestObserver<JsonT>(this) {
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
