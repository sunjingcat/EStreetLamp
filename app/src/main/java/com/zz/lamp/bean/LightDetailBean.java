package com.zz.lamp.bean;

public class LightDetailBean {
    String title;
    String content;

    public LightDetailBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
