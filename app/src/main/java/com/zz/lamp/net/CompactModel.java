package com.zz.lamp.net;

import com.zz.lib.commonlib.model.GsonModel;

/**
 * Created by admin on 2018/5/4.
 */

public abstract class CompactModel extends GsonModel {
    private String code;

    protected String msg;
    private Integer is_auth;

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public Integer getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(Integer is_auth) {
        this.is_auth = is_auth;
    }

    public boolean isSuccess() {
       return code.equals("200");
    }

    public boolean isOutdate() {
        if (is_auth != null&&is_auth == 0) return true;
        return false;
    }


}
