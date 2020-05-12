package com.zz.lamp.business.mine.mvp;

import com.zz.lamp.bean.UserBasicBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

public class Contract {
    public interface IsetMineInfoPresenter extends BasePresenter {
        void getMineInfo();
        void logout();
    }
    public interface IMineInfoView extends BaseView {
        void showUserInfo(UserBasicBean userInfo);
        void showIntent();
    }
}
