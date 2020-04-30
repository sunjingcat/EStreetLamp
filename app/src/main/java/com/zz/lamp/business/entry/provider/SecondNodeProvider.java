package com.zz.lamp.business.entry.provider;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.RegionExpandItem1;
import com.zz.lamp.business.entry.adapter.RegionAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SecondNodeProvider extends BaseNodeProvider implements Serializable {
    @Override
    public int getItemViewType() {
        return 1;
    }
    RegionAdapter.OnProviderOnClick onProviderOnClick;
    public SecondNodeProvider(RegionAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }
    @Override
    public int getLayoutId() {
        return R.layout.item_region_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        baseViewHolder.setText(R.id.title,((RegionExpandItem1)baseNode).getAreaName());
        baseViewHolder.setImageResource(R.id.image_fold,((BaseExpandNode)baseNode).isExpanded()?R.drawable.image_down:R.drawable.image_right);
        baseViewHolder.getView(R.id.image_fold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().expandOrCollapse(baseViewHolder.getAdapterPosition());
            }
        });
        baseViewHolder.getView(R.id.region_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode,0);
            }
        });
        baseViewHolder.getView(R.id.region_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode,1);
            }
        });

    }
    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
