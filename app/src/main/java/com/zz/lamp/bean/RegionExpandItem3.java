package com.zz.lamp.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegionExpandItem3 extends BaseNode {
    private String id;// 1,
    private int orderNum;// 1,
    private String areaPid;// null,
    private String userId;// null,
    private String areaName;// 中智施维,
    private String areaLng;// 117.7947719493379,
    private String areaLat;// 39.164443269461685

    public String getId() {
        return id;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public String getAreaPid() {
        return areaPid;
    }

    public String getUserId() {
        return userId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAreaLng() {
        return areaLng;
    }

    public String getAreaLat() {
        return areaLat;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
