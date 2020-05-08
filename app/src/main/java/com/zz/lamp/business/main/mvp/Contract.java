package com.zz.lamp.business.main.mvp;

import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.MapListBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetMapPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(Map<String, Object> map);

    }

    public interface IGetMapView extends BaseView {
        void showResult();
        void showDetailResult(List<MapListBean> alarmBean);

    }
}
