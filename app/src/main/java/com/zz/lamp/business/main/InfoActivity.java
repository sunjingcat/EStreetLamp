package com.zz.lamp.business.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hikvision.wifi.configuration.DeviceInfo;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.control.LigitDeviceControlActivity;
import com.zz.lamp.business.control.TerminalControlActivity;
import com.zz.lamp.business.entry.adapter.LightDetailAdapter;
import com.zz.lamp.business.main.adapter.DetailAdapter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.NavUtils;
import com.zz.lib.core.http.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends Activity {

    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    @BindView(R.id.btn_nav)
    Button btnNav;
    @BindView(R.id.btn_control)
    Button btnControl;
    List<LightDetailBean> mlist = new ArrayList<>();
    DetailAdapter adapter;
    Double lat;
    Double lon;
    ConcentratorBean terminalInfo;
    LightDevice deviceInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(R.layout.item_detail, mlist);
        infoRv.setAdapter(adapter);
         terminalInfo = (ConcentratorBean) getIntent().getSerializableExtra("TerminalInfo");
         deviceInfo = (LightDevice) getIntent().getSerializableExtra("DeviceInfo");
        if (deviceInfo != null) {
            showIntent(deviceInfo);
            lat = deviceInfo.getDeviceLat();
            lon = deviceInfo.getDeviceLng();
        }
        if (terminalInfo != null) {
            showIntent(terminalInfo);
            lat = terminalInfo.getTerminalLat();
            lon = terminalInfo.getTerminalLng();
        }

    }

    @OnClick({R.id.btn_nav, R.id.btn_control, R.id.close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_nav:
                if (!NavUtils.isInstalled()) {
                    ToastUtils.showToast("未安装百度地图");
                    return;
                } else {
                    NavUtils.invokeNavi(this, null, "中智.智慧路灯", lat + "," + lon);
                }
                break;
            case R.id.btn_control:
                if (deviceInfo != null&& !TextUtils.isEmpty(deviceInfo.getTerminalId())&&deviceInfo.getCanCtrl()==1) {
                    startActivity(new Intent(this, LigitDeviceControlActivity.class).putExtra("terminalId", deviceInfo.getTerminalId()));
                }
                if (terminalInfo != null&& !TextUtils.isEmpty(terminalInfo.getId())&&terminalInfo.getCanCtrl()==1) {
                    startActivity(new Intent(this, TerminalControlActivity.class).putExtra("terminalId",terminalInfo.getId()));
                }
                break;
            case R.id.close:
                finish();
                break;
        }
    }

    public void showIntent(LightDevice lightDevice) {
        if (lightDevice == null) return;
        mlist.clear();
        List<LightDetailBean> list = new ArrayList<>();
//        路灯开关灯状态，0-关灯，1-开灯

        list.add(new LightDetailBean("开关灯状态", lightDevice.getStatus()==0? "关灯":"开灯"));
        list.add(new LightDetailBean("路灯控制器地址", lightDevice.getDeviceAddr() + ""));
        list.add(new LightDetailBean("支路", lightDevice.getLineName() + ""));
        list.add(new LightDetailBean("路灯控制器编号", lightDevice.getDeviceCode() + ""));
        list.add(new LightDetailBean("路灯控制器别名", lightDevice.getDeviceName() + ""));
        list.add(new LightDetailBean("安装时间", lightDevice.getLightInstallTime() + ""));

        list.add(new LightDetailBean("路杆编号", lightDevice.getLightPoleCode() + ""));
        list.add(new LightDetailBean("路杆类型", lightDevice.getLightPoleTypeText() + ""));
        list.add(new LightDetailBean("路杆高度", lightDevice.getLightPoleHeight() + ""));
        list.add(new LightDetailBean("灯头类型", lightDevice.getLightTypeText() + ""));

        list.add(new LightDetailBean("路灯类型", lightDevice.getDeviceType() + ""));
        list.add(new LightDetailBean("主灯类型", lightDevice.getLightMainTypeName() + ""));
        list.add(new LightDetailBean("主灯额定功率(W)", lightDevice.getLightMainPower() + ""));
        list.add(new LightDetailBean("主灯功率阈值(W)", lightDevice.getLightMainPowerLimit() + ""));

        if (lightDevice.getDeviceType() == 2) {
            list.add(new LightDetailBean("辅灯类型", lightDevice.getLightAuxiliaryTypeName() + ""));
            list.add(new LightDetailBean("辅灯额定功率(W)", lightDevice.getLightAuxiliaryPower() + ""));
            list.add(new LightDetailBean("辅灯功率阈值(W)", lightDevice.getLightAuxiliaryPowerLimit() + ""));
        }
        mlist.addAll(list);
        adapter.notifyDataSetChanged();

    }

    public void showIntent(ConcentratorBean concentratorBean) {
        if (concentratorBean == null) return;
        mlist.clear();
        List<LightDetailBean> list = new ArrayList<>();
        list.add(new LightDetailBean("当前状态", concentratorBean.getIsOnlineText() + ""));
        list.add(new LightDetailBean("运行模式", concentratorBean.getMaintenanceModeText() + ""));
        list.add(new LightDetailBean("开灯时间", concentratorBean.getLightOnTime() + ""));
        list.add(new LightDetailBean("关灯时间", concentratorBean.getLightOffTime() + ""));
        list.add(new LightDetailBean("灯控器数", concentratorBean.getLightDeviceCount() + ""));
        list.add(new LightDetailBean("箱门状态", concentratorBean.getDoorStateText() + ""));
        list.add(new LightDetailBean("上电时间", concentratorBean.getPowerOnTime() + ""));
        list.add(new LightDetailBean("掉电时间", concentratorBean.getPowerOffTime() + ""));
        list.add(new LightDetailBean("电压", concentratorBean.getVoltage() + ""));
        list.add(new LightDetailBean("电流", concentratorBean.getAmpere() + ""));
        list.add(new LightDetailBean("功率", concentratorBean.getPower() + ""));
        list.add(new LightDetailBean("电量", concentratorBean.getEnergy() + ""));
        list.add(new LightDetailBean("漏电电流", concentratorBean.getLeakCurrent() + ""));
        list.add(new LightDetailBean("信号", concentratorBean.getSignalStrengthText() + ""));
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
