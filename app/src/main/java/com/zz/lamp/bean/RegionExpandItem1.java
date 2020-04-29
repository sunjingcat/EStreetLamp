package com.zz.lamp.bean;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegionExpandItem1 extends BaseExpandNode {

    private String  searchValue;// null,
    private String      createBy;// cjw,
    private String    createTime;// 2020-04-03 10;//30;//49,
    private String    updateBy;// ,
    private String  updateTime;// null,
    private String   remark;// null,
    private String    deptId;// 110,
    private String   params;// {},
    private String   parentName;// null,
    private String  parentId;// null,
    private String   orderNum;// 1,
    private String    ancestors;// null,
    private String  id;// 1,
    private String   areaPid;// null,
    private String   userId;// null,
    private String    areaName;// 中智施维,
    private String     areaLng;// 117.7947719493379,
    private String    areaLat;// 39.164443269461685

    private List<BaseNode> childNode;


    public RegionExpandItem1(List<BaseNode> childNode) {

        setExpanded(true);
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getParams() {
        return params;
    }

    public String getParentName() {
        return parentName;
    }

    public String getParentId() {
        return parentId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getAncestors() {
        return ancestors;
    }

    public String getId() {
        return id;
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
        return childNode;
    }
}
