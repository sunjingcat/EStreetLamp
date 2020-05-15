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
    int isEdit;
    public FourthNodeProvider(int isEdit,RegionAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
        this.isEdit=isEdit;
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
        if (isEdit == 1) {
//            baseViewHolder.getView(R.id.region_add).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.region_edit).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.region_delete).setVisibility(View.VISIBLE);
        } else {
//            baseViewHolder.getView(R.id.region_add).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.region_edit).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.region_delete).setVisibility(View.GONE);
        }

        baseViewHolder.getView(R.id.region_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode, 1);
            }
        });
        baseViewHolder.getView(R.id.region_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode, 2);
            }
        });
        baseViewHolder.getView(R.id.region_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(baseNode, 3);
            }
        });

    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (isEdit == 0) {
            onProviderOnClick.onItemOnclick(data, 0);
        }

    }
}
