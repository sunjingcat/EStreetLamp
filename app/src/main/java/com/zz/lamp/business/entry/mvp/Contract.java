package com.zz.lamp.business.entry.mvp;


import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.net.JsonT;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetTerminalPresenter extends BasePresenter {
        void getTerminalList(Map<String, Object> params);
    }

    public interface IGetTerminalView extends BaseView {
        void showIntent(List<ConcentratorBean> list);
    }

    public interface IsetTerminalAddPresenter extends BasePresenter {
        void postTerminal(Map<String, Object> params);
        void getTerminalDetail(String id);
        void checkTerminalAddr(String id,Map<String, Object> params);
        void checkTerminalName(String id,Map<String, Object> params);
        void deleteTerminal(String id);
        void getImage(String type,String modelId);
    }

    public interface IGetTerminalAddView extends BaseView {
        void showIntent();
        void showCheckAddrIntent(JsonT jsonT);
        void showCheckNameIntent(JsonT jsonT);
        void showTerminalDetail(ConcentratorBean concentratorBean);
        void showDeleteIntent();
        void showImage(List<String> list);
    }

    public interface IsetLampAddPresenter extends BasePresenter {
        void postTerminal(Map<String, Object> params);

        void getLightDeviceType();

        void getLightType();

        void getLightPoleType();

        void checkDeviceAddr(String id,Map<String, Object> params);
        void checkDeviceName(String id,Map<String, Object> params);




    }

    public interface IGetLampAddView extends BaseView {
        void showIntent();

        void showLightDeviceType(List<DeviceType> list);

        void showLightType(List<DictBean> list);

        void showLightPoleType(List<DictBean> list);

        void showCheckAddrIntent(JsonT jsonT);
        void showCheckNameIntent(JsonT jsonT);



    }

    public interface IsetRegionPresenter extends BasePresenter {
        void getAreaList(Map<String, Object> params);

        void postArea(Map<String, Object> params);
        void deleteArea(String id);
    }

    public interface IGetRegionlView extends BaseView {
        void showIntent(List<RegionExpandItem> list);

        void showPostIntent();
        void showDeleteIntent();
    }

    public interface IsetLampPresenter extends BasePresenter {
        void getLampList(Map<String, Object> params, String id);

        void getTerminalDetail(String id);
    }

    public interface IGetLampView extends BaseView {
        void showIntent(List<LightDevice> list);

        void showTerminalDetail(ConcentratorBean concentratorBean);
    }

    public interface IsetLinePresenter extends BasePresenter {
        void getLineList(String id);

        void getUsableCode(String id);

        void postLine(Map<String, Object> params);
        void deleteLine(String id);

    }

    public interface IGetLineView extends BaseView {
        void showIntent(List<LineBean> list);

        void showUsableCode(String[] list);

        void showPostIntent();
        void showDeleteIntent();

    }

    public interface IsetLampDetailPresenter extends BasePresenter {
        void getLightDetail(String id);

        void deleteLight(String id);
    }

    public interface IGeLampDetailView extends BaseView {
        void showDeleteIntent();

        void showLightDetail(LightDevice concentratorBean);
    }
}
