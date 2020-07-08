package com.zz.lamp.business.control.adapter;


import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.LineBean;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlLineAdapter extends BaseQuickAdapter<LineBean, BaseViewHolder> {

    public ControlLineAdapter(@LayoutRes int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final LineBean item) {

        ImageView imageView =  holper.getView(R.id.item_control_check);
        if (item.isCheck()){
            imageView.setImageResource(R.drawable.image_real_check);
        }else {
            imageView.setImageResource(R.drawable.image_real_uncheck);
        }
        holper.setText(R.id.item_control_title,item.getLineName());
        holper.setText(R.id.item_control_state,item.getStatus()==1?"开":"关");
        holper.setTextColor(R.id.item_control_state,item.getStatus()==1? Color.parseColor("#2EAE73") :Color.parseColor("#E84444"));
    }

}