package com.zz.lamp.business.login.mvp.presenter;

import com.igexin.sdk.PushManager;
import com.zz.lamp.bean.UserInfo;
import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.business.login.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.PushUtils;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.Map;

public class LoginPresenter extends MyBasePresenterImpl<Contract.IGetLoginView> implements Contract.IsetLoginPresenter {

    public LoginPresenter(Contract.IGetLoginView view) {
        super(view);
    }

    @Override
    public void getAddress(String authCode) {
        RxNetUtils.request(getApi(ApiService.class).getAddress(authCode), new RequestObserver<JsonT<IpAdress>>(this) {
//
            @Override
            protected void onSuccess(JsonT<IpAdress> jsonT) {
                if (jsonT.isSuccess()) {
                    String url= "http://"+jsonT.getData().getIp()+":"+jsonT.getData().getPort();
                    CacheUtility.saveURL(url);
                    CacheUtility.saveCode(jsonT.getData().getAuthcode());
                    view.setAuthCode(jsonT.getData());

                }else {
//                    view.showMessage(login_data.getMessage());
                }
            }
            @Override
            protected void onFail2(JsonT<IpAdress> ipAdressJsonT) {
                super.onFail2(ipAdressJsonT);
                view.showToast("获取IP失败");
            }
        },mDialog);

    }


    @Override
    public void setAccount(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).login(params), new RequestObserver<JsonT<UserInfo>>(this) {
            @Override
            protected void onSuccess(JsonT<UserInfo> login_data) {
                if (login_data.isSuccess()) {
                    CacheUtility.saveToken(login_data.getData().getLoginToken());
                    view.showIntent();
                }else {

                }
            }
            @Override
            protected void onFail2(JsonT<UserInfo> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);

    }



}
