package com.zz.lamp.bean;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegionExpandItem1 extends BaseExpandNode {

    private String title;

    private List<BaseNode> childNode;
    public String getTitle() {
        return title;
    }

    public RegionExpandItem1(String title, List<BaseNode> childNode) {
        this.title = title;
        this.childNode = childNode;
        setExpanded(true);
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}