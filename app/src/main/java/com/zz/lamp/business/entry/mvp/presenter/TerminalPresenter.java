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

public class TerminalPresenter extends MyBasePresenterImpl<Contract.IGetTerminalView> implements Contract.IsetTerminalPresenter {

    public TerminalPresenter(Contract.IGetTerminalView view) {
        super(view);
    }

    @Override
    public void getTerminalList(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).getTerminalList(params), new RequestObserver<JsonT<List<ConcentratorBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ConcentratorBean>> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ConcentratorBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }
}
