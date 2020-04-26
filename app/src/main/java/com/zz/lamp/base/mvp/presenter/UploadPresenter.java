package com.zz.lamp.base.mvp.presenter;


import com.zz.lamp.base.mvp.Contract;
import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;

import okhttp3.MultipartBody;

public class UploadPresenter extends MyBasePresenterImpl<Contract.IGetUploadView> implements Contract.IsetUploadPresenter {

    public UploadPresenter(Contract.IGetUploadView view) {
        super(view);
    }


    @Override
    public void uploadImage(List<MultipartBody.Part> imgs) {
        RxNetUtils.request(getApi(ApiService.class).upload(imgs), new RequestObserver<JsonT<ImageBean>>(this) {

            @Override
            protected void onSuccess(JsonT<ImageBean> jsonT) {
                view.showImage(jsonT.getData().getImageUrl());
            }


        },mDialog);
    }
}
