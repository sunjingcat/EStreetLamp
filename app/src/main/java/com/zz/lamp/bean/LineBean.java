package com.zz.lamp.bean;

public class LineBean {
    private Integer id;//integer	主键
    private Integer lineCode;//integer	线路编码
    private String lineName;//	string	线路名称
    private String terminalId;//integer	集中器终端id
    private boolean check = true;//integer	集中器终端id
    private int status;//integer	集中器终端id

    public int getStatus() {
        return status;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLineCode() {
        return lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public String getTerminalId() {
        return terminalId;
    }
}
