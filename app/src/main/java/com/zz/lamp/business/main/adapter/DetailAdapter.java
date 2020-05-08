package com.zz.lamp.business.main.adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LightDetailBean;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class DetailAdapter extends BaseQuickAdapter<LightDetailBean, BaseViewHolder> {

    public DetailAdapter(@LayoutRes int layoutResId, @Nullable List<LightDetailBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final LightDetailBean item) {
        holper.setText(R.id.item_title,item.getTitle());
        holper.setText(R.id.item_content,item.getContent());

    }

}