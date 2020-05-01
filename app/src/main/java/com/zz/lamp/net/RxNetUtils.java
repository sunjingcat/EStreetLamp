package com.zz.lamp.net;

import android.app.Dialog;
import android.text.TextUtils;

import com.zz.lamp.BuildConfig;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.http.RxHttpUtils;
import com.zz.lib.core.http.base.BaseObserver;
import com.zz.lib.core.http.http.RetrofitClient;
import com.zz.lib.core.http.http.SingleRxHttp;
import com.zz.lib.core.http.interceptor.Transformer;
import com.zz.lib.core.http.upload.UploadRetrofit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/4/24.
 */

public class RxNetUtils extends RxHttpUtils {


    public static SingleRxHttp getSInstance() {
        SingleRxHttp rxhttp = SingleRxHttp.getInstance();

        if(CacheUtility.isLogin()){
            if (!TextUtils.isEmpty(CacheUtility.getToken()) ){
                Map<String,Object> paras = new HashMap<>();
                paras.put("token", CacheUtility.getToken());
                rxhttp.addHeaders(paras);
            }
        }
//        Map<String,Object> param = new HashMap<>();
//        rxhttp.addParams(param);
        rxhttp.baseUrl(rxhttp.getBaseUrl());
        return rxhttp;
    }

    public static SingleRxHttp getSCInstance(String URL) {
        SingleRxHttp rxhttp = SingleRxHttp.getInstance();

        if(CacheUtility.isLogin()){
            if (!TextUtils.isEmpty(CacheUtility.getToken()) ){
                Map<String,Object> paras = new HashMap<>();
                paras.put("token", CacheUtility.getToken());
                rxhttp.addHeaders(paras);
            }
        }
//        Map<String,Object> param = new HashMap<>();
//        rxhttp.addParams(param);
        rxhttp.baseUrl(URL);
        return rxhttp;
    }

    public static SingleRxHttp getTestInstance() {
        SingleRxHttp rxhttp = SingleRxHttp.getInstance();

        return rxhttp;
    }

    public static <T> T getTestApi(Class<T> t) {
        return RxNetUtils
                .getTestInstance()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .createSApi(t);
    }



    public static <T> T getApi(Class<T> t) {
        return RxNetUtils
                .getSInstance()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .createSApi(t);
    }

    public static <T> T getCApi(Class<T> t) {
        return RxNetUtils
                .getSCInstance(CacheUtility.getURL())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .createSApi(t);
    }
    public static <T> T getUploadApi(Class<T> t) {
        String baseUrl = RetrofitClient.getInstance().getRetrofit().baseUrl().toString() ;
        return UploadRetrofit
                .getInstance(baseUrl)
                .getRetrofit()
                .create(t);
    }

    public static MultipartBody.Part getMultiBody(String key, String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        return body;
    }


//    public static MultipartBody.Part getMultiBody(String key,String filePath, RequestObserver observer) {
//        File file = new File(filePath);
//        UploadFileRequestBody uploadFileRequestBody =
//                new UploadFileRequestBody(file,observer);
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData(key, file.getName(), uploadFileRequestBody);
//        return body;
//    }






    public static <T> void request(Observable<T> o, BaseObserver<T> ob, Dialog dialog) {
        o.compose(Transformer.<T>switchSchedulers(dialog))
                .subscribe(ob);
    }



    public static <T> void request(Observable<T> o, BaseObserver<T> ob) {
        o.compose(Transformer.<T>switchSchedulers())
                .subscribe(ob);
    }

    public static <T> void upload(Observable<T> o, BaseObserver<T> ob) {
        o.compose(Transformer.<T>switchSchedulers())
                .subscribe(ob);
    }

    public static <T> Disposable timer(final Observable<T> o, final BaseObserver<T> ob) {

        Disposable disposable = Flowable.interval(0,10000, TimeUnit.MILLISECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                request(o, ob);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {

            }
        });

        return disposable;

    }

    public static <T> Disposable timer(final Observable<T> o, final BaseObserver<T> ob, long time) {

        Disposable disposable = Flowable.interval(0,time, TimeUnit.MILLISECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                request(o, ob);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {

            }
        });

        return disposable;

    }

}
