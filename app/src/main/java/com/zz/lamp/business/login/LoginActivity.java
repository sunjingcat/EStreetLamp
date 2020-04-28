package com.zz.lamp.business.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class LoginActivity extends MyBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
