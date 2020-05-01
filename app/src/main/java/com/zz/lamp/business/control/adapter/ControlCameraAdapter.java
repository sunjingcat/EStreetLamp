package com.zz.lamp.business.control.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.RealTimeCtrlTerminal;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class ControlCameraAdapter extends BaseQuickAdapter<CameraBean, BaseViewHolder> {

    public ControlCameraAdapter(@LayoutRes int layoutResId, @Nullable List<CameraBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final CameraBean item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_title,item.getDeviceName());


    }

}