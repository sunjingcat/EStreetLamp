package com.zz.lamp.net;


/**
 * Created by user on 2017-08-25.
 */

public class JsonT<T> extends CompactModel {
    private T data;

    private int count;
    private int page_count;
    private int page;
    private long last_time;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }


}
