package com.zz.lamp.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegionExpandItem2 extends BaseNode {
    private String title;

    public String getTitle() {
        return title;
    }

    public RegionExpandItem2(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
