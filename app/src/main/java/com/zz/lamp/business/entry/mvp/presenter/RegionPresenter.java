package com.zz.lamp.business.entry.mvp.presenter;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class RegionPresenter extends MyBasePresenterImpl<Contract.IGetRegionlView> implements Contract.IsetRegionPresenter {

    public RegionPresenter(Contract.IGetRegionlView view) {
        super(view);
    }

    @Override
    public void getAreaList(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).getAreaList(params), new RequestObserver<JsonT<List<RegionExpandItem>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<RegionExpandItem>> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<RegionExpandItem>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void postArea(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).postArea(params), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showPostIntent();
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

    @Override
    public void deleteArea(String id) {
        RxNetUtils.request(getCApi(ApiService.class).checkDelete(id), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    delete(id);
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

    public void delete(String id) {
        RxNetUtils.request(getCApi(ApiService.class).areaDelete(id), new RequestObserver<JsonT>(this) {
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
