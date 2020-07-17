package com.zz.lamp.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class ConcentratorBean implements Serializable {
    private String searchValue;// null,
    private String createBy;// czxy,
    private String createTime;// 2020-04-08 15;//54;//17,
    private String updateBy;// czxy,
    private String updateTime;// 2020-04-10 16;//15;//35,
    private String remark;// null,
    private String deptId;// 116,
    private String isRecordSyncText;// 116,
    private String recordSyncTime;// 116,

    private String area;// null,
    private String id;// 2,
    private String terminalAddr;// 20040801,
    private String terminalName;// 静心湖,
    private String terminalSim;// 20200408202004088888,
    private String terminalType;// 1,
    private Double terminalLng;// 116.7916727236518,
    private Double terminalLat;// 38.290273307174054,
    private String areaId;// 3,
    private String areaName;// 沧州师范学院,
    private String isDoorCheck;// 0,
    private String preEnergyQuantity;// 200,
    private String loopCount;// 1,
    private String lineCount;// 1,
    private String loopTransformerRatio;// 20,
    private String lineTransformerRatio;// 20,
    private String relayOnDelayedTime;// 5,
    private String alarmDelayedTime;// 5
    private int terminalOnOff;
    private int isOnline;
    private String terminalOnOffText;
    private String isOnlineText;
    private int maintenanceMode;
    private String maintenanceModeText;

    private String   simCode;// 89860422021980117827,

    private  Integer   canCtrl;//	1-可以，0-不可以
    private String   signalStrength;// 10,

    private String   signalStrengthText;// 弱,
    private String  lightOnTime;// 11;//45;//00,
    private String   lightOffTime;// 12;//00;//00,
    private String   powerOnTime;// 2020-05-12 08;//43;//56,
    private String   powerOffTime;// 2020-04-11 16;//14;//26,
    private String  reportState;// null,
    private String    lineState;// 00000000,
    private String   switchState;// 0000000000000000,
    private String  reportStateText;// null,
    private String   groupLuminance;// 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    private Integer    doorState;// 1,
    private String   doorStateText;// 开,
    private String   voltageA;// 233.4,
    private String   ampereA;// 0.0,
    private String   powerA;// 0.0,
    private String    energyA;// 60.64,
    private String   voltageB;// 233.0,
    private String   ampereB;// 0.0,
    private String   powerB;// 0.0,
    private String   energyB;// 0.01,
    private String  voltageC;// 233.0,
    private String  ampereC;// 0.0,
    private String  powerC;// 0.0,
    private String  energyC;// 0.01,
    private String  leakCurrent;// 0.0 A,
    private String  voltage;// 233.4 V,
    private String  ampere;// 0.0 A,
    private String   power;// 0.0 KW,
    private String   energy;// 60.66 KWH,
    private Integer   nowState;// 3,
    private String   nowStateText;// 定时上报,
    private String   lightDeviceCount;// 2,
    private String  lightDeviceSucceedCount;// 0
    private String  lightDeviceActualSum;// 0
    private  Integer   warnStatus;//	1-可以，0-不可以
    private  String   warnContent;//	1-可以，0-不可以

    public Integer getWarnStatus() {
        return warnStatus;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public int getTerminalOnOff() {
        return terminalOnOff;
    }

    public String getLightDeviceActualSum() {
        return lightDeviceActualSum;
    }

    public Integer getCanCtrl() {
        return canCtrl;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public String getTerminalOnOffText() {
        return terminalOnOffText;
    }

    public String getIsOnlineText() {
        return isOnlineText;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getDeptId() {
        return deptId;
    }


    public String getArea() {
        return area;
    }

    public String getId() {
        return id;
    }

    public String getTerminalAddr() {
        return terminalAddr;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public String getTerminalSim() {
        return terminalSim;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public Double getTerminalLng() {
        if (terminalLng == null)return 0.0;
        return terminalLng;
    }

    public Double getTerminalLat() {
        if (terminalLat == null)return 0.0;
        return terminalLat;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getIsDoorCheck() {
        return isDoorCheck;
    }

    public String getPreEnergyQuantity() {
        return preEnergyQuantity;
    }

    public String getLoopCount() {
        return loopCount;
    }

    public String getLineCount() {
        return lineCount;
    }

    public String getLoopTransformerRatio() {
        return loopTransformerRatio;
    }

    public String getLineTransformerRatio() {
        return lineTransformerRatio;
    }

    public String getRelayOnDelayedTime() {
        return relayOnDelayedTime;
    }

    public int getMaintenanceMode() {
        return maintenanceMode;
    }

    public String getMaintenanceModeText() {
        return maintenanceModeText;
    }

    public String getSimCode() {
        return simCode;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public String getSignalStrengthText() {
        return signalStrengthText;
    }

    public String getLightOnTime() {
        return lightOnTime;
    }

    public String getLightOffTime() {
        return lightOffTime;
    }

    public String getPowerOnTime() {
        return powerOnTime;
    }

    public String getPowerOffTime() {
        if(TextUtils.isEmpty(powerOffTime)){
            return "未掉电";
        }
        return powerOffTime;
    }

    public String getReportState() {
        return reportState;
    }

    public String getLineState() {
        return lineState;
    }

    public String getSwitchState() {
        return switchState;
    }

    public String getReportStateText() {
        return reportStateText;
    }

    public String getGroupLuminance() {
        return groupLuminance;
    }

    public Integer getDoorState() {
        return doorState;
    }

    public String getDoorStateText() {
        return doorStateText;
    }

    public String getVoltageA() {
        return voltageA;
    }

    public String getAmpereA() {
        return ampereA;
    }

    public String getPowerA() {
        return powerA;
    }

    public String getEnergyA() {
        return energyA;
    }

    public String getVoltageB() {
        return voltageB;
    }

    public String getAmpereB() {
        return ampereB;
    }

    public String getPowerB() {
        return powerB;
    }

    public String getEnergyB() {
        return energyB;
    }

    public String getVoltageC() {
        return voltageC;
    }

    public String getAmpereC() {
        return ampereC;
    }

    public String getPowerC() {
        return powerC;
    }

    public String getEnergyC() {
        return energyC;
    }

    public String getLeakCurrent() {
        return leakCurrent;
    }

    public String getVoltage() {
        return voltage;
    }

    public String getAmpere() {
        return ampere;
    }

    public String getPower() {
        return power;
    }

    public String getEnergy() {
        return energy;
    }

    public Integer getNowState() {
        return nowState;
    }

    public String getNowStateText() {
        return nowStateText;
    }

    public String getLightDeviceCount() {
        return lightDeviceCount;
    }

    public String getLightDeviceSucceedCount() {
        return lightDeviceSucceedCount;
    }

    public String getIsRecordSyncText() {
        return isRecordSyncText;
    }

    public String getRecordSyncTime() {
        return recordSyncTime;
    }

    public String getAlarmDelayedTime() {
        return alarmDelayedTime;
    }
}
