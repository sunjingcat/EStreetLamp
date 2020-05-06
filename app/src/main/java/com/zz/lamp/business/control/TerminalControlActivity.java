package com.zz.lamp.business.control;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.adapter.ControlCameraAdapter;
import com.zz.lamp.business.control.adapter.ControlLineAdapter;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.TerminalControlPresenter;
import com.zz.lamp.business.login.LoginActivity;
import com.zz.lamp.business.mine.MineActivity;
import com.zz.lamp.net.OutDateEvent;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.MyTimeTask;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TerminalControlActivity extends MyBaseActivity<Contract.IsetTerminalControlPresenter> implements Contract.IGetTerminalControlView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.control_group)
    RelativeLayout controlGroup;
    @BindView(R.id.control_lamp)
    RelativeLayout controlLamp;
    @BindView(R.id.item_title)
    TextView itemTitle;
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
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    String terminalId;
    List<LineBean> mlist = new ArrayList<>();
    int pageNum = 1;
    int pageSize = 20;
    ControlLineAdapter adapter;
    @BindView(R.id.control_all)
    CheckBox controlAll;

    @Override
    protected int getContentView() {
        return R.layout.activity_terminal_control;
    }

    @Override
    public TerminalControlPresenter initPresenter() {
        return new TerminalControlPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
        mPresenter.getTerminalDetail(terminalId);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new ControlLineAdapter(R.layout.item_line_control, mlist);
        rv.setAdapter(adapter);
        mPresenter.getLineList(terminalId);
        refreshLayout.setEnableLoadMore(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mlist.get(position).setCheck(!mlist.get(position).isCheck());
                adapter.notifyDataSetChanged();
            }
        });
        controlAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (LineBean lineBean : mlist) {
                    lineBean.setCheck(isChecked);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showDetail(ConcentratorBean concentratorBean) {
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

    @Override
    public void showLineList(List<LineBean> list) {
        if (list == null) return;
        mlist.clear();
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        if (mlist.size()>0){
            llNull.setVisibility(View.GONE);
        }else {
            llNull.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showIntent() {

    }
    private CustomDialog customDialog;
    CustomDialog.Builder builder;
    @OnClick({R.id.control_group, R.id.control_lamp, R.id.ll_show, R.id.control_open, R.id.control_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_group:
                break;
            case R.id.control_lamp:
                break;
            case R.id.control_close:
                showTimeDialog(0);
                break;
            case R.id.control_open:
                showTimeDialog(-1);
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
        }
    }
    void showTimeDialog(int opt){
        stopTimer();
        builder = new CustomDialog.Builder(TerminalControlActivity.this)
                .setTitle("提示")
                .setMessage(opt==0?"确定拉闸？":"确定合闸？")
                .setCancelOutSide(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopTimer();
                    }
                })
                .setPositiveButton("确定", new CustomDialog.Builder.OnPClickListener() {
                    @Override
                    public void onClick(CustomDialog v, String msg) {
                        postData(opt);
                    }
                });
        setTimer();
        customDialog = builder.create();
        customDialog.show();
    }

    void postData(int opt) {
        List<String> list = new ArrayList<>();
        for (LineBean lineBean : mlist) {
            if (lineBean.isCheck()) {
                list.add(lineBean.getId());
            }
        }
        String[] arr = (String[]) list.toArray(new String[list.size()]);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", arr);
        params.put("opt", opt);
        mPresenter.realTimeCtrlLine(params);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        stopTimer();
    }
    private   int TIMER = 5;
    private MyTimeTask task;
    private void setTimer(){
        task =new MyTimeTask(1000, new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
                //或者发广播，启动服务都是可以的
            }
        });
       task.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    TIMER--;
                    builder.setPositiveButton(TIMER+"");
                    if (TIMER==0){
                        stopTimer();
                        builder.setPositiveButton("确定");

                    }
                    break;
                default:
                    break;
            }
        }
    };
    private void stopTimer(){
        if (task!=null) {
            task.stop();
        }
        TIMER = 5;
    }
}
