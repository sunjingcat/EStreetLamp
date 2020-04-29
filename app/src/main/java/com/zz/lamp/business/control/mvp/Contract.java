package com.zz.lamp.business.control.mvp;



import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.IpAdress;
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

}
