package com.zz.lamp.business.main.mvp.presenter;

import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.business.main.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by .
 */

public class MapPresenter extends MyBasePresenterImpl<Contract.IGetMapView> implements Contract.IsetMapPresenter {

    public MapPresenter(Contract.IGetMapView view) {
        super(view);
    }



    @Override
    public void submitData(Map<String, Object> map) {
//        RxNetUtils.request(getCApi(ApiService.class).handleLightAlarm(map), new RequestObserver<JsonT>(this) {
//            @Override
//            protected void onSuccess(JsonT data) {
//                if (data.isSuccess()) {
//                    view.showResult();
//                }else {
//
//                }
//            }
//
//            @Override
//            protected void onFail2(JsonT userInfoJsonT) {
//                super.onFail2(userInfoJsonT);
//                view.showToast(userInfoJsonT.getMessage());
//            }
//        },mDialog);
    }

    @Override
    public void getData(Map<String, Object> map) {
        RxNetUtils.request(getCApi(ApiService.class).getMapList(map), new RequestObserver<JsonT<List<MapListBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<MapListBean>> data) {
                if (data.isSuccess()) {
                    view.showDetailResult(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<MapListBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

}

