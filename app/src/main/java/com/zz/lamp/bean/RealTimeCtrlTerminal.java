package com.zz.lamp.bean;

public class RealTimeCtrlTerminal {
    private String id;// 2,
    private String       deviceKind;// 1,
    private String       addr;// 20040801,
    private String      code;// null,
    private String      name;// 静心湖,
    private String      markerIconPath;// null,
    private int     status;// 1,
    private int     isOnline;// 1,
    private String    warnStatus;// 0,
    private Double    lng;// 116.7916727236518,
    private Double   lat;// 38.290273307174054,
    private String    lightOnTime;// 2020-05-01 19;//19;//00,
    private String    lightOffTime;// 2020-05-01 23;//59;//00,
    private String     otherContent;// null

    public int getIsOnline() {
        return isOnline;
    }

    public String getId() {
        return id;
    }

    public String getDeviceKind() {
        return deviceKind;
    }

    public String getAddr() {
        return addr;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMarkerIconPath() {
        return markerIconPath;
    }

    public int getStatus() {
        return status;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    public String getLightOnTime() {
        return lightOnTime;
    }

    public String getLightOffTime() {
        return lightOffTime;
    }

    public String getOtherContent() {
        return otherContent;
    }
}
