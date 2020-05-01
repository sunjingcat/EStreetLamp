package com.zz.lamp.business.entry.mvp.presenter;

import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.UsableCode;
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

public class LinePresenter extends MyBasePresenterImpl<Contract.IGetLineView> implements Contract.IsetLinePresenter {

    public LinePresenter(Contract.IGetLineView view) {
        super(view);
    }

    @Override
    public void getLineList(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getLineList(id), new RequestObserver<JsonT<List<LineBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<LineBean>> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
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
    public void getUsableCode(String id) {
            RxNetUtils.request(getCApi(ApiService.class).getUsableCode(id), new RequestObserver<JsonT<String[]>>(this) {
                @Override
                protected void onSuccess(JsonT<String[]> data) {
                    if (data.isSuccess()) {
                        view.showUsableCode(data.getData());
                    }else {
                    }
                }

                @Override
                protected void onFail2(JsonT<String[]> userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showToast(userInfoJsonT.getMessage());
                }
            }, mDialog);

    }

    @Override
    public void postLine(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).postLine(params), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showPostIntent();
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
}
