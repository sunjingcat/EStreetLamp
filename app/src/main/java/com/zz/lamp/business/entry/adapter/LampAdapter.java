package com.zz.lamp.business.entry.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LineBean;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class LampAdapter extends BaseQuickAdapter<LightDevice, BaseViewHolder> {

    public LampAdapter(@LayoutRes int layoutResId, @Nullable List<LightDevice> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final LightDevice item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_entry_name,item.getDeviceName());
        holper.setText(R.id.item_entry_num,item.getDevicecAddr());

    }

}