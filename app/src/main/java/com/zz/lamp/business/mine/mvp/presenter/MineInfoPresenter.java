package com.zz.lamp.business.mine.mvp.presenter;


import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.mine.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.business.mine.mvp.Contract;

import static com.zz.lamp.net.RxNetUtils.getApi;

/**
 * Created by 77 on 2018/8/8.
 */

public class MineInfoPresenter extends MyBasePresenterImpl<Contract.IMineInfoView> implements Contract.IsetMineInfoPresenter {

    public MineInfoPresenter(Contract.IMineInfoView view) {
        super(view);
    }


    @Override
    public void getMineInfo() {
        RxNetUtils.request(getCApi(ApiService.class).getUserDetail(), new RequestObserver<JsonT<UserBasicBean>>(this) {
            @Override
            protected void onSuccess(JsonT<UserBasicBean> data) {
                if (data.isSuccess()) {
                    view.showUserInfo(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<UserBasicBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void logout() {
        RxNetUtils.request(getCApi(ApiService.class).logout(), new RequestObserver<JsonT>(this) {
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
                view.showToast(userInfoJsonT.getMessage());
                view.showIntent();
            }
        }, mDialog);
    }
}

