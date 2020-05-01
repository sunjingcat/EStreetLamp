package com.zz.lamp.bean;

import java.io.Serializable;

public class LightDevice implements Serializable {
    private  String deviceName;//	string	路灯控制器别名
    private  String devicecAddr;//	string	路灯控制器地址
    private  String   devicecCode;//	string	路灯控制器编号
    private  String  devicecLat;//	string	纬度
    private  String  devicecLng	;//string	经度
    private  Integer   devicecType;//	integer	路灯类型
    private  String   id	;//integer	主键id
    private  String lightAuxiliaryPower;//	number	辅灯额定功率(W)
    private  String  lightAuxiliaryPowerLimit;//	number	辅灯功率阈值(W)
    private  Integer  lightAuxiliaryType;//	integer	辅灯类型
    private  Integer  lightAuxiliaryTypeName;//	integer	辅灯类型
    private  Long  lightInstallTime;//	string	安装时间
    private  String   lightMainPower	;//number	主灯额定功率(W)
    private  String  lightMainPowerLimit	;//number	主灯功率阈值(W)
    private  Integer  lightMainType	;//integer	主灯类型
    private  String  lightMainTypeName	;//integer	主灯类型
    private  String   lightPoleCode;//	string	灯杆编号
    private  String  lightPoleHeight	;//string	灯杆高度
    private  String  lightPoleType	;//integer	灯杆类型
    private  String   lightType	;//integer	灯头类型
    private  String   lineId	;//integer	支路id
    private  String   lineName	;//string	支路名称
    private  String  terminalId	;//integer	集中控制器id

    public String getLightMainTypeName() {
        return lightMainTypeName;
    }

    public Integer getLightAuxiliaryTypeName() {
        return lightAuxiliaryTypeName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDevicecAddr() {
        return devicecAddr;
    }

    public String getDevicecCode() {
        return devicecCode;
    }

    public String getDevicecLat() {
        return devicecLat;
    }

    public String getDevicecLng() {
        return devicecLng;
    }

    public Integer getDevicecType() {
        return devicecType;
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
        return lightAuxiliaryType;
    }

    public Long getLightInstallTime() {
        return lightInstallTime;
    }

    public String getLightMainPower() {
        return lightMainPower;
    }

    public String getLightMainPowerLimit() {
        return lightMainPowerLimit;
    }

    public Integer getLightMainType() {
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
        return lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public String getTerminalId() {
        return terminalId;
    }
}
