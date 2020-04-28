package com.zz.lamp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class AlarmBean implements MultiItemEntity {
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
