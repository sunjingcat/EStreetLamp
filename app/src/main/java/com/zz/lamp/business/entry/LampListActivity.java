package com.zz.lamp.business.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampPresenter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LampListActivity extends MyBaseActivity<Contract.IsetLampPresenter> implements Contract.IGetLampView {
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
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent(List<LightDevice> list) {

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


    @OnClick({R.id.entry_line, R.id.entry_lamp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_line:
                startActivity(new Intent(this,LineActivity.class).putExtra("terminalId",terminalId));
                break;
            case R.id.entry_lamp:
                startActivity(new Intent(this,EntryLampActivity.class).putExtra("terminalId",terminalId));
                break;
        }
    }
}
