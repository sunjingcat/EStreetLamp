package com.zz.lamp.business.control;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.adapter.ControlLineAdapter;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.GroupControlPresenter;
import com.zz.lamp.utils.MyTimeTask;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupControlActivity extends MyBaseActivity<Contract.IsetGroupControlPresenter> implements Contract.IGetGroupControlView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.control_all)
    CheckBox controlAll;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.control_close)
    Button controlClose;
    @BindView(R.id.control_open)
    Button controlOpen;
    String terminalId;
    List<LineBean> mlist = new ArrayList<>();
    ControlLineAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_group_control;
    }


    @Override
    public GroupControlPresenter initPresenter() {
        return new GroupControlPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ControlLineAdapter(R.layout.item_line_control, mlist);
        rv.setAdapter(adapter);
        terminalId = getIntent().getStringExtra("terminalId");
        mPresenter.getGroupList(terminalId);
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
    public void showGroupList(List<LineBean> list) {
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
    @OnClick({R.id.control_close, R.id.control_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_close:
                showTimeDialog(0);
                break;
            case R.id.control_open:
                showTimeDialog(1);
                break;
        }
    }
    void showTimeDialog(int opt){
        stopTimer();
        builder = new CustomDialog.Builder(GroupControlActivity.this)
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
        mPresenter.realTimeCtrGroup(params);
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