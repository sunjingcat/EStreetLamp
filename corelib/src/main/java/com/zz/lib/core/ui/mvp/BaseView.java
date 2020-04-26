package com.zz.lib.core.ui.mvp;

/*
 * 项目名:    BaseLib
 * 包名       com.zhon.baselib.mvpbase
 * 文件名:    BaseView
 * 创建者:    ZJB
 * 创建时间:  2017/6/20 on 14:16
 * 描述:     TODO
 */
public interface BaseView {

//    void onInit();

    void onDone();

    void showLoading(String msg);

    void dismissLoading();

    void showToast(String msg);

    void showToast(int msg);

    void onError();
}
