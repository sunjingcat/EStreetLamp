package com.zz.lamp.business.alarm;

import android.view.View;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class AlarmFragment extends MyBaseFragment {

    @Override
    protected int getCreateView() {
        return R.layout.fragment_alarm;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onError() {

    }
}
