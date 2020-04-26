package com.zz.lamp.net;

/**
 * Created by admin on 2018/4/26.
 */

public class RespModel<T> extends CompactModel {
    private T data;



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
