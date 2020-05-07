package com.zz.lamp.business.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class VideoActivity extends MyBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_video;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }
}
