package com.zz.lamp.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RegionExpandItem2 extends BaseExpandNode {
    private String id;// 1,
    private int orderNum;// 1,
    private String areaPid;// null,
    private String userId;// null,
    private String areaName;// 中智施维,
    private String areaLng;// 117.7947719493379,
    private String areaLat;// 39.164443269461685
    private List<RegionExpandItem3> childrens = new ArrayList<>();

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

    public List<RegionExpandItem3> getChildrens() {
        return childrens;
    }

    public RegionExpandItem2() {
        setExpanded(true);
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        if (childrens == null) return null;
        List<BaseNode> childs = new ArrayList<>();
        for (BaseNode node : childrens) {
            childs.add(node);
        }
        return childs;
    }
}
