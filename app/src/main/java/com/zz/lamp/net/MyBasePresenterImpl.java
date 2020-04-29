package com.zz.lamp.net;

import com.zz.lamp.BuildConfig;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.http.http.RetrofitClient;
import com.zz.lib.core.http.upload.UploadRetrofit;
import com.zz.lib.core.ui.mvp.BasePresenterImpl;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin on 2018/6/5.
 */

public class MyBasePresenterImpl<V extends com.zz.lib.core.ui.mvp.BaseView> extends BasePresenterImpl<V> {


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

    protected  <T> T getCApi(Class<T> t) {
        return RxNetUtils
                .getSCInstance(CacheUtility.getURL())
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
