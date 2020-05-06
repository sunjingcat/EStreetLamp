package com.zz.lamp.bean;

public class RealTimeCtrlGroup {
    private int id;// 3,
    private String      terminalId;// 7,
    private String     baseGroupName;// test,
    private String       baseGroupCode;// 1,
    private int      luminance;// 3
    boolean check ;

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getBaseGroupName() {
        return baseGroupName;
    }

    public String getBaseGroupCode() {
        return baseGroupCode;
    }

    public int getLuminance() {
        return luminance;
    }

    public boolean isCheck() {
        return check;
    }
}
