package com.zz.lamp.business.control.adapter;


import android.widget.CheckBox;

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

        CheckBox checkBox = (CheckBox) holper.getView(R.id.item_control_check);
        checkBox.setEnabled(false);
        checkBox.setChecked(item.isCheck());
        holper.setText(R.id.item_control_title,item.getDeviceName());
        holper.setText(R.id.item_control_state,item.getStatus()==0?"关灯":"开灯");

    }

}