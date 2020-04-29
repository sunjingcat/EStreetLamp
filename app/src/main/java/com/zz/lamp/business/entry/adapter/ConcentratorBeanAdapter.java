package com.zz.lamp.business.entry.adapter;



import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ConcentratorBeanAdapter extends BaseQuickAdapter<ConcentratorBean, BaseViewHolder> {

    public ConcentratorBeanAdapter(@LayoutRes int layoutResId, @Nullable List<ConcentratorBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final ConcentratorBean item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_entry_name,item.getTerminalName());
        holper.setText(R.id.item_entry_num,item.getTerminalAddr());

    }

}