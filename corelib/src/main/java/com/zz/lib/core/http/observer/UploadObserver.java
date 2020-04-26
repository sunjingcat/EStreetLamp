package com.zz.lib.core.http.observer;

import com.zz.lib.core.ui.BaseActivity;
import com.zz.lib.core.ui.BaseFragment;
import com.zz.lib.core.ui.mvp.BasePresenter;

/**
 * Created by admin on 2018/6/27.
 */

public  abstract class UploadObserver<T> extends CommonObserver<T> {

    public UploadObserver() {
    }


    public UploadObserver(BasePresenter presenter) {
        super(presenter);
    }


    public UploadObserver(BaseActivity activity) {
        super(activity);
    }

    public UploadObserver(BaseFragment fragment) {
        super(fragment);
    }


    // 上传进度回调
    public abstract void onProgress(int progress);

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }
}
