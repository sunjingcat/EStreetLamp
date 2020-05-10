package com.zz.lamp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class AlarmBean implements MultiItemEntity {
    private int itemType;

    private String searchValue;// null,
    private String   createBy;// ,
    private String   createTime;// 2020-05-07 11;//10;//04,
    private String  updateBy;// ,
    private String  updateTime;// null,
    private String    remark;// null,
    private String    deptId;// 117,
    private String    id;// 101,
    private String    serialNumber;// 0010110705209C0C0000,
    private String   terminalAddr;// 20042001,
    private String   terminalName;// 20042001,
    private String   devicecCode;// 3,
    private String   deviceName;// 3,
    private Integer   alarmType;// 2,
    private String  alarmValue;// 0,
    private Integer   alarmStatus;// 1,
    private String    alarmDeviceType;// 1,
    private String   lightDeviceAddr;// 202004200047,
    private String   handleDescription;// null,
    private List<String> handleFile;// null,
    private String  description;// 位于202004200047的灯控器发生辅灯损坏故障.
    private String  alarmDegree;// 位于202004200047的灯控器发生辅灯损坏故障.
    private String  alarmDegreeType;// 位于202004200047的灯控器发生辅灯损坏故障.
    private String  alarmDegreeColor;// 位于202004200047的灯控器发生辅灯损坏故障.

    public String getAlarmDegreeColor() {
        return alarmDegreeColor;
    }

    public String getAlarmDegree() {
        return alarmDegree;
    }

    public String getAlarmDegreeType() {
        return alarmDegreeType;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public AlarmBean(int itemType, String createTime) {
        this.itemType = itemType;
        this.createTime = createTime;
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

    public String getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getTerminalAddr() {
        return terminalAddr;
    }

    public String getDevicecCode() {
        return devicecCode;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public String getAlarmValue() {
        return alarmValue;
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public String getAlarmDeviceType() {
        return alarmDeviceType;
    }

    public String getLightDeviceAddr() {
        return lightDeviceAddr;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public List<String> getHandleFile() {
        return handleFile;
    }

    public String getDescription() {
        return description;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
