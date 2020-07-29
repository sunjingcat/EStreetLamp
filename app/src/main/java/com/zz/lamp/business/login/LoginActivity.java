package com.zz.lamp.business.login;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.zz.lamp.App;
import com.zz.lamp.HomeActivity;
import com.zz.lamp.MainActivity;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;

import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.business.login.mvp.Contract;
import com.zz.lamp.business.login.mvp.presenter.LoginPresenter;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends MyBaseActivity<Contract.IsetLoginPresenter> implements Contract.IGetLoginView{

    @BindView(R.id.log_number)
    EditText logNumber;
    @BindView(R.id.log_password)
    EditText logPassword;
    @BindView(R.id.login_password_show)
    ImageView loginPasswordShow;
    @BindView(R.id.log_code)
    EditText logCode;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.boutique_all)
    CheckBox boutiqueAll;
    private boolean mPasswordVisible =true;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        String code = CacheUtility.spGetOut("code", "600001");
        logCode.setText(code+"");
    }

    @Override
    protected void initToolBar() {

    }
    @OnClick({R.id.login_password_show, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_password_show:
                if (mPasswordVisible) {
                    //设置EditText的密码为可见的
                    logPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordVisible = false;
                } else {
                    //设置密码为隐藏的
                    logPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordVisible = true;
                }


                break;
            case R.id.login_btn:
                String number = logNumber.getText().toString();
                String password = logPassword.getText().toString();
                String code = logCode.getText().toString();
                if (TextUtils.isEmpty(number)){
                    showToast("请填写用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    showToast("请填写密码");
                    return;
                }
                if (TextUtils.isEmpty(code)){
                    showToast("请填授权码");
                    return;
                }
                mPresenter.getAddress(code);

                break;
        }
    }


    @Override
    public void setAuthCode(IpAdress params) {
        String number = logNumber.getText().toString();
        String password = logPassword.getText().toString();
        String code = logCode.getText().toString();
        Map<String,Object> map = new HashMap<>();
        map.put("username",number);
        map.put("password",password);
        map.put("cId",code);
        mPresenter.setAccount(map);

    }

    @Override
    public void showIntent(int indexType) {
        showToast("登录成功");
        String code = logCode.getText().toString();

        PushManager.getInstance().turnOnPush(this);

        Intent intent = new Intent();
        if (indexType ==1){
            intent.setClass(this, HomeActivity.class);
        }else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
