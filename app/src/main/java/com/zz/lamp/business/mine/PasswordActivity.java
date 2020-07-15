package com.zz.lamp.business.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.YsConfig;
import com.zz.lamp.business.login.LoginActivity;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.OutDateEvent;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;
/**
 * 密码
 */
public class PasswordActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_password_old)
    EditText edPasswordOld;
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
        getData();
    }

    void getData() {
        Map<String,Object> map = new HashMap<>();
        String edPasswordOld_ = edPasswordOld.getText().toString();
        String edPassword_ = edPassword.getText().toString();
        String edPasswordAgain_ = edPasswordAgain.getText().toString();
        String username = getIntent().getStringExtra("userName");
        if (TextUtils.isEmpty(edPasswordOld_)){
            showToast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(edPassword_)){
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(edPasswordAgain_)){
            showToast("请输入确认密码");
            return;
        }
        if (!edPassword_.equals(edPasswordAgain_)){
            showToast("两次新密码不一致");
            return;
        }

        map.put("newPassword",edPassword_);
        map.put("oldPassword",edPasswordOld_);
        map.put("username",username);
        RxNetUtils.request(getCApi(ApiService.class).resetPwd(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    CacheUtility.saveToken("");
                    CacheUtility.clear();
                    startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                    EventBus.getDefault().post(new OutDateEvent());
                    finish();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

}
