package com.zz.lamp.bean;

public class LightDeviceConBean {
   private String areaName;// null,
    private String       terminalId;// 7,
    private String      terminalName;// 展厅集中器,
    private Integer      id;// 9,
    private String     devicecCode;// 1,
    private String     deviceName;// 展厅-1,
    private String      devicecAddr;// 202004050004,
    private String       lightPoleCode;// 001,
    private Integer       deviceType;// 1,
    private String       voltage;// 228.4,
    private String       ampere;// 0.024,
    private String      power;// 0.0306,
    private String     energy;// 20.88,
    private int     status;// 1,
    private String    luminance;// null,
    private String     createTime;// 2020-05-06 09;//07;//23
    boolean check ;

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public Integer getId() {
        return id;
    }

    public String getDevicecCode() {
        return devicecCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDevicecAddr() {
        return devicecAddr;
    }

    public String getLightPoleCode() {
        return lightPoleCode;
    }

    public Integer getDeviceType() {
        return deviceType;
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

    public int getStatus() {
        return status;
    }

    public String getLuminance() {
        return luminance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public boolean isCheck() {
        return check;
    }
}
