package com.zz.lamp.business.control.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.base.ConcentratorBean;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlJzqAdapter extends BaseQuickAdapter<ConcentratorBean, BaseViewHolder> {

    public ControlJzqAdapter(@LayoutRes int layoutResId, @Nullable List<ConcentratorBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final ConcentratorBean item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
//        holper.setText(R.id.item_approval_title,item.getId());
//        holper.setText(R.id.item_approval_content,item.getId());
//        holper.setText(R.id.item_approval_state,item.getId());
//        holper.setText(R.id.item_approval_refuse,item.getId());
//        holper.setText(R.id.item_approval_time,TimeUtils.getTime(item.getId(),TimeUtils.DEFAULT_DATE_FORMAT));


    }

}