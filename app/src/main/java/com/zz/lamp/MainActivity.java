package com.zz.lamp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.business.map.SelectLocationActivity;
import com.zz.lamp.business.map.TestLocationActivity;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.UpdateManager;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.http.utils.ToastUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class MainActivity extends MyBaseActivity {

    private long mExitTime = 0;
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        new UpdateManager(this).checkUpdate();
       putClientId();
    }

    @Override
    protected void initToolBar() {

    }

    @OnClick({R.id.main_group_1, R.id.main_group_2, R.id.main_group_3, R.id.main_group_4, R.id.main_group_5, R.id.main_group_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_group_1:
                Intent intent = new Intent();
                intent.setClass(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.main_group_2:
                startActivity(new Intent(this, TestLocationActivity.class));
                break;
            case R.id.main_group_3:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_4:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_5:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_6:
                showToast("暂未开放，敬请期待");
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void putClientId() {
        Map<String,Object> map = new HashMap<>();
        map.put("cId", CacheUtility.spGetOut("cId",""));
        RxNetUtils.request(getCApi(ApiService.class).putClientId(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT login_data) {
                if (login_data.isSuccess()) {

                }else {

                }
            }
            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
            }
        }, null);
    }

}
