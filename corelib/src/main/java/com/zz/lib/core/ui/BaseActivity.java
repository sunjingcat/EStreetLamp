package com.zz.lib.core.ui;

import android.os.Bundle;
import android.view.View;


import com.zz.lib.commonlib.ui.CommonActivity;
import com.zz.lib.commonlib.utils.IToast;
import com.zz.lib.core.BaseApplication;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.lib.core.ui.widget.CustomProgressDialog;
import com.zz.lib.core.utils.LoadingUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by admin on 2017/8/8.
 */

public abstract class BaseActivity<P extends BasePresenter> extends CommonActivity implements View.OnClickListener ,BaseView {

    protected P mPresenter;
    private CustomProgressDialog mLoadingDialog;
    protected CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mLoadingDialog = LoadingUtils.build(this);
        mCompositeDisposable = new CompositeDisposable();
        mPresenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        onDone();
    }

    @Override
    public void showLoading(String msg) {
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        IToast.show(this,msg);
    }

    @Override
    public void showToast(int id) {
        IToast.show(this,getString(id));
    }

    public abstract P initPresenter();

    public P getPresenter() {
        return mPresenter;
    }



    @Override
    public void onDone() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        if (mPresenter != null) {
            mPresenter.onDone();//在presenter中解绑释放view
            mPresenter = null;
        }else {
//            RxHttpUtils.cancelAllRequest();
        }

    }



    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

}
