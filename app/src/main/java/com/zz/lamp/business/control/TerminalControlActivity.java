package com.zz.lamp.business.control;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.adapter.ControlLineAdapter;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.TerminalControlPresenter;
import com.zz.lamp.business.main.adapter.DetailAdapter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.MyTimeTask;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ControlLineAdapter adapter;
    @BindView(R.id.control_all)
    CheckBox controlAll;
    ConcentratorBean concentratorBean;
    @BindView(R.id.rv_info)
    RecyclerView infoRv;

    List<LightDetailBean> detailBeanList = new ArrayList<>();
    List<LightDetailBean> detailBeanListALL = new ArrayList<>();
    DetailAdapter detailAdapter;
    boolean isVisble = true;


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
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        detailAdapter = new DetailAdapter(R.layout.item_detail_control, detailBeanList);
        infoRv.setAdapter(detailAdapter);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ControlLineAdapter(R.layout.item_line_control, mlist);
        rv.setAdapter(adapter);
        terminalId = getIntent().getStringExtra("terminalId");
//        terminalId ="2";//TODO
        mPresenter.getTerminalDetail(terminalId);

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
        this.concentratorBean = concentratorBean;
        LogUtils.v(concentratorBean.toString());
        showIntent(concentratorBean);
    }

    @Override
    public void showLineList(List<LineBean> list) {
        if (list == null) return;
        mlist.clear();
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        if (mlist.size() > 0) {
            llNull.setVisibility(View.GONE);
        } else {
            llNull.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showIntent() {
        showToast("操作成功，请稍后刷新");
        mPresenter.getTerminalDetail(terminalId);

        mPresenter.getLineList(terminalId);

    }

    private CustomDialog customDialog;
    CustomDialog.Builder builder;

    @OnClick({R.id.control_group, R.id.control_lamp, R.id.ll_show, R.id.control_open, R.id.control_close, R.id.control_reStart, R.id.control_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_group:
                if (concentratorBean != null && concentratorBean.getTerminalOnOff() == 1) {
                    startActivity(new Intent(TerminalControlActivity.this, GroupControlActivity.class).putExtra("terminalId", terminalId));
                } else {
                    showToast("集中器未合闸");
                }
                break;
            case R.id.control_lamp:
                if (concentratorBean != null && concentratorBean.getTerminalOnOff() == 1) {
                    startActivity(new Intent(TerminalControlActivity.this, LigitDeviceControlActivity.class).putExtra("terminalId", terminalId));
                } else {
                    showToast("集中器未合闸");
                }
                break;
            case R.id.control_close:
                showTimeDialog(0, "拉闸");
                break;
            case R.id.control_open:
                showTimeDialog(1, "合闸");
                break;
            case R.id.control_reStart:
                showTimeDialog(2, "重启");
                break;
            case R.id.control_refresh:
                mPresenter.getTerminalDetail(terminalId);

                mPresenter.getLineList(terminalId);
                break;
            case R.id.ll_show:
                if (isVisble) {
                    if (detailBeanListALL.size() > 8) {
                        List<LightDetailBean> list = detailBeanListALL.subList(0, 7);
                        detailBeanList.clear();
                        detailBeanList.addAll(list);
                        detailAdapter.notifyDataSetChanged();
                    }
                    isVisble = false;
                    ivShow.setImageResource(R.drawable.image_open);
                    tvShow.setText("打开");
                } else {
                    detailBeanList.clear();
                    detailBeanList.addAll(detailBeanListALL);
                    detailAdapter.notifyDataSetChanged();
                    isVisble = true;
                    ivShow.setImageResource(R.drawable.image_close_tab);
                    tvShow.setText("收起");
                }


                break;
        }
    }

    void showTimeDialog(int opt, String title) {
        stopTimer();
        builder = new CustomDialog.Builder(TerminalControlActivity.this)
                .setTitle("提示")
                .setMessage("确定" + title + "?")
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
        if (opt>0) {
            setTimer();
        }
        customDialog = builder.create();
        customDialog.show();
    }

    void postData(int opt) {
        List<Integer> list = new ArrayList<>();
        for (LineBean lineBean : mlist) {
            if (lineBean.isCheck()) {
                list.add(lineBean.getLineCode());
            }
        }
        Integer[] arr = (Integer[]) list.toArray(new Integer[list.size()]);
        Map<String, Object> params = new HashMap<>();
        params.put("opt", opt);
        mPresenter.realTimeCtrlLine(terminalId, params, arr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        stopTimer();
    }

    private int TIMER = 5;
    private MyTimeTask task;

    private void setTimer() {
        task = new MyTimeTask(1000, new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
                //或者发广播，启动服务都是可以的
            }
        });
        task.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    TIMER--;
                    builder.setPositiveButton(TIMER + "");
                    if (TIMER == 0) {
                        stopTimer();
                        builder.setPositiveButton("确定");

                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void stopTimer() {
        if (task != null) {
            task.stop();
        }
        TIMER = 5;
    }

    public void showIntent(ConcentratorBean concentratorBean) {
        if (concentratorBean == null) return;
        detailBeanList.clear();
        detailBeanListALL.clear();
        List<LightDetailBean> list = new ArrayList<>();
        list.add(new LightDetailBean("当前状态", concentratorBean.getIsOnlineText() + ""));
        list.add(new LightDetailBean("运行模式", concentratorBean.getMaintenanceModeText() + ""));
        list.add(new LightDetailBean("开灯时间", concentratorBean.getLightOnTime() + ""));
        list.add(new LightDetailBean("关灯时间", concentratorBean.getLightOffTime() + ""));
        list.add(new LightDetailBean("灯控器数", concentratorBean.getLightDeviceSucceedCount()+"/"+concentratorBean.getLightDeviceCount()+"/"+concentratorBean.getLightDeviceActualSum() + ""));
        list.add(new LightDetailBean("箱门状态", concentratorBean.getDoorStateText() + ""));
        list.add(new LightDetailBean("上电时间", concentratorBean.getPowerOnTime() + ""));
        list.add(new LightDetailBean("掉电时间", concentratorBean.getPowerOffTime() + ""));
        list.add(new LightDetailBean("电压", concentratorBean.getVoltage() + ""));
        list.add(new LightDetailBean("电流", concentratorBean.getAmpere() + ""));
        list.add(new LightDetailBean("功率", concentratorBean.getPower() + ""));
        list.add(new LightDetailBean("电量", concentratorBean.getEnergy() + ""));
        list.add(new LightDetailBean("漏电电流", concentratorBean.getLeakCurrent() + ""));
        list.add(new LightDetailBean("信号", concentratorBean.getSignalStrengthText() + ""));
        detailBeanList.addAll(list);
        detailBeanListALL.addAll(list);
        detailAdapter.notifyDataSetChanged();
    }
}
