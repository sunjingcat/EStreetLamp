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
        RxNetUtils.request(getApi(ApiService.class).sendMessage(), new RequestObserver<JsonT<UserBasicBean>>(this) {
            @Override
            protected void onSuccess(JsonT<UserBasicBean> jsonT) {
                view.showUserInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<UserBasicBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, null);
    }
}

