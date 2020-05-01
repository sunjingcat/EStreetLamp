package com.zz.lamp.business.entry.mvp;



import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.UsableCode;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetTerminalPresenter extends BasePresenter {
        void getTerminalList(Map<String, Object> params);
    }

    public interface IGetTerminalView extends BaseView{
        void showIntent(List<ConcentratorBean> list);
    }

    public interface IsetTerminalAddPresenter extends BasePresenter {
        void postTerminal(Map<String, Object> params);
        void getLightDeviceType();
    }

    public interface IGetTerminalAddView extends BaseView{
        void showIntent();
        void showLightDeviceType(List<DeviceType> list);
    }

    public interface IsetRegionPresenter extends BasePresenter {
        void getAreaList(Map<String, Object> params);
        void postArea(Map<String, Object> params);
    }

    public interface IGetRegionlView extends BaseView{
        void showIntent(List<RegionExpandItem> list);
        void showPostIntent();
    }

    public interface IsetLampPresenter extends BasePresenter {
        void getLampList(Map<String, Object> params,String id);
        void getTerminalDetail(String id);
    }

    public interface IGetLampView extends BaseView{
        void showIntent(List<LightDevice> list);
        void showTerminalDetail(ConcentratorBean concentratorBean);
    }

    public interface IsetLinePresenter extends BasePresenter {
        void getLineList(String id);
        void getUsableCode(String id);
        void postLine(Map<String, Object> params);
    }

    public interface IGetLineView extends BaseView{
        void showIntent(List<LineBean> list);
        void showUsableCode(String[] list);
        void showPostIntent();
    }
}
