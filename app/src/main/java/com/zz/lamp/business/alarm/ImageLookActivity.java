package com.zz.lamp.business.alarm;


import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.utils.BASE64;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageLookActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.intensify_image)
    ImageView intensify_image;

    @Override
    protected int getContentView() {
        return R.layout.activity_image_look;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        try {
            String url = getIntent().getStringExtra("url");
            GlideUtils.loadImage(this, url, intensify_image);

        }catch (Exception e){
            LogUtils.e(e.toString());
        }

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
