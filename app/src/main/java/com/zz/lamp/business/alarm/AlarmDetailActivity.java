package com.zz.lamp.business.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.TestPost;
import com.zz.lamp.business.alarm.adapter.ImageDeleteItemAdapter;
import com.zz.lamp.business.alarm.adapter.ImageItemAdapter;
import com.zz.lamp.business.alarm.mvp.Contract;
import com.zz.lamp.business.alarm.mvp.presenter.AlarmAddPresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.BASE64;
import com.zz.lamp.utils.BitmapUtil;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlarmDetailActivity extends MyBaseActivity<Contract.IsetAlarmAddPresenter> implements Contract.IGetAlarmAddView {
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.rv_images_annex)
    RecyclerView rvImagesAnnex;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.alarm_name)
    TextView alarmName;
    @BindView(R.id.alarm_des)
    TextView alarmDes;
    @BindView(R.id.alarm_time)
    TextView alarmTime;
    @BindView(R.id.alarm_address)
    TextView alarm_address;
    @BindView(R.id.alarm_content)
    TextView alarmContent;
    @BindView(R.id.tv_status)
    TextView tvStatus;

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
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        rvImagesAnnex.setAdapter(adapter);
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        mPresenter.getData(map);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showResult() {

    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        setResult(RESULT_OK);
        finish();
    }


    @Override
    public void showDetailResult(AlarmBean alarmBean) {
        alarmName.setText(alarmBean.getTerminalName()+""+alarmBean.getDeviceName());
        alarmDes.setText(alarmBean.getDescription() + "");
        alarmTime.setText(alarmBean.getCreateTime() + "");
        alarmContent.setText(alarmBean.getHandleDescription() + "");
        tvStatus.setText(alarmBean.getAlarmStatus()==0?"忽略":"已处理");
        mPresenter.getImage("alarm", alarmBean.getId());
    }

    @Override
    public void showImage(List<String> list) {
        if (list == null) return;
        images.clear();

        images.addAll(list);

        adapter.notifyDataSetChanged();
    }

}
