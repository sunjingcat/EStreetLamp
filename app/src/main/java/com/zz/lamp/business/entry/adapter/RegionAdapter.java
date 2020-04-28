package com.zz.lamp.business.entry.adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.base.ConcentratorBean;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.RegionExpandItem1;
import com.zz.lamp.bean.RegionExpandItem2;
import com.zz.lamp.business.entry.provider.RootNodeProvider;
import com.zz.lamp.business.entry.provider.SecondNodeProvider;
import com.zz.lamp.business.entry.provider.ThirdNodeProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class RegionAdapter extends BaseNodeAdapter {
    public RegionAdapter() {
        super();
        addNodeProvider(new RootNodeProvider());
        addNodeProvider(new SecondNodeProvider());
        addNodeProvider(new ThirdNodeProvider());

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int position) {
        BaseNode node = list.get(position);
        if (node instanceof RegionExpandItem) {
            return 0;
        } else if (node instanceof RegionExpandItem1) {
            return 1;
        } else if (node instanceof RegionExpandItem2) {
            return 2;
        }
        return -1;
    }
}