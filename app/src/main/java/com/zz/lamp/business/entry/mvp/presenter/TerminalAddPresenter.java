package com.zz.lamp.business.entry.mvp.presenter;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class TerminalAddPresenter extends MyBasePresenterImpl<Contract.IGetTerminalAddView> implements Contract.IsetTerminalAddPresenter {

    public TerminalAddPresenter(Contract.IGetTerminalAddView view) {
        super(view);
    }

    @Override
    public void postTerminal(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).postTerminal(params), new RequestObserver<JsonT>(this) {
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
