package com.lmx.friends.base.mvp.presenter;


import com.lmx.friends.base.mvp.Contract;
import com.lmx.friends.bean.ImageBean;
import com.lmx.friends.net.ApiService;
import com.lmx.friends.net.JsonT;
import com.lmx.friends.net.MyBasePresenterImpl;
import com.lmx.friends.net.RequestObserver;
import com.lmx.friends.net.RxNetUtils;

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
