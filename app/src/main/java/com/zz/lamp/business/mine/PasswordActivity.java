package com.zz.lamp.business.mine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_password_again)
    EditText edPasswordAgain;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected int getContentView() {
        return R.layout.activity_pass_word;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
    }
}
