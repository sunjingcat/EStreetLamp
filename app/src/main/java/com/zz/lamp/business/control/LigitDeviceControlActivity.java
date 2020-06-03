package com.zz.lamp.business.control;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LightPost;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.business.control.adapter.ControlLightAdapter;
import com.zz.lamp.business.control.adapter.ControlLineAdapter;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.GroupControlPresenter;
import com.zz.lamp.business.control.mvp.presenter.LightControlPresenter;
import com.zz.lamp.utils.MyTimeTask;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LigitDeviceControlActivity extends MyBaseActivity<Contract.IsetLightControlPresenter> implements Contract.IGetLightControlView, OnLoadMoreListener, OnRefreshListener {

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
    List<LightDeviceConBean> mlist = new ArrayList<>();
    ControlLightAdapter adapter;
    int pageNum = 1;
    int pageSize = 20;
    int luminance ;

    @Override
    protected int getContentView() {
        return R.layout.activity_light_control;
    }


    @Override
    public LightControlPresenter initPresenter() {
        return new LightControlPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ControlLightAdapter(R.layout.item_line_control, mlist);
        rv.setAdapter(adapter);
        terminalId = getIntent().getStringExtra("terminalId");
        getData();
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
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
                for (LightDeviceConBean lineBean : mlist) {
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
    public void showLightList(List<LightDeviceConBean> list) {
        if (list == null) return;
        if (pageNum==1) {
            mlist.clear();
        }
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
        showToast("请求成功");
    }
    private CustomDialog customDialog;
    CustomDialog.Builder builder;
    @OnClick({R.id.control_close, R.id.control_open, R.id.control_adjust})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.control_close:
                showTimeDialog(0,"关灯");
                break;
            case R.id.control_open:
                showTimeDialog(100,"开灯");
                break;
            case R.id.control_adjust:
                String[] PLANETS2 = new String[]{"0%","10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
                final SelectPopupWindows selectPopupWindows2 = new SelectPopupWindows(this, PLANETS2);
                selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        luminance = index*10;
                        if (luminance==0){
                            showTimeDialog(0,"关灯");
                        }else if (luminance==100){
                            showTimeDialog(100,"开灯");
                        } else {
                            showTimeDialog(luminance,"调光");
                        }
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
        }
        }

    void showTimeDialog(int opt,String title) {
        stopTimer();
        builder = new CustomDialog.Builder(LigitDeviceControlActivity.this)
                .setTitle("提示")
                .setMessage("确定"+title+"？")
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
        List<LightPost> list = new ArrayList<>();
        for (LightDeviceConBean lineBean : mlist) {
            if (lineBean.isCheck()) {
                list.add(new LightPost(lineBean.getId(),lineBean.getDeviceType()));
            }
        }
        if (list.size()==0){
            showToast("请先选中操作对象");
            return;
        }
        String s = new Gson().toJson(list);
        Map<String, Object> params = new HashMap<>();
        params.put("luminance", opt);
        params.put("lightDevices", s);
        params.put("terminalId", terminalId);
        mPresenter.realTimeCtrLight(terminalId,params);
    }
    void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        mPresenter.getLightList(terminalId,map);
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum=1;
        getData();
        refreshLayout.finishRefresh();
    }
}
