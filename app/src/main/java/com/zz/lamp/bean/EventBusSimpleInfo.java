package com.zz.lamp.bean;


import com.zz.lamp.net.CompactModel;

/**
 * Created by Administrator on 2017/4/24.
 * 适用于一些简单的数据 ，只有 data的数据
 */

public class EventBusSimpleInfo extends CompactModel {

    public String message;
    public String stringData;
    public int intData;
    public boolean booleanData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public int getIntData() {
        return intData;
    }

    public void setIntData(int intData) {
        this.intData = intData;
    }

    public boolean isBooleanData() {
        return booleanData;
    }

    public void setBooleanData(boolean booleanData) {
        this.booleanData = booleanData;
    }


}
