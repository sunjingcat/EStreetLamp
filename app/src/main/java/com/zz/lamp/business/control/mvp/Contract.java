package com.zz.lamp.business.control.mvp;



import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RealTimeCtrlGroup;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetTerminalControlPresenter extends BasePresenter {
        void getTerminalDetail(String id);
        void getLineList(String id);
        void realTimeCtrlLine(String id,Map<String, Object> params);
    }

    public interface IGetTerminalControlView extends BaseView{
        void showDetail(ConcentratorBean list);
        void showLineList(List<LineBean> list);
        void showIntent();
    }

    public interface IsetGroupControlPresenter extends BasePresenter {
        void getGroupList(String id);
        void realTimeCtrGroup(Map<String, Object> params);
    }

    public interface IGetGroupControlView extends BaseView{
        void showGroupList(List<RealTimeCtrlGroup> list);
        void showIntent();
    }

    public interface IsetLightControlPresenter extends BasePresenter {
        void getLightList(String id,Map<String, Object> params);
        void realTimeCtrLight(Map<String, Object> params);
    }

    public interface IGetLightControlView extends BaseView{
        void showLightList(List<LightDeviceConBean> list);
        void showIntent();
    }


}
