package com.zz.lamp.business.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.business.alarm.adapter.ImageDeleteItemAdapter;
import com.zz.lamp.business.alarm.mvp.Contract;
import com.zz.lamp.business.alarm.mvp.presenter.AlarmAddPresenter;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AlarmDetailActivity extends  MyBaseActivity<Contract.IsetAlarmAddPresenter> implements Contract.IGetAlarmAddView {
    ArrayList<String> imagesAnnex = new ArrayList<>();
    ImageDeleteItemAdapter adapterAnnex;
    @BindView(R.id.rv_images_annex)
    RecyclerView rvImagesAnnex;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg)
    LinearLayout bg;

    @Override
    protected int getContentView() {
        return R.layout.activity_alarm_detail;
    }

    @Override
    public AlarmAddPresenter initPresenter() {
        return new AlarmAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImagesAnnex.setLayoutManager(new GridLayoutManager(this, 3));
        adapterAnnex = new ImageDeleteItemAdapter(this, imagesAnnex);
        rvImagesAnnex.setAdapter(adapterAnnex);
        adapterAnnex.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {

                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(4) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(imagesAnnex) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AlarmDetailActivity.this, 1102); // 打开相册

            }

            @Override
            public void onclickDelete(View v, int option) {
                imagesAnnex.remove(option);
                adapterAnnex.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == 1102&&data!=null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if (images.size() > 0) {
                this.imagesAnnex.clear();
            }
            this.imagesAnnex.addAll(images);
//
//            upload(images);

            adapterAnnex.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
    }

    @Override
    public void showResult() {

    }

}
