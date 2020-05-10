package com.zz.lamp.business.main;

import android.app.Activity;
import android.os.Bundle;
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
import com.zz.lamp.business.entry.adapter.LightDetailAdapter;
import com.zz.lamp.business.main.adapter.DetailAdapter;
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
    DetailAdapter adapter ;
    Double lat;
    Double lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(R.layout.item_detail,mlist);
        infoRv.setAdapter(adapter);
        ConcentratorBean terminalInfo = (ConcentratorBean) getIntent().getSerializableExtra("TerminalInfo");
        LightDevice deviceInfo = (LightDevice) getIntent().getSerializableExtra("DeviceInfo");
        if (deviceInfo!=null){
            showIntent(deviceInfo);
            lat = deviceInfo.getDevicecLat();
            lon = deviceInfo.getDevicecLng();
        }if (deviceInfo!=null){
            lat = terminalInfo.getTerminalLat();
            lon = terminalInfo.getTerminalLng();
        }

    }

    @OnClick({R.id.btn_nav, R.id.btn_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_nav:
                if (!NavUtils.isInstalled()){
                    ToastUtils.showToast("未安装百度地图");
                    return;
                }else {
                    NavUtils.invokeNavi(this,null,"中智.智慧路灯",lat+","+lon);
                }
                break;
            case R.id.btn_control:

                break;
        }
    }
    public void showIntent(LightDevice lightDevice) {
        if (lightDevice == null) return;
        mlist.clear();
        mlist.add(new LightDetailBean("路灯控制器地址",lightDevice.getDevicecAddr()+""));
        mlist.add(new LightDetailBean("支路",lightDevice.getLineName()+""));
        mlist.add(new LightDetailBean("路灯控制器编号",lightDevice.getDevicecCode()+""));
        mlist.add(new LightDetailBean("路灯控制器别名",lightDevice.getDeviceName()+""));
        mlist.add(new LightDetailBean("安装时间",lightDevice.getLightInstallTime()+""));

        mlist.add(new LightDetailBean("路杆编号",lightDevice.getLightPoleCode()+""));
        mlist.add(new LightDetailBean("路杆类型",lightDevice.getLightPoleType()+""));
        mlist.add(new LightDetailBean("路杆高度",lightDevice.getLightPoleHeight()+""));
        mlist.add(new LightDetailBean("灯头类型",lightDevice.getLightType()+""));

        mlist.add(new LightDetailBean("路灯类型",lightDevice.getDevicecType()+""));
        mlist.add(new LightDetailBean("主灯类型",lightDevice.getLightMainTypeName()+""));
        mlist.add(new LightDetailBean("主灯额定功率(W)",lightDevice.getLightMainPower()+""));
        mlist.add(new LightDetailBean("主灯功率阈值(W)",lightDevice.getLightMainPowerLimit()+""));

        if (lightDevice.getDevicecType()==2){
            mlist.add(new LightDetailBean("辅灯类型",lightDevice.getLightAuxiliaryTypeName()+""));
            mlist.add(new LightDetailBean("辅灯额定功率(W)",lightDevice.getLightAuxiliaryPower()+""));
            mlist.add(new LightDetailBean("辅灯功率阈值(W)",lightDevice.getLightAuxiliaryPowerLimit()+""));
        }
        adapter.notifyDataSetChanged();

    }
}
