package com.zz.lamp.net;

import com.zz.lib.commonlib.model.GsonModel;

/**
 * Created by admin on 2018/5/4.
 */

public abstract class CompactModel extends GsonModel {
    private boolean success;

    protected String message;
    private Integer is_auth;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(Integer is_auth) {
        this.is_auth = is_auth;
    }

    public boolean isSuccess() {
       return success;
    }

    public boolean isOutdate() {
        if (is_auth != null&&is_auth == 0) return true;
        return false;
    }


}
