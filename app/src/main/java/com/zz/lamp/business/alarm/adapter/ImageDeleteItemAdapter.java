package com.zz.lamp.business.alarm.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.zz.lamp.R;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.utils.BASE64;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lamp.utils.ImagePreview;
import com.zz.lib.commonlib.utils.PermissionUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ImageDeleteItemAdapter extends RecyclerView.Adapter<ImageDeleteItemAdapter.MyViewHolder> {
    public Onclick onclick;
    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
    public interface  Onclick{
        void onclickAdd(View v, int option);
        void onclickDelete(View v, int option);
    }
    private List<ImageBack> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    public ImageDeleteItemAdapter(Context context, List<ImageBack> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater=LayoutInflater. from(mContext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout. item_image_delete,null);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
//
        if (holder.getAdapterPosition()==mDatas.size()){
            holder.imageView.setImageResource(R.drawable.image_add);
            holder.delete.setVisibility(View.GONE);
        }else {
            GlideUtils.loadImage(mContext, mDatas.get(i).getPath(),  holder.imageView);
            holder.delete.setVisibility(View.VISIBLE);
        }
        holder. delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onclickDelete(v,i);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition()==mDatas.size()) {
                    onclick.onclickAdd(v, holder.getAdapterPosition());
                }else {
                    PermissionUtils.getInstance().checkPermission((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                        @Override
                        public void onGranted() {
                            String str = mDatas.get(i).getPath();
                            ImagePreview.preview(mContext, str);
                        }

                        @Override
                        public void onDenied() {

                        }
                    });

                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if (mDatas.size()<9){
            return mDatas.size()+1;
        }else {
            return mDatas.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView delete;
        public MyViewHolder(View view) {
            super(view);
            imageView= view.findViewById(R.id. image);
            delete= view.findViewById(R.id. delete);
        }
    }
}
