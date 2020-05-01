package com.zz.lamp.business.control.adapter;


import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.RealTimeCtrlTerminal;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlJzqAdapter extends BaseQuickAdapter<RealTimeCtrlTerminal, BaseViewHolder> {

    public ControlJzqAdapter(@LayoutRes int layoutResId, @Nullable List<RealTimeCtrlTerminal> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final RealTimeCtrlTerminal item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_control_name,item.getName());
        holper.setText(R.id.item_control_num,item.getAddr());
        holper.setText(R.id.item_control_state,(!TextUtils.isEmpty(item.getStatus())) &&item.getStatus().equals("1")?"开闸":"合闸");
        holper.setTextColor(R.id.item_control_state,(!TextUtils.isEmpty(item.getStatus())) &&item.getStatus().equals("1")?R.color.green_2e:R.color.red_e8);
        holper.setText(R.id.item_control_open,"开灯时间："+item.getLightOnTime());
        holper.setText(R.id.item_control_close,"关灯时间："+item.getLightOffTime());


    }

}