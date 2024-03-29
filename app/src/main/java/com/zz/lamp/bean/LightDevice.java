package com.zz.lamp.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class LightDevice implements Serializable {
    private  String deviceName;//	string	路灯控制器别名
    private  String deviceAddr;//	string	路灯控制器地址
    private  String   deviceCode;//	string	路灯控制器编号
    private  String  deviceLat;//	string	纬度
    private  String  deviceLng	;//string	经度
    private  Integer   deviceType;//	integer	路灯类型
    private  String   deviceTypeText;//	integer	路灯类型
    private  Integer   status;//	integer	开关灯状态
    private  String   id	;//integer	主键id
    private  String lightAuxiliaryPower;//	number	辅灯额定功率(W)
    private  String  lightAuxiliaryPowerLimit;//	number	辅灯功率阈值(W)
    private  Integer  lightAuxiliaryType;//	integer	辅灯类型
    private  String  lightAuxiliaryTypeText;//	integer	辅灯类型
    private  String  lightInstallTime;//	string	安装时间
    private  String   lightMainPower	;//number	主灯额定功率(W)
    private  String  lightMainPowerLimit	;//number	主灯功率阈值(W)
    private  Integer  lightMainType	;//integer	主灯类型
    private  String  lightMainTypeText	;//integer	主灯类型
    private  String   lightPoleCode;//	string	灯杆编号
    private  String  lightPoleHeight	;//string	灯杆高度
    private  String  lightPoleType	;//integer	灯杆类型
    private  String  lightPoleTypeText	;//integer	灯杆类型
    private  String   lightType	;//integer	灯头类型
    private  String   lineId	;//integer	支路id
    private  String   lineName	;//string	支路名称
    private  String  terminalId	;//integer	集中控制器id
    private  String   lightTypeText;//	integer	路灯类型
    private  Integer   canCtrl;//	1-可以，0-不可以
    private  Integer   warnStatus;//	1-可以，0-不可以
    private  String   warnContent;//	1-可以，0-不可以

    public Integer getWarnStatus() {
        return warnStatus;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public String getDeviceTypeText() {
        return deviceTypeText;
    }

    public String getLightTypeText() {
        return lightTypeText;
    }

    public Integer getStatus() {
        if (status == null) return 0;
        return status;
    }

    public Integer getCanCtrl() {
        return canCtrl;
    }

    public String getLightPoleTypeText() {
        return lightPoleTypeText;
    }

    public String getLightMainTypeName() {
        return lightMainTypeText;
    }

    public String getLightAuxiliaryTypeName() {
        return lightAuxiliaryTypeText;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public Double getDeviceLat() {
        if (TextUtils.isEmpty(deviceLat))return 0.0;
        return Double.parseDouble(deviceLat);
    }

    public Double getDeviceLng() {
        if (TextUtils.isEmpty(deviceLng))return 0.0;
        return Double.parseDouble(deviceLng);
    }

    public Integer getDeviceType() {
        if (deviceType==null) return 0;
        return deviceType;
    }

    public String getId() {
        return id;
    }

    public String getLightAuxiliaryPower() {
        return lightAuxiliaryPower;
    }

    public String getLightAuxiliaryPowerLimit() {
        return lightAuxiliaryPowerLimit;
    }

    public Integer getLightAuxiliaryType() {
        if (lightAuxiliaryType==null) return 0;
        return lightAuxiliaryType;
    }

    public String getLightInstallTime() {
        return lightInstallTime;
    }

    public String getLightMainPower() {
        return lightMainPower;
    }

    public String getLightMainPowerLimit() {
        return lightMainPowerLimit;
    }

    public Integer getLightMainType() {
        if (lightMainType==null) return 0;
        return lightMainType;
    }

    public String getLightPoleCode() {
        return lightPoleCode;
    }

    public String getLightPoleHeight() {
        return lightPoleHeight;
    }

    public String getLightPoleType() {
        return lightPoleType;
    }

    public String getLightType() {
        return lightType;
    }

    public String getLineId() {
        if (lineId==null) return "";
        return lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public String getTerminalId() {
        return terminalId;
    }
}
