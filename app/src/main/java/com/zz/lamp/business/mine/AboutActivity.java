package com.zz.lamp.business.mine;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.Version;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.UpdateManager;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

/**
 * 关于
 */
public class AboutActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_icon)
    ImageView tvIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.bt_update)
    Button btUpdate;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvVersion.setText(getVersioName());
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        new UpdateManager(this).checkUpdate();
    }
    private String getVersioName() {
        try {
          String  versionName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return "";
    }
    void getData() {
        RxNetUtils.request(getCApi(ApiService.class).getVersionInfo(getVersionCode()+""), new RequestObserver<JsonT<Version>>(this) {
            @Override
            protected void onSuccess(JsonT<Version> data) {
                if (data.isSuccess()&&data.getData()!=null) {
                    tvInfo.setText(data.getData().getChanges()+"");
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<Version> jsonT) {
                super.onFail2(jsonT);
                showToast(jsonT.getMessage());
            }
        }, null);
    }
    //获取app版本
    private int getVersionCode() {
        try {
            return getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }
}
