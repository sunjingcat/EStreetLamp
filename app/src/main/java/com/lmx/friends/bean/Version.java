package com.lmx.friends.bean;

/**
 * Created by sunjing on 2018/6/6.
 */

public class Version {
    private String version_name;//1.000.002,
    private int version_code;//1,
    private int necessary;//1,
    private String download;//http;////www.topbtch.com/download,
    private String changes;//1.增加版本检测<br> 2.优化K线


    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public int getNecessary() {
        return necessary;
    }

    public void setNecessary(int necessary) {
        this.necessary = necessary;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
