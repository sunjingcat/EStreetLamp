package com.zz.lamp.business.entry;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.troila.customealert.CustomDialog;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.entry.adapter.LightDetailAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampDetailPresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class LightDetailActivity extends MyBaseActivity<Contract.IsetLampDetailPresenter> implements Contract.IGeLampDetailView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bg)
    LinearLayout bg;
    LightDetailAdapter adapter;
    List<LightDetailBean> mlist = new ArrayList<>();
    String lightId;
    @Override
    protected int getContentView() {
        return R.layout.activity_light_detail;
    }
    LightDevice lightDevice;
    @Override
    public LampDetailPresenter initPresenter() {
        return new LampDetailPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new LightDetailAdapter(R.layout.item_detail_light, mlist);
        rv.setAdapter(adapter);
         lightId = getIntent().getStringExtra("lightId");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getLightDetail(lightId);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (lightDevice == null) return;
                startActivity(new Intent(this, EntryLampActivity.class).putExtra("terminalId", lightDevice.getTerminalId()).putExtra("device", lightDevice).putExtra("id",lightDevice.getId()));
                break;
            case R.id.delete:
                showDeleteDialog();
                break;
        }
    }
    private CustomDialog customDialog;
    private void showDeleteDialog() {
        if (lightDevice == null) return;
        CustomDialog.Builder builder = new com.troila.customealert.CustomDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除设备")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteLight(lightId);
                    }
                });
        customDialog = builder.create();
        customDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    @Override
    public void showDeleteIntent() {
        finish();
    }

    @Override
    public void showLightDetail(LightDevice lightDevice) {
        if (lightDevice == null) return;
        this.lightDevice  = lightDevice;
        mlist.clear();
        mlist.add(new LightDetailBean("路灯控制器地址", lightDevice.getDeviceAddr() + ""));
        mlist.add(new LightDetailBean("支路", lightDevice.getLineName() + ""));
        mlist.add(new LightDetailBean("路灯控制器编号", lightDevice.getDeviceCode() + ""));
        mlist.add(new LightDetailBean("路灯控制器别名", lightDevice.getDeviceName() + ""));
        mlist.add(new LightDetailBean("安装时间", lightDevice.getLightInstallTime() + ""));

        mlist.add(new LightDetailBean("路杆编号", lightDevice.getLightPoleCode() + ""));
        mlist.add(new LightDetailBean("路杆类型", lightDevice.getLightPoleType() + ""));
        mlist.add(new LightDetailBean("路杆高度", lightDevice.getLightPoleHeight() + ""));
        mlist.add(new LightDetailBean("灯头类型", lightDevice.getLightType() + ""));

        mlist.add(new LightDetailBean("路灯类型", lightDevice.getDeviceType() + ""));
        mlist.add(new LightDetailBean("主灯类型", lightDevice.getLightMainTypeName() + ""));
        mlist.add(new LightDetailBean("主灯额定功率(W)", lightDevice.getLightMainPower() + ""));
        mlist.add(new LightDetailBean("主灯功率阈值(W)", lightDevice.getLightMainPowerLimit() + ""));

        if (lightDevice.getDeviceType() == 2) {
            mlist.add(new LightDetailBean("辅灯类型", lightDevice.getLightAuxiliaryTypeName() + ""));
            mlist.add(new LightDetailBean("辅灯额定功率(W)", lightDevice.getLightAuxiliaryPower() + ""));
            mlist.add(new LightDetailBean("辅灯功率阈值(W)", lightDevice.getLightAuxiliaryPowerLimit() + ""));
        }
        adapter.notifyDataSetChanged();
    }

}
