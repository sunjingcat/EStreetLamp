package com.zz.lamp.business.entry.provider;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.RegionExpandItem3;

import org.jetbrains.annotations.NotNull;

public class FourthNodeProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_region_fourth;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        baseViewHolder.setText(R.id.title,((RegionExpandItem3)baseNode).getAreaName());
    }
    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
//        getAdapter().expandOrCollapse(position);
    }
}
