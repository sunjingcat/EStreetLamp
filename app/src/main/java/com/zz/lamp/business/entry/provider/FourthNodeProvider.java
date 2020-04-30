package com.zz.lamp.business.entry.provider;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.RegionExpandItem3;
import com.zz.lamp.business.entry.adapter.RegionAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class FourthNodeProvider extends BaseNodeProvider implements Serializable {
    RegionAdapter.OnProviderOnClick onProviderOnClick;

    public FourthNodeProvider(RegionAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

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
        baseViewHolder.setText(R.id.title, ((RegionExpandItem3) baseNode).getAreaName());
        baseViewHolder.getView(R.id.image_fold).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.region_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode, 0);
            }
        });
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
//        getAdapter().expandOrCollapse(position);
    }
}
