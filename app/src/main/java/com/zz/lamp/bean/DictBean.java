package com.zz.lamp.bean;

import java.io.Serializable;

public class DictBean implements Serializable {
    private String cssClass;    //string
    private Integer dictCode;//'	integer
    private String dictLabel;//	string
    private Integer dictSort;//integer
    private String dictType;//	string
    private String dictValue;//	string
    private String isDefault;//	string
    private String listClass;//	string
    private String status;//string

    public String getCssClass() {
        return cssClass;
    }

    public Integer getDictCode() {
        return dictCode;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public String getDictType() {
        return dictType;
    }

    public String getDictValue() {
        return dictValue;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public String getListClass() {
        return listClass;
    }

    public String getStatus() {
        return status;
    }
}
