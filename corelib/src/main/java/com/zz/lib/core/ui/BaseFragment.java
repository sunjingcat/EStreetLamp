package com.zz.lib.core.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;


import com.zz.lib.commonlib.ui.CommonFragment;
import com.zz.lib.commonlib.utils.IToast;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.lib.core.ui.widget.CustomProgressDialog;
import com.zz.lib.core.utils.LoadingUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by admin on 2018/4/13.
 */

public abstract class BaseFragment<P extends BasePresenter> extends CommonFragment implements BaseView {
//

    protected P mPresenter;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private CustomProgressDialog mLoadingDialog;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        mLoadingDialog = LoadingUtils.build(getActivity());
        mPresenter = initPresenter();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
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
        IToast.show(getActivity(),msg);
    }

    @Override
    public void showToast(int id) {
        IToast.show(getActivity(),getString(id));
    }

    public abstract P initPresenter();

    @Override
    public void onDone() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        if (mPresenter != null) {
            mPresenter.onDone();//在presenter中解绑释放view
            mPresenter = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            onVisible();
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    protected void onVisible(){

    }

    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

}
