package com.zz.lamp.business.control.adapter;


import android.graphics.Color;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RealTimeCtrlGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlGroupAdapter extends BaseQuickAdapter<RealTimeCtrlGroup, BaseViewHolder> {

    public ControlGroupAdapter(@LayoutRes int layoutResId, @Nullable List<RealTimeCtrlGroup> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final RealTimeCtrlGroup item) {
//        亮度值（0-关灯，100-开灯，2-99调光）
        CheckBox checkBox = (CheckBox) holper.getView(R.id.item_control_check);
        checkBox.setEnabled(false);
        checkBox.setChecked(item.isCheck());
        holper.setText(R.id.item_control_title,item.getBaseGroupName());
        if (item.getLuminance()==0){
            holper.setText(R.id.item_control_state,"关灯");
            holper.setTextColor(R.id.item_control_state,Color.parseColor("#2EAE73") );
        }else if(item.getLuminance()==100){
            holper.setText(R.id.item_control_state,"开灯");
            holper.setTextColor(R.id.item_control_state,Color.parseColor("#E84444") );
        }else {
            holper.setText(R.id.item_control_state,item.getLuminance()+"%");
        }

//        holper.setTextColor(R.id.item_control_state,item.getStatus()==0? Color.parseColor("#2EAE73") :Color.parseColor("#E84444"));

    }

}