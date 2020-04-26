package com.zz.lamp.business.entry;

import android.view.View;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class EntryFragment extends MyBaseFragment {

    @Override
    protected int getCreateView() {
        return R.layout.fragment_entry;
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
