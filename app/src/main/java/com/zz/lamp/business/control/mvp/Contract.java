package com.zz.lamp.business.control.mvp;



import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetTerminalControlPresenter extends BasePresenter {
        void getTerminalDetail(String id);
        void getLineList(String id);
        void realTimeCtrlLine(Map<String, Object> params);
    }

    public interface IGetTerminalControlView extends BaseView{
        void showDetail(ConcentratorBean list);
        void showLineList(List<LineBean> list);
        void showIntent();
    }


}
