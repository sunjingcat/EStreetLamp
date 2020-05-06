package com.zz.lamp.business.entry.adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.baidu.mapapi.search.core.PoiInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.LocationInfo;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class SearchLocationAdapter extends BaseQuickAdapter<PoiInfo, BaseViewHolder> {

    public SearchLocationAdapter(@LayoutRes int layoutResId, @Nullable List<PoiInfo> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final PoiInfo item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_title,item.getAddress());

    }

}