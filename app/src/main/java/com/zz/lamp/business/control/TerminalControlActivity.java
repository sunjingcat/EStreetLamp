package com.zz.lamp.business.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;

import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.TerminalControlPresenter;

public class TerminalControlActivity extends MyBaseActivity<Contract.IsetTerminalControlPresenter> implements Contract.IGetTerminalControlView{

    @Override
    protected int getContentView() {
        return R.layout.activity_terminal_control;
    }

    @Override
    public TerminalControlPresenter initPresenter() {
        return new TerminalControlPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void showIntent(ConcentratorBean list) {

    }
}
