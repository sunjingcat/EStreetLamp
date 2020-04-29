package com.zz.lamp.business.entry.mvp;



import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.bean.RegionExpandItem;
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

    public interface IsetRegionPresenter extends BasePresenter {
        void getAreaList(Map<String, Object> params);
    }

    public interface IGetRegionlView extends BaseView{
        void showIntent(List<RegionExpandItem> list);
    }

}
