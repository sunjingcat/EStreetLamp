package com.zz.lib.core.http.observer;

import android.text.TextUtils;

import com.zz.lib.core.http.base.BaseObserver;
import com.zz.lib.core.ui.BaseActivity;
import com.zz.lib.core.ui.BaseFragment;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import io.reactivex.disposables.Disposable;


public abstract class CommonObserver<T> extends BaseObserver<T> {


    private BasePresenter mPresenter;
    private BaseActivity activity;
    private BaseFragment fragment;

    public CommonObserver() {
    }

    public CommonObserver(BasePresenter presenter) {
        this.mPresenter = presenter;
    }


    public CommonObserver(BaseActivity activity) {
        this.activity = activity;
    }

    public CommonObserver(BaseFragment fragment) {
        this.fragment = fragment;
    }


    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected void onError(String errorMsg){
        if (!TextUtils.isEmpty(errorMsg))
            Logger.d(errorMsg);
    }

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    protected void onFail(String message){
        Logger.d(message);

    }

    @Override
    public void doOnError(String errorMsg) {
        onError(errorMsg);
    }


    @Override
    public abstract void doOnNext(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        if(this.mPresenter != null){
            mPresenter.addDisposable(d);
        }
        if(this.activity != null){
            this.activity.addDisposable(d);
        }
        if(this.fragment != null){
            this.fragment.addDisposable(d);
        }
//        RxHttpUtils.addDisposable(d);
    }


    @Override
    public void doOnCompleted() {
        mPresenter = null;
        activity = null;
        fragment = null;
    }
}
