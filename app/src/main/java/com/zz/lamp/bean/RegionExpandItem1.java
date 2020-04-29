package com.zz.lamp.bean;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RegionExpandItem1 extends BaseExpandNode {

    private String  id;// 1,
    private int  orderNum;// 1,
    private String   areaPid;// null,
    private String   userId;// null,
    private String    areaName;// 中智施维,
    private String     areaLng;// 117.7947719493379,
    private String    areaLat;// 39.164443269461685
    private List<BaseNode> childrens = new ArrayList<>();


    public RegionExpandItem1(List<BaseNode> childrens) {

        setExpanded(true);
    }

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

    public List<BaseNode> getChildrens() {
        return childrens;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childrens;
    }
}
