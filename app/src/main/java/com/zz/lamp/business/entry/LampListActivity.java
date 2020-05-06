package com.zz.lamp.business.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.entry.adapter.LampAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampPresenter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        mPresenter.getTerminalDetail(terminalId);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new LampAdapter(R.layout.item_entry_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnLoadMoreListener(this);
        getData();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(LampListActivity.this, LightDetailActivity.class).putExtra("lightId", mlist.get(position).getId()));
            }
        });
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
        terminalLat.setText(concentratorBean.getTerminalLat() + "," + concentratorBean.getTerminalLng());
    }


    @OnClick({R.id.entry_line, R.id.entry_lamp, R.id.ll_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_line:
                startActivity(new Intent(this, LineActivity.class).putExtra("terminalId", terminalId));
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
                if (mlist.size() > 0) {
                    LightDevice lightDevice = mlist.get(mlist.size() - 1);
                    startActivity(new Intent(this, EntryLampActivity.class).putExtra("terminalId", terminalId).putExtra("device", lightDevice));
                } else {
                    startActivity(new Intent(this, EntryLampActivity.class).putExtra("terminalId", terminalId));
                }
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
}
