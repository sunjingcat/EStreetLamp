package com.zz.lib.core.ui.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.lib.core.R;


/**
 * Created by admin on 2018/2/9.
 */

public class CustomProgressDialog  extends ProgressDialog {
    private AnimationDrawable mAnimation;
    private ImageView mImageView;
    private String mLoadingTip;
    private TextView mLoadingTv;
    private int mResid;

    public CustomProgressDialog(Context context, String content, int theme, int id) {
        super(context, theme);
        this.mLoadingTip = content;
        this.mResid = id;
        setCanceledOnTouchOutside(false);// 设置不能点击对话框外边取消当前对话框
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mImageView.setBackgroundResource(mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();

            }
        });
        mLoadingTv.setText("数据加载中\n请耐心等待");
    }

    public void setContent(String str) {
//        mLoadingTv.setText("");
    }

    private void initView() {
        setContentView(R.layout.progress_loading_dialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }
}
