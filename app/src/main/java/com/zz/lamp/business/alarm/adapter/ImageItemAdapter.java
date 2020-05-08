package com.zz.lamp.business.alarm.adapter;

import android.view.View;
import android.widget.ImageView;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.lamp.R;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ImageItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageItemAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final String item) {
        GlideUtils.loadImage(getContext(), CacheUtility.getURL() +item, (ImageView) holder.getView(R.id.image));

        holder.getView(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePreview.preview(getContext(), item);
            }
        });
    }
}
