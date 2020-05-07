package com.zz.lamp.business.entry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.search.core.PoiInfo;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.SelectLocationActivity;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.TerminalAddPresenter;
import com.zz.lamp.net.JsonT;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EntryJzqActivity extends MyBaseActivity<Contract.IsetTerminalAddPresenter> implements Contract.IGetTerminalAddView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.tv_terminalAddr)
    TextView tv_terminalAddr;
    @BindView(R.id.tv_terminalName)
    TextView tv_terminalName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.terminalAddr)
    EditText terminalAddr;
    @BindView(R.id.terminalName)
    EditText terminalName;
    @BindView(R.id.terminalType)
    TextView terminalType;
    @BindView(R.id.loopCount)
    EditText loopCount;
    @BindView(R.id.lineCount)
    EditText lineCount;
    @BindView(R.id.loopTransformerRatio)
    EditText loopTransformerRatio;
    @BindView(R.id.lineTransformerRatio)
    EditText lineTransformerRatio;
    @BindView(R.id.alarmDelayedTime)
    EditText alarmDelayedTime;
    @BindView(R.id.relayOnDelayedTime)
    EditText relayOnDelayedTime;
    @BindView(R.id.lat)
    TextView tv_lat;
    String areaId;
    String areaName;
    int type = 0;

    double lat = 0.0;
    double lon = 0.0;

    @Override
    protected int getContentView() {
        return R.layout.activity_entry_jzq;
    }

    @Override
    public TerminalAddPresenter initPresenter() {
        return new TerminalAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.tv_area, R.id.terminalType, R.id.lat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData(1);
                break;
            case R.id.tv_area:
                startActivityForResult(new Intent(EntryJzqActivity.this, RegionActivity.class), 1001);
                break;
            case R.id.terminalType:
                String[] PLANETS2 = new String[]{"塔吊", "升降机", "塔吊黑匣子", "环境监测设备", "其他"};
                final SelectPopupWindows selectPopupWindows2 = new SelectPopupWindows(this, PLANETS2);
                selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
//                        equipmentType=index+1;
//                        deviceEquipmentType.setText(msg);
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
            case R.id.lat:
                startActivityForResult(new Intent(EntryJzqActivity.this, SelectLocationActivity.class), 1002);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:
                if (data == null) return;
                areaId = data.getStringExtra("areaId");
                areaName = data.getStringExtra("areaName");
                tvArea.setText(areaName + "");
                break;
            case 1002:
                if (data == null) return;
                PoiInfo poiInfo = data.getParcelableExtra("location");
                lat = poiInfo.location.latitude;
                lon = poiInfo.location.longitude;
                tv_lat.setText(poiInfo.address + "");
                break;
        }
    }

    void postData(int check) {
        Map<String, Object> params = new HashMap<>();
        if (TextUtils.isEmpty(areaId) || TextUtils.isEmpty(areaName)) {
            showToast("请选择区域");
            return;
        }
        params.put("areaId", areaId);
        params.put("areaName", areaName);

        String addr = terminalAddr.getText().toString();
        if (TextUtils.isEmpty(addr)) {
            showToast("请输入集中器地址");
            return;
        }
        if (addr.length() != 8) {
            showToast("请输入正确的8位集中器地址");
            return;
        }
        params.put("terminalAddr", addr);
        if (check ==1){
            Map<String, Object> map = new HashMap<>();
            map.put("terminalAddr",terminalAddr);
            mPresenter.checkTerminalAddr(map);
            return;
        }
        String name = terminalName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入集中器别名");
            return;
        }
        params.put("terminalName", name);
        if (check == 2){
            Map<String, Object> map = new HashMap<>();
            map.put("terminalName",terminalName);
            mPresenter.checkTerminalName(map);
            return;
        }

        String count = loopCount.getText().toString();
        if (TextUtils.isEmpty(count)) {
            showToast("请输入回路数量");
            return;
        }
        params.put("loopCount", count);

        String line_Count = lineCount.getText().toString();
        if (TextUtils.isEmpty(line_Count)) {
            showToast("请输入支路数量");
            return;
        }
        params.put("lineCount", line_Count);

        String transformerRatio = loopTransformerRatio.getText().toString();
        if (TextUtils.isEmpty(transformerRatio)) {
            showToast("请输入回路互感器变比");
            return;
        }
        params.put("loopTransformerRatio", transformerRatio);

        String line_transformerRatio = lineTransformerRatio.getText().toString();
        if (TextUtils.isEmpty(line_transformerRatio)) {
            showToast("请输入相线互感器变比");
            return;
        }
        params.put("lineTransformerRatio", line_transformerRatio);

        String alarmDelayedTime_ = alarmDelayedTime.getText().toString();
        if (TextUtils.isEmpty(alarmDelayedTime_)) {
            showToast("请输入报警延时");
            return;
        }
        params.put("alarmDelayedTime", alarmDelayedTime_);

        String relayOnDelayedTime_ = relayOnDelayedTime.getText().toString();
        if (TextUtils.isEmpty(relayOnDelayedTime_)) {
            showToast("请输入上电合闸延时");
            return;
        }
        params.put("relayOnDelayedTime", relayOnDelayedTime_);

        /**
         *
         */
        if (lat == 0.0 || lon == 0.0) {
            showToast("请选择经纬度");
            return;
        }
        params.put("lat", lat);
        params.put("lon", lon);

        mPresenter.postTerminal(params);

    }

    @Override
    public void showIntent() {
        showToast("添加成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showCheckAddrIntent(JsonT jsonT) {
        if (!jsonT.isSuccess()){
            tv_terminalAddr.setTextColor(getResources().getColor(R.color.red_e8));
            showToast(jsonT.getMessage());
        }else {
            tv_terminalAddr.setTextColor(getResources().getColor(R.color.colorTextBlack33));
            postData(2);
        }
    }

    @Override
    public void showCheckNameIntent(JsonT jsonT) {
        if (!jsonT.isSuccess()){
            tv_terminalName.setTextColor(getResources().getColor(R.color.red_e8));
            showToast(jsonT.getMessage());
        }else {
            tv_terminalName.setTextColor(getResources().getColor(R.color.colorTextBlack33));
            postData(0);
        }
    }



}
