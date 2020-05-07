package com.zz.lamp.bean;

public class LightPost {
    Integer id;
    Integer type;

    public LightPost(Integer id, Integer type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }
}
