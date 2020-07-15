package com.zz.lamp.business.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.business.login.LoginActivity;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.base.BasePresenter.getApi;
import static com.zz.lamp.net.RxNetUtils.getCApi;
/**
 * 切换邀请码
 */
public class ChangeCodeActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_code;
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
        postData();
    }
    void postData() {
        String trim = edCode.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            showToast("请输入授权码");
            return;
        }
        RxNetUtils.request(getApi(ApiService.class).getAddress(trim), new RequestObserver<JsonT<IpAdress>>(this) {
            @Override
            protected void onSuccess(JsonT<IpAdress> jsonT) {
                if (jsonT.isSuccess()) {
                    CacheUtility.spSave("code",trim);
                    startActivity(new Intent(ChangeCodeActivity.this, LoginActivity.class));
                    finish();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<IpAdress> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }
}
