package com.lmx.friends.net;

import android.text.TextUtils;

import com.lmx.lib.core.http.observer.UploadObserver;
import com.lmx.lib.core.ui.BaseActivity;
import com.lmx.lib.core.ui.BaseFragment;
import com.lmx.lib.core.ui.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/5/11.
 */

public abstract class RequestObserver<T extends CompactModel> extends UploadObserver<T> {



    public RequestObserver() {
    }


    public RequestObserver(BasePresenter presenter) {
        super(presenter);
    }

    public RequestObserver(BaseActivity activity) {
        super(activity);
    }

    public RequestObserver(BaseFragment fragment) {
        super(fragment);
    }



    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected void onError(String errorMsg) {
        if (!TextUtils.isEmpty(errorMsg))
            Logger.d(errorMsg);
    }

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    protected void onFail(String message) {
        Logger.d(message);
//        IToast.show(message);
    }

    protected void onFail2(T t) {
    }


    protected void onTokenOutDate() {
        EventBus.getDefault().post(new OutDateEvent());

    }

    @Override
    public void doOnSubscribe(Disposable d) {
        super.doOnSubscribe(d);
    }


    @Override
    public void doOnNext(T t) {
        if (t.isSuccess()) {
            onSuccess(t);
        } else if (t.isOutdate()) {
            onTokenOutDate();
        } else {
            onFail(t.getMessage());
            onFail2(t);
        }
    }

    @Override
    public void onProgress(int progress) {

    }
}
