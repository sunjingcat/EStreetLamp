package com.zz.lamp.business.main;

import android.view.View;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class MainFragment extends MyBaseFragment {

    @Override
    protected int getCreateView() {
        return R.layout.fragment_main;
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
