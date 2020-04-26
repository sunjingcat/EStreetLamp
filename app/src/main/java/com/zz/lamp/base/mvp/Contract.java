package com.zz.lamp.base.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetUploadPresenter extends BasePresenter {


        void uploadImage(List<MultipartBody.Part> imgs);
    }

    public interface IGetUploadView extends BaseView {

        void showImage(String url);

        void showIntent();
    }


}
