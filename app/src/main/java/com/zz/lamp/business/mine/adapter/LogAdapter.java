package com.zz.lamp.business.mine.adapter;


import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.OperLog;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class LogAdapter extends BaseQuickAdapter<OperLog, BaseViewHolder> {
    public LogAdapter(@LayoutRes int layoutResId, @Nullable List<OperLog> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holper, final OperLog item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_title, item.getTitle());
        holper.setText(R.id.item_content, "操作："+item.getBusinessTypeText());
        holper.setText(R.id.item_operName, "操作人："+item.getOperName());
    }

}