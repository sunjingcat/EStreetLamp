package com.zz.lamp.business.entry.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LineBean;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by ASUS on 2018/10/10.
 */

public class LineAdapter extends BaseQuickAdapter<LineBean, BaseViewHolder> {
    public void setOnClickListner(OnClickListener onClickListner) {
        this.onClickListner = onClickListner;
    }
    int type;
    OnClickListener onClickListner;
    public LineAdapter(@LayoutRes int layoutResId, @Nullable List<LineBean> data,int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holper, final LineBean item) {
//        GlideUtils.loadImage(mContext, item, (ImageView) holper.getView(R.id.item_approval_icon));
        holper.setText(R.id.item_title, item.getLineName());
        if (type == 1) {
            holper.getView(R.id.item_edit).setVisibility(View.VISIBLE);
            holper.getView(R.id.item_delete).setVisibility(View.VISIBLE);
        }else {
            holper.getView(R.id.item_edit).setVisibility(View.GONE);
            holper.getView(R.id.item_delete).setVisibility(View.GONE);
        }
        holper.getView(R.id.item_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListner.onEditClick(holper.getAdapterPosition());
            }
        });
        holper.getView(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListner.onDeleteClick(holper.getAdapterPosition());
            }
        });
    }

   public interface OnClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

}