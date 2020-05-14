package com.zz.lamp.business.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.igexin.sdk.PushManager;
import com.troila.customealert.CustomDialog;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.login.LoginActivity;
import com.zz.lamp.business.mine.mvp.Contract;
import com.zz.lamp.business.mine.mvp.presenter.MineInfoPresenter;
import com.zz.lamp.net.OutDateEvent;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends MyBaseActivity<Contract.IsetMineInfoPresenter> implements Contract.IMineInfoView {

    @BindView(R.id.my_head)
    ImageView myHead;
    @BindView(R.id.my_about)
    LinearLayout myAbout;
    UserBasicBean userInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.not_content)
    TextView notContent;
    @BindView(R.id.my_information)
    RelativeLayout myInformation;
    @BindView(R.id.my_password)
    LinearLayout myPassword;
    @BindView(R.id.my_code)
    LinearLayout myCode;

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
        mPresenter.getMineInfo();

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @Override
    public void showUserInfo(UserBasicBean userInfo) {
        this.userInfo = userInfo;
        myName.setText(userInfo.getUserName()+"");
        notContent.setText(userInfo.getPhonenumber()+"");

        if (!TextUtils.isEmpty(userInfo.getAvatar())) {

            GlideUtils.loadCircleImage(this, userInfo.getAvatar(), myHead);
        }
    }

    @Override
    public void showIntent() {
        CacheUtility.saveToken("");
        CacheUtility.clear();
        startActivity(new Intent(MineActivity.this, LoginActivity.class));
        EventBus.getDefault().post(new OutDateEvent());
        PushManager.getInstance().turnOffPush(this);
        finish();
    }
    private CustomDialog customDialog;
    @OnClick({R.id.my_password, R.id.my_code, R.id.my_about, R.id.my_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_password:
                if (userInfo==null||TextUtils.isEmpty(userInfo.getLoginName())){
                    return;}
                startActivity(new Intent(MineActivity.this,PasswordActivity.class).putExtra("userName",userInfo.getLoginName()));

                break;
            case R.id.my_code:
                startActivity(new Intent(MineActivity.this,ChangeCodeActivity.class));

                break;
            case R.id.my_about:
                startActivity(new Intent(MineActivity.this,AboutActivity.class));
                break;
            case R.id.my_logout:
                CustomDialog.Builder builder = new CustomDialog.Builder(MineActivity.this)
                        .setTitle("提示")
                        .setMessage("确定退出登录")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.logout();
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }
}
