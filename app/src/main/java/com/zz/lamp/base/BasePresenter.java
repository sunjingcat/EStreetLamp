package com.zz.lamp.base;

import android.app.Dialog;
import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.core.http.http.RetrofitClient;
import com.zz.lib.core.http.upload.UploadRetrofit;
import com.zz.lib.core.utils.LoadingUtils;


import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/3/22.
 */

public class BasePresenter<M, V> implements com.zz.lib.core.ui.mvp.BasePresenter {
    public M model;
    public V view;
    public WeakReference<V> mViewRef;

    protected Dialog mDialog;

    private CompositeDisposable mCompositeDisposable;

    public void attachModelView(M model, V pView) {
        mViewRef = new WeakReference<V>(pView);
        this.model = model;

    }

    public V getView() {
        if (isAttach()) {
            intiDialog(mViewRef.get());
            return mViewRef.get();
        } else {
            return null;
        }

    }

    public void intiDialog(V view){
        if (view instanceof Context)
            mDialog = LoadingUtils.build((Context) view);
        else if (view instanceof Fragment)
            mDialog = LoadingUtils.build(((Fragment) view).getContext());
        else if (view instanceof View)
            mDialog = LoadingUtils.build(((View) view).getContext());
    }
    public boolean isAttach() {

        return null != mViewRef && null != mViewRef.get();
    }

    public void onDettach() {
        if (null != mViewRef) {
            mViewRef.clear();
            mViewRef = null;
        }
        if(mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onDone() {
        this.view = null;
        if(mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
        unDisposable();
    }

    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
//        RxHttpUtils.cancelAllRequest();

    }

    public static <T> T getApi(Class<T> t) {
        return RxNetUtils
                .getSInstance()
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
}
