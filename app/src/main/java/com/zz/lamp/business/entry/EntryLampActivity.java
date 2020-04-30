package com.zz.lamp.business.entry;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.ebo.medialib.qrcode.google.zxing.activity.CaptureActivity;
import com.ebo.medialib.qrcode.util.Constant;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.business.SelectLocationActivity;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampAddPresenter;
import com.zz.lamp.utils.TimeUtils;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryLampActivity extends MyBaseActivity<Contract.IsetTerminalAddPresenter> implements Contract.IGetTerminalAddView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.devicecAddr)
    TextView devicecAddr;
    @BindView(R.id.deviceName)
    EditText deviceName;
    @BindView(R.id.devicecCode)
    EditText devicecCode;
    @BindView(R.id.lightInstallTime)
    TextView lightInstallTime;
    @BindView(R.id.lightPoleCode)
    EditText lightPoleCode;
    @BindView(R.id.lightPoleHeight)
    EditText lightPoleHeight;
    @BindView(R.id.devicecType)
    TextView devicecType;
    @BindView(R.id.lightMainType)
    TextView lightMainType;
    @BindView(R.id.lightMainPower)
    EditText lightMainPower;
    @BindView(R.id.lightMainPowerLimit)
    EditText lightMainPowerLimit;
    @BindView(R.id.lightAuxiliaryType)
    TextView lightAuxiliaryType;
    @BindView(R.id.lightAuxiliaryPower)
    EditText lightAuxiliaryPower;
    @BindView(R.id.lightAuxiliaryPowerLimit)
    EditText lightAuxiliaryPowerLimit;
    @BindView(R.id.ll_auxiliary)
    LinearLayout llAuxiliary;
    @BindView(R.id.lat)
    TextView lat_tv;
    long lightInstallTime_;
    int devicecType_;
    int lightMainType_;
    int lightAuxiliaryType_;
    String terminalId;
    double lat = 0.0;
    double lon = 0.0;
    @Override
    protected int getContentView() {
        return R.layout.activity_lamp;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
    }

    @Override
    protected void initToolBar() {

        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent() {

    }

    @OnClick({R.id.toolbar_subtitle, R.id.devicecAddr, R.id.lightInstallTime, R.id.devicecType, R.id.lightMainType, R.id.lightAuxiliaryType, R.id.lat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                break;
            case R.id.devicecAddr:
                startQrCode();
                break;
            case R.id.lightInstallTime:
                DatePickDialog dialog = new DatePickDialog(EntryLampActivity.this);
                //设置标题
                dialog.setTitle("选择时间");
                dialog.setYearLimt(20);
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        lightInstallTime_ = date.getTime();
                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        lightInstallTime.setText(time);
                    }
                });
                dialog.show();
                break;
            case R.id.devicecType:
                String[] PLANETS2 = new String[]{"单灯", "双灯"};
                final SelectPopupWindows selectPopupWindows2 = new SelectPopupWindows(this, PLANETS2);
                selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {

                        llAuxiliary.setVisibility(index == 0 ? View.GONE : View.VISIBLE);

                        devicecType_ = index + 1;
                        devicecType.setText(msg);
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
            case R.id.lightMainType:
                break;
            case R.id.lightAuxiliaryType:
                break;
            case R.id.lat:
                startActivityForResult(new Intent(this, SelectLocationActivity.class), 1002);

                break;
        }
    }

    private void startQrCode() {

        PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.CAMERA}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
                // 二维码扫码
                Intent intent = new Intent(EntryLampActivity.this, CaptureActivity.class);
                startActivityForResult(intent, Constant.REQ_QR_CODE);
            }

            @Override
            public void onDenied() {

            }
        });
    }

    @Override
    public LampAddPresenter initPresenter() {
        return new LampAddPresenter(this);
    }
    void postData(){
        Map<String, Object> params = new HashMap<>();

        params.put("terminalId",terminalId);

        String devicecAddr_ = devicecAddr.getText().toString();
        if (TextUtils.isEmpty(devicecAddr_)){
            showToast("请输入路灯控制器地址");
            return;
        }
        params.put("terminalAddr",devicecAddr_);

        String deviceName_ = deviceName.getText().toString();
        if (TextUtils.isEmpty(deviceName_)){
            showToast("请输入路灯控制器别名");
            return;
        }
        params.put("deviceName",deviceName_);

        String devicecCode_ = devicecCode.getText().toString();
        if (TextUtils.isEmpty(devicecCode_)){
            showToast("请输入路灯控制器别名");
            return;
        }
        params.put("devicecCode",devicecCode_);


        if (lightInstallTime_==0.0){
            showToast("请选择安装时间");
            return;
        }
        params.put("lightInstallTime",lightInstallTime_);

        String lightPoleCode_ = lightPoleCode.getText().toString();
        if (TextUtils.isEmpty(lightPoleCode_)){
            showToast("请输入灯杆编号");
            return;
        }
        params.put("lightPoleCode",lightPoleCode_);

        String lightPoleHeight_ = lightPoleHeight.getText().toString();
        if (TextUtils.isEmpty(lightPoleHeight_)){
            showToast("请输入灯杆高度");
            return;
        }
        params.put("lightPoleHeight",lightPoleHeight_);

        if (devicecType_ == 0){
            showToast("请选择路灯类型");
            return;
        }
        params.put("devicecType",devicecType_);

        if (lightMainType_ == 0){
            showToast("请选择主灯类型");
            return;
        }
        params.put("lightMainType",lightMainType_);

        String lightMainPower_ = lightMainPower.getText().toString();
        if (TextUtils.isEmpty(lightMainPower_)){
            showToast("请输入主灯额定功率(W)");
            return;
        }
        params.put("lightMainPower",lightMainPower_);

        String lightMainPowerLimit_ = lightMainPowerLimit.getText().toString();
        if (TextUtils.isEmpty(lightMainPowerLimit_)){
            showToast("请输入主灯功率阈值(W)");
            return;
        }
        params.put("lightMainPowerLimit",lightMainPowerLimit_);
        if (devicecType_==2){
            if (lightAuxiliaryType_ == 0){
                showToast("请选择辅灯类型");
                return;
            }
            params.put("lightAuxiliaryType",lightAuxiliaryType_);
            String lightAuxiliaryPower_ = lightAuxiliaryPower.getText().toString();
            if (TextUtils.isEmpty(lightAuxiliaryPower_)){
                showToast("请输入辅灯额定功率(W)");
                return;
            }
            params.put("lightAuxiliaryPower",lightAuxiliaryPower_);

            String lightAuxiliaryPowerLimit_ = lightAuxiliaryPowerLimit.getText().toString();
            if (TextUtils.isEmpty(lightAuxiliaryPowerLimit_)){
                showToast("辅灯功率阈值(W)");
                return;
            }
            params.put("lightAuxiliaryPowerLimit",lightAuxiliaryPowerLimit_);
        }

        if (lat==0.0||lon==0.0){
            showToast("请选择经纬度");
            return;
        }
        params.put("lat",lat);
        params.put("lon",lon);
        mPresenter.postTerminal(params);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            if (!TextUtils.isEmpty(scanResult)) {
                devicecAddr.setText(scanResult);
            }
        }
    }
}
