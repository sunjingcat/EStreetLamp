package com.zz.lamp.business.login.mvp;



import com.zz.lamp.bean.IpAdress;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.Map;

public class Contract {
    public interface IsetLoginPresenter extends BasePresenter {
        void getAddress(String authCode);
        void setAccount(Map<String, Object> params);
    }

    public interface IGetLoginView extends BaseView{

        void setAuthCode(IpAdress params);
        void showIntent();
    }

}
