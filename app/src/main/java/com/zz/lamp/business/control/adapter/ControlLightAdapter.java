package com.zz.lamp.business.control.adapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LineBean;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlLightAdapter extends BaseQuickAdapter<LightDeviceConBean, BaseViewHolder> {

    public ControlLightAdapter(@LayoutRes int layoutResId, @Nullable List<LightDeviceConBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final LightDeviceConBean item) {

        ImageView imageView =  holper.getView(R.id.item_control_check);
        if (item.isCheck()){
            imageView.setImageResource(R.drawable.image_real_check);
        }else {
            imageView.setImageResource(R.drawable.image_real_uncheck);
        }
        holper.setText(R.id.item_control_title,item.getDeviceName()+(item.getDeviceMainAuxi()==1?"（主）":"（辅）"));
        holper.setText(R.id.item_control_state,item.getStatus()==0?"关灯":"开灯");

    }

}