package com.zz.lamp.business.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.mine.mvp.Contract;
import com.zz.lamp.business.mine.mvp.presenter.MineInfoPresenter;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.ui.mvp.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends MyBaseActivity<Contract.IsetMineInfoPresenter> implements Contract.IMineInfoView  {

    @BindView(R.id.my_head)
    ImageView myHead;
    @BindView(R.id.my_subTitle)
    TextView mySubTitle;
    @BindView(R.id.not_logged)
    TextView notLogged;
    @BindView(R.id.my_information)
    RelativeLayout myInformation;
    @BindView(R.id.my_ID_card)
    LinearLayout myIDCard;
    @BindView(R.id.my_contract)
    LinearLayout myContract;
    @BindView(R.id.my_about)
    LinearLayout myAbout;
    UserBasicBean userInfo;
    @Override
    protected int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public MineInfoPresenter initPresenter() {
        return new MineInfoPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initToolBar() {

    }

    @OnClick({R.id.my_head, R.id.my_ID_card, R.id.my_contract, R.id.my_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_head:
                break;
            case R.id.my_ID_card:
                break;
            case R.id.my_contract:
                break;
            case R.id.my_about:
                break;
        }
    }

    @Override
    public void showUserInfo(UserBasicBean userInfo) {
        this.userInfo = userInfo;
        CacheUtility.spSave("name", userInfo.getName());

        if (!TextUtils.isEmpty(userInfo.getAvatar())) {

            GlideUtils.loadCircleImage(this, userInfo.getAvatar(), myHead);
        }
    }

    @Override
    public void showIntent() {

    }
}
