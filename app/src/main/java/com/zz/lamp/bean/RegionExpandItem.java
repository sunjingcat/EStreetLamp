package com.zz.lamp.bean;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RegionExpandItem extends BaseExpandNode {
    private String  id;// 1,
    private int  orderNum;// 1,
    private String   areaPid;// null,
    private String   userId;// null,
    private String    areaName;// 中智施维,
    private String     areaLng;// 117.7947719493379,
    private String    areaLat;// 39.164443269461685
    private List<RegionExpandItem1> childrens = new ArrayList<>();

    public RegionExpandItem(int orderNum, String areaPid) {
        this.orderNum = orderNum;
        this.areaPid = areaPid;
    }

    public RegionExpandItem() {
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

    public List<RegionExpandItem1> getChildrens() {
        return childrens;
    }

    @Override
    public String toString() {
        return "RegionExpandItem{" +
                "id='" + id + '\'' +
                ", orderNum=" + orderNum +
                ", areaPid='" + areaPid + '\'' +
                ", userId='" + userId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaLng='" + areaLng + '\'' +
                ", areaLat='" + areaLat + '\'' +
                ", childrens=" + childrens +
                '}';
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        List<BaseNode> childs = new ArrayList<>();
        if (childrens==null)return null;
        for (BaseNode node:childrens){
            childs.add(node);
        }
        return childs;
    }

}
