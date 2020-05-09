package com.zz.lamp.business.alarm.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.AlarmBean;

import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class AlarmAdapter extends BaseMultiItemQuickAdapter<AlarmBean, BaseViewHolder> {

    public AlarmAdapter(@Nullable List<AlarmBean> data) {
        super( data);
        addItemType(1, R.layout.item_alarm_time);
        addItemType(2, R.layout.item_alarm_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AlarmBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                helper.setText(R.id.item_time, item.getCreateTime());
                break;
            case 2:
                helper.setText(R.id.item_alarm_name, item.getTerminalName()+""+item.getDeviceName());
                helper.setText(R.id.item_alarm_content, item.getDescription()+"");
                helper.setText(R.id.item_alarm_state, item.getAlarmStatus()+"");
                break;
        }
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
//        holper.setText(R.id.item_approval_title,item.getId());
//        holper.setText(R.id.item_approval_content,item.getId());
//        holper.setText(R.id.item_approval_state,item.getId());
//        holper.setText(R.id.item_approval_refuse,item.getId());
//        holper.setText(R.id.item_approval_time,TimeUtils.getTime(item.getId(),TimeUtils.DEFAULT_DATE_FORMAT));


    }

}