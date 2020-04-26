package com.lmx.friends.net;

import com.lmx.lib.core.http.http.RetrofitClient;
import com.lmx.lib.core.http.upload.UploadRetrofit;
import com.lmx.lib.core.ui.mvp.BasePresenterImpl;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin on 2018/6/5.
 */

public class MyBasePresenterImpl<V extends com.lmx.lib.core.ui.mvp.BaseView> extends BasePresenterImpl<V> {


    public MyBasePresenterImpl(V view) {
        super(view);
    }


    protected  <T> T getApi(Class<T> t) {
        return RxNetUtils
                .getSInstance()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .createSApi(t);
    }


    protected <T> T getUploadApi(Class<T> t) {
        String baseUrl = RetrofitClient.getInstance().getRetrofit().baseUrl().toString();
        return UploadRetrofit
                .getInstance(baseUrl)
                .getRetrofit()
                .create(t);
    }

}
