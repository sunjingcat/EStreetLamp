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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.search.core.PoiInfo;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.ebo.medialib.qrcode.google.zxing.activity.CaptureActivity;
import com.ebo.medialib.qrcode.util.Constant;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.map.SelectLocationActivity;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampAddPresenter;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.utils.TimeUtils;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryLampActivity extends MyBaseActivity<Contract.IsetLampAddPresenter> implements Contract.IGetLampAddView {

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
    String lightPoleType_;
    String lightType_;
    String terminalId;
    String lineId;
    double lat = 0.0;
    double lon = 0.0;
    List<String> types = new ArrayList<>();
    List<DeviceType> deviceTypes = new ArrayList<>();
    @BindView(R.id.lightPoleType)
    TextView lightPoleType;
    @BindView(R.id.lightType)
    TextView lightType;
    @BindView(R.id.lineName)
    TextView lineName;
    @BindView(R.id.bg)
    LinearLayout bg;
    List<DictBean> light_type_list = new ArrayList();
    List<String> light_type_arry = new ArrayList();
    List<DictBean> light_pole_type_list = new ArrayList();
    List<String> light_pole_type_array = new ArrayList();
    @BindView(R.id.tv_devicecAddr)
    TextView tvDevicecAddr;
    @BindView(R.id.tv_deviceName)
    TextView tvDeviceName;
    @BindView(R.id.tv_devicecCode)
    TextView tvDevicecCode;

    @Override
    protected int getContentView() {
        return R.layout.activity_lamp;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
        showInfo();
        mPresenter.getLightDeviceType();
        mPresenter.getLightPoleType();
        mPresenter.getLightType();
    }

    @Override
    protected void initToolBar() {

        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent() {
        devicecAddr.setText("");
        showToast("提交成功");
    }

    @Override
    public void showLightDeviceType(List<DeviceType> list) {
        if (list == null) return;
        deviceTypes = list;
        types = new ArrayList<>();
        for (DeviceType deviceType : list) {
            types.add(deviceType.getModelName());
        }
    }

    @Override
    public void showLightType(List<DictBean> list) {
        if (list == null) return;
        light_type_list.clear();
        light_type_list.addAll(list);
        light_type_arry = new ArrayList<>();
        for (DictBean dictBean : list) {
            light_type_arry.add(dictBean.getDictLabel());
        }
    }

    @Override
    public void showLightPoleType(List<DictBean> list) {
        if (list == null) return;
        light_pole_type_list.clear();
        light_pole_type_list.addAll(list);
        light_pole_type_array = new ArrayList<>();
        for (DictBean dictBean : list) {
            light_pole_type_array.add(dictBean.getDictLabel());
        }
    }

    @Override
    public void showCheckAddrIntent(JsonT jsonT) {
        if (!jsonT.isSuccess()) {
            tvDevicecAddr.setTextColor(getResources().getColor(R.color.red_e8));
            showToast(jsonT.getMessage());
        } else {
            tvDevicecAddr.setTextColor(getResources().getColor(R.color.colorTextBlack33));
            postData(2);
        }
    }

    @Override
    public void showCheckNameIntent(JsonT jsonT) {
        if (!jsonT.isSuccess()) {
            tvDeviceName.setTextColor(getResources().getColor(R.color.red_e8));
            showToast(jsonT.getMessage());
        } else {
            tvDeviceName.setTextColor(getResources().getColor(R.color.colorTextBlack33));
            postData(3);
        }
    }

    @Override
    public void showCheckCodeIntent(JsonT jsonT) {
        if (!jsonT.isSuccess()) {
            tvDevicecCode.setTextColor(getResources().getColor(R.color.red_e8));
            showToast(jsonT.getMessage());
        } else {
            tvDevicecCode.setTextColor(getResources().getColor(R.color.colorTextBlack33));
            postData(0);
        }
    }

    @OnClick({R.id.lineName, R.id.toolbar_subtitle, R.id.devicecAddr, R.id.lightInstallTime, R.id.devicecType, R.id.lightMainType, R.id.lightAuxiliaryType, R.id.lat, R.id.lightPoleType, R.id.lightType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                postData(1);
                break;
            case R.id.devicecAddr:
                startQrCode();
                break;
            case R.id.lineName:
                startActivityForResult(new Intent(EntryLampActivity.this, LineActivity.class).putExtra("terminalId", terminalId), 1001);
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
                selectDeviceType(1);
                break;
            case R.id.lightAuxiliaryType:
                selectDeviceType(2);
                break;
            case R.id.lat:
                startActivityForResult(new Intent(this, SelectLocationActivity.class), 1002);
                break;
            case R.id.lightPoleType:
                selectLightType(2, light_pole_type_array);
                break;
            case R.id.lightType:
                selectLightType(1, light_type_arry);
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

    void postData(int check) {
        Map<String, Object> params = new HashMap<>();

        params.put("terminalId", terminalId);

        String deviceAddr_ = devicecAddr.getText().toString();
        if (TextUtils.isEmpty(deviceAddr_)) {
            showToast("请输入路灯控制器地址");
            return;
        }
        params.put("deviceAddr", deviceAddr_);
        if (check == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("deviceAddr", deviceAddr_);
            map.put("terminalId", terminalId);
            mPresenter.checkDeviceAddr(map);
            return;
        }
        String deviceName_ = deviceName.getText().toString();
        if (TextUtils.isEmpty(deviceName_)) {
            showToast("请输入路灯控制器别名");
            return;
        }
        params.put("deviceName", deviceName_);
        if (check == 2) {
            Map<String, Object> map = new HashMap<>();
            map.put("deviceName", deviceName_);
            map.put("terminalId", terminalId);
            mPresenter.checkDeviceName(map);
            return;
        }
        String devicecCode_ = devicecCode.getText().toString();
        if (TextUtils.isEmpty(devicecCode_)) {
            showToast("请输入路灯控制器别名");
            return;
        }
        params.put("deviceCode", devicecCode_);

        if (check == 3) {
            Map<String, Object> map = new HashMap<>();
            map.put("deviceCode", devicecCode_);
            map.put("terminalId", terminalId);
            mPresenter.checkDeviceCode(map);
            return;
        }
        if (TextUtils.isEmpty(lineId)) {
            showToast("请选择支路");
            return;
        }
        params.put("lineId", lineId);


        if (lightInstallTime_ == 0.0) {
            showToast("请选择安装时间");
            return;
        }
        params.put("lightInstallTime", TimeUtils.getTime(lightInstallTime_,TimeUtils.DATE_FORMAT_DATE) );

        String lightPoleCode_ = lightPoleCode.getText().toString();
        if (TextUtils.isEmpty(lightPoleCode_)) {
            showToast("请输入灯杆编号");
            return;
        }
        params.put("lightPoleCode", lightPoleCode_);

        String lightPoleHeight_ = lightPoleHeight.getText().toString();
        if (TextUtils.isEmpty(lightPoleHeight_)) {
            showToast("请输入灯杆高度");
            return;
        }
        params.put("lightPoleHeight", lightPoleHeight_);


        if (TextUtils.isEmpty(lightPoleType_)) {
            showToast("请选择灯杆类型");
            return;
        }
        params.put("lightPoleType", lightPoleType_);

        if (TextUtils.isEmpty(lightType_)) {
            showToast("请选择灯头类型");
            return;
        }
        params.put("lightType", lightType_);
        if (devicecType_ == 0) {
            showToast("请选择路灯类型");
            return;
        }
        params.put("deviceType", devicecType_);

        if (lightMainType_ == 0) {
            showToast("请选择主灯类型");
            return;
        }
        params.put("lightMainType", lightMainType_);

        String lightMainPower_ = lightMainPower.getText().toString();
        if (TextUtils.isEmpty(lightMainPower_)) {
            showToast("请输入主灯额定功率(W)");
            return;
        }
        params.put("lightMainPower", lightMainPower_);

        String lightMainPowerLimit_ = lightMainPowerLimit.getText().toString();
        if (TextUtils.isEmpty(lightMainPowerLimit_)) {
            showToast("请输入主灯功率阈值(W)");
            return;
        }
        params.put("lightMainPowerLimit", lightMainPowerLimit_);
        if (devicecType_ == 2) {
            if (lightAuxiliaryType_ == 0) {
                showToast("请选择辅灯类型");
                return;
            }
            params.put("lightAuxiliaryType", lightAuxiliaryType_);
            String lightAuxiliaryPower_ = lightAuxiliaryPower.getText().toString();
            if (TextUtils.isEmpty(lightAuxiliaryPower_)) {
                showToast("请输入辅灯额定功率(W)");
                return;
            }
            params.put("lightAuxiliaryPower", lightAuxiliaryPower_);

            String lightAuxiliaryPowerLimit_ = lightAuxiliaryPowerLimit.getText().toString();
            if (TextUtils.isEmpty(lightAuxiliaryPowerLimit_)) {
                showToast("辅灯功率阈值(W)");
                return;
            }
            params.put("lightAuxiliaryPowerLimit", lightAuxiliaryPowerLimit_);
        }

        if (lat == 0.0 || lon == 0.0) {
            showToast("请选择经纬度");
            return;
        }
        params.put("lat", lat);
        params.put("lon", lon);

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
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            lineId = data.getStringExtra("lineId" )+"";
            lineName.setText(data.getStringExtra("lineName") + "");
        }
        if (requestCode == 1002 && resultCode == RESULT_OK) {
            if (data == null) return;
            PoiInfo poiInfo = data.getParcelableExtra("location");
            lat = poiInfo.location.latitude;
            lon = poiInfo.location.longitude;
            lat_tv.setText(poiInfo.address + "");
        }
    }

    public void selectDeviceType(int type) {
        String[] arr = (String[]) types.toArray(new String[types.size()]);
        final SelectPopupWindows selectPopupWindows = new SelectPopupWindows(this, arr);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (type == 1) {
                    lightMainType_ = deviceTypes.get(index).getId();
                    lightMainType.setText(msg);
                }
                if (type == 2) {
                    lightAuxiliaryType_ = deviceTypes.get(index).getId();
                    lightAuxiliaryType.setText(msg);
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    public void selectLightType(int type, List<String> array) {
        String[] arr = (String[]) array.toArray(new String[array.size()]);
        final SelectPopupWindows selectPopupWindows = new SelectPopupWindows(this, arr);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (type == 1) {
                    lightType_ = light_type_list.get(index).getDictValue();
                    lightType.setText(msg);
                }
                if (type == 2) {
                    lightPoleType_ = light_pole_type_list.get(index).getDictValue();
                    lightPoleType.setText(msg);
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    void showInfo() {
        LightDevice device = (LightDevice) getIntent().getSerializableExtra("device");
        if (device == null) return;
        devicecCode.setText(device.getDeviceCode() + "");
        lightInstallTime.setText(device.getLightInstallTime()+"");
        lightInstallTime_ = TimeUtils.parseTime(device.getLightInstallTime(), TimeUtils.DATE_FORMAT_DATE).getTime();
        lightPoleCode.setText(device.getLightPoleCode() + "");
        lightPoleHeight.setText(device.getLightPoleHeight() + "");
        lightPoleType.setText(device.getLightPoleType() + "");
        lightPoleType_ = device.getLightPoleType();//TODO
        lightType.setText(device.getLightType() + "");
        lightType_ = device.getLightType();//TODO
        if (device.getDeviceType() == 1) {
            devicecType.setText("单灯");
            llAuxiliary.setVisibility(View.GONE);
        }
        if (device.getDeviceType() == 2) {
            devicecType.setText("双灯");
            llAuxiliary.setVisibility(View.VISIBLE);
        }
        devicecType_ = device.getDeviceType();

        lightMainType.setText(device.getLightMainTypeName() + "");
        lightMainType_ = device.getLightMainType();
        lightMainPower.setText(device.getLightMainPower() + "");
        lightMainPowerLimit.setText(device.getLightMainPowerLimit() + "");
        lineName.setText(device.getLineName() + "");
        lineId = device.getLineId();
        if (devicecType_== 2) {
            lightAuxiliaryType.setText(device.getLightAuxiliaryTypeName() + "");
            lightAuxiliaryType_ = device.getLightAuxiliaryType();
            lightAuxiliaryPower.setText(device.getLightAuxiliaryPower() + "");
            lightAuxiliaryPowerLimit.setText(device.getLightAuxiliaryPowerLimit() + "");
        }
        lat_tv.setText(device.getDeviceLat() + "," + device.getDeviceLng());
        lat = device.getDeviceLat();
        lon = device.getDeviceLng();
    }

}
