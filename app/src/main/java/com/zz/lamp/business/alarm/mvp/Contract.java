package com.zz.lamp.business.alarm.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetAlarmAddPresenter extends BasePresenter {
        void submitData(String url, Map<String, Object> map);

    }

    public interface IGetAlarmAddView extends BaseView {
        void showResult();

    }
}
