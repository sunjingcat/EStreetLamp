package com.zz.lamp.business.entry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.alarm.adapter.ImageItemAdapter;
import com.zz.lamp.business.entry.adapter.LampAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampPresenter;
import com.zz.lamp.utils.BASE64;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 集中器详情灯控器列表
 */
public class LampListActivity extends MyBaseActivity<Contract.IsetLampPresenter> implements Contract.IGetLampView, OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.areaName)
    TextView areaName;
    @BindView(R.id.terminalAddr)
    TextView terminalAddr;
    @BindView(R.id.terminalName)
    TextView terminalName;
    @BindView(R.id.loopCount)
    TextView loopCount;
    @BindView(R.id.lineCount)
    TextView lineCount;
    @BindView(R.id.loopTransformerRatio)
    TextView loopTransformerRatio;
    @BindView(R.id.lineTransformerRatio)
    TextView lineTransformerRatio;
    @BindView(R.id.alarmDelayedTime)
    TextView alarmDelayedTime;
    @BindView(R.id.relayOnDelayedTime)
    TextView relayOnDelayedTime;
    @BindView(R.id.terminalLat)
    TextView terminalLat;
    @BindView(R.id.ll_gone)
    LinearLayout llGone;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    String terminalId;
    List<LightDevice> mlist = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 20;
    LampAdapter adapter;
    boolean isGone = false;
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.tv_show)
    TextView tvShow;
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter imageItemAdapter;
    @BindView(R.id.rv_images_annex)
    RecyclerView rvImagesAnnex;
    @BindView(R.id.terminalSync)
    TextView terminalSync;
    @BindView(R.id.terminalSyncTime)
    TextView terminalSyncTime;

    @Override
    protected int getContentView() {
        return R.layout.activity_lamp_list;
    }

    @Override
    public LampPresenter initPresenter() {
        return new LampPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new LampAdapter(R.layout.item_entry_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnLoadMoreListener(this);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivityForResult(new Intent(LampListActivity.this, LightDetailActivity.class).putExtra("lightId", mlist.get(position).getId()),3001);
            }
        });
        rvImagesAnnex.setLayoutManager(new GridLayoutManager(this, 3));
        imageItemAdapter = new ImageItemAdapter(R.layout.item_image, images);
        rvImagesAnnex.setAdapter(imageItemAdapter);
        pageNum = 1;
        mPresenter.getTerminalDetail(terminalId);
        mPresenter.getImage("terminal", terminalId);
        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent(List<LightDevice> list) {
        if (list == null) return;
        if (pageNum == 1) {
            mlist.clear();
        }
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        if (mlist.size() > 0) {
            llNull.setVisibility(View.GONE);
        } else {
            llNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showImage(List<ImageBack> list) {

        if (list == null) return;
        showLoading("");


        List<String> showList = new ArrayList<>();
        for (ImageBack imageBack : list) {
            String bitmapName = "termial_"+imageBack.getId()+".jpg";
            String path = getCacheDir() + "/zhongzhi/light/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                showList.add(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                showList.add(s);
            }

        }
        images.clear();
        images.addAll(showList);

        imageItemAdapter.notifyDataSetChanged();
        dismissLoading();
    }

    @Override
    public void showTerminalDetail(ConcentratorBean concentratorBean) {
        if (concentratorBean == null) return;
        LogUtils.v(concentratorBean.toString());
        areaName.setText(concentratorBean.getAreaName() + "");
        terminalAddr.setText(concentratorBean.getTerminalAddr() + "");
        terminalName.setText(concentratorBean.getTerminalName() + "");
        loopCount.setText(concentratorBean.getLoopCount() + "");
        lineCount.setText(concentratorBean.getLineCount() + "");
        loopTransformerRatio.setText(concentratorBean.getLoopTransformerRatio() + "");
        lineTransformerRatio.setText(concentratorBean.getLineTransformerRatio() + "");
        alarmDelayedTime.setText(concentratorBean.getAlarmDelayedTime() + "");
        relayOnDelayedTime.setText(concentratorBean.getRelayOnDelayedTime() + "");
        terminalSync.setText(concentratorBean.getIsRecordSyncText() + "");
        terminalSyncTime.setText(concentratorBean.getRecordSyncTime() + "");
//        terminalLat.setText(concentratorBean.getTerminalLat() + "," + concentratorBean.getTerminalLng());


    }


    @OnClick({R.id.entry_line, R.id.entry_lamp, R.id.ll_show, R.id.toolbar_subtitle, R.id.dangan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, EntryJzqActivity.class).putExtra("terminalId", terminalId),3001);
                break;
            case R.id.entry_line:
                startActivityForResult(new Intent(this, LineActivity.class).putExtra("terminalId", terminalId).putExtra("shouldBack", 1),3001);
                break;
            case R.id.ll_show:
                if (llGone.getVisibility() == View.VISIBLE) {
                    llGone.setVisibility(View.GONE);
                    ivShow.setImageResource(R.drawable.image_open);
                    tvShow.setText("打开");
                } else {
                    llGone.setVisibility(View.VISIBLE);
                    ivShow.setImageResource(R.drawable.image_close_tab);
                    tvShow.setText("收起");
                }
                break;
            case R.id.entry_lamp:

                startActivityForResult(new Intent(this, EntryLampActivity.class).putExtra("terminalId", terminalId),3001);

                break;
            case R.id.dangan:
                mPresenter.lightDbSet(terminalId);
                break;
        }
    }

    void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
//        map.put("searchValue",pageSize);
        mPresenter.getLampList(map, terminalId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            setResult(RESULT_OK);
            finish();
        }
        if (requestCode==3001){
            pageNum = 1;
            mPresenter.getTerminalDetail(terminalId);
            getData();
        }
    }
}
