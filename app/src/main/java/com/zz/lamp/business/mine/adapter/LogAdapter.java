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
        holper.setText(R.id.item_title, item.getAreaName()+"  "+item.getTerminalName()+"  "+item.getTerminalAddr());
        holper.setText(R.id.item_content, "操作："+item.getBusinessTypeText()+" "+item.getReportTypeText());
        holper.setText(R.id.item_operName, "操作人："+item.getOperName());
        holper.setText(R.id.item_time, "时间:"+item.getOperTime());
        holper.setText(R.id.item_line, "支路状态："+item.getLineStateText());
        holper.setText(R.id.item_num, "流水号："+item.getSerialNumber());
    }

}