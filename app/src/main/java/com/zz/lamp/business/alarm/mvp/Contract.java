package com.zz.lamp.business.alarm.mvp;

import com.zz.lamp.bean.AlarmBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetAlarmAddPresenter extends BasePresenter {
        void submitData(String id, String alarmStatus, String handleDescription, String ID, String handleFile);
        void getData(Map<String, Object> map);

    }

    public interface IGetAlarmAddView extends BaseView {
        void showResult();
        void showDetailResult(AlarmBean alarmBean);

    }
}
