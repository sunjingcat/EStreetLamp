package com.zz.lamp.business.entry.adapter;


import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.RegionExpandItem1;
import com.zz.lamp.bean.RegionExpandItem2;
import com.zz.lamp.bean.RegionExpandItem3;
import com.zz.lamp.business.entry.provider.FourthNodeProvider;
import com.zz.lamp.business.entry.provider.RootNodeProvider;
import com.zz.lamp.business.entry.provider.SecondNodeProvider;
import com.zz.lamp.business.entry.provider.ThirdNodeProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class RegionAdapter extends BaseNodeAdapter {
    public RegionAdapter(OnProviderOnClick onProviderOnClick) {
        super();
        addNodeProvider(new RootNodeProvider(onProviderOnClick));
        addNodeProvider(new SecondNodeProvider(onProviderOnClick));
        addNodeProvider(new ThirdNodeProvider(onProviderOnClick));
        addNodeProvider(new FourthNodeProvider(onProviderOnClick));

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
        }else if (node instanceof RegionExpandItem3) {
            return 3;
        }
        return -1;
    }
    public interface OnProviderOnClick{
        void onItemOnclick(BaseNode node,int type);
    }
}