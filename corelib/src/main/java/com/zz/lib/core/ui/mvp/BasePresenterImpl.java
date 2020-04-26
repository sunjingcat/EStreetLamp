package com.zz.lib.core.ui.mvp;


import android.app.Dialog;
import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.zz.lib.commonlib.utils.AppUtil;
import com.zz.lib.core.utils.LoadingUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    protected V view;
    protected Dialog mDialog;
    protected Context context;
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    protected CompositeDisposable mCompositeDisposable;

    public BasePresenterImpl(V view) {
        this.view = view;
        if (view instanceof Context) {
            mDialog = LoadingUtils.build((Context) view);
            context = (Context) view;
        }
        else if (view instanceof Fragment) {
            mDialog = LoadingUtils.build(((Fragment) view).getContext());
            context = ((Fragment) view).getContext();
        }
        else if (view instanceof View) {
            mDialog = LoadingUtils.build(((View) view).getContext());
            context = AppUtil.getCompactContext((View) view);
        }
        onInit();
    }


    @Override
    public void onDone() {
        this.view = null;
        unDisposable();
    }

    @Override
    public void onInit() {

    }

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
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

    public String getString(Context context, int rid) {
        return context.getString(rid);
    }



}
