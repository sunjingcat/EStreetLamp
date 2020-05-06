package com.zz.lamp.business.entry.adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LightDevice;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class LightDetailAdapter extends BaseQuickAdapter<LightDetailBean, BaseViewHolder> {

    public LightDetailAdapter(@LayoutRes int layoutResId, @Nullable List<LightDetailBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final LightDetailBean item) {
        holper.setText(R.id.item_title,item.getTitle());
        holper.setText(R.id.item_content,item.getContent());

    }

}