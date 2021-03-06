package com.zz.lamp.business.main.mvp;

import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceKind;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.bean.UserInfo;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetMapPresenter extends BasePresenter {
        void deviceKindList();
        void getTerminalData(String id);
        void getLightDeviceData(String id);
        void getData(Map<String, Object> map);
        void getUserInfoData();

    }

    public interface IGetMapView extends BaseView {
        void showResult(List<MapListBean> listBeans);
        void showResult(String s);
        void showDeviceKindList(DeviceKind listBeans);
        void showTerminalData(ConcentratorBean concentratorBean);
        void showLightDeviceData(LightDevice alarmBean);
        void showUserInfo(UserBasicBean userInfo);
        void showError();

    }
}
