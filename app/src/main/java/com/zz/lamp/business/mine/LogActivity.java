package com.zz.lamp.business.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.OperLog;
import com.zz.lamp.business.alarm.AlarmHandleActivity;
import com.zz.lamp.business.alarm.adapter.AlarmAdapter;
import com.zz.lamp.business.mine.adapter.LogAdapter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class LogActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {

    List<OperLog> mlist = new ArrayList<>();
    LogAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int pageNum = 1;
    int pageSize = 20;
    @Override
    protected int getContentView() {
        return R.layout.activity_log;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter(R.layout.item_log,mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }
    void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        RxNetUtils.request(getCApi(ApiService.class).getLoglist(map), new RequestObserver<JsonT<List<OperLog>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<OperLog>> data) {
                showIntent(data.getData());
            }

            @Override
            protected void onFail2(JsonT<List<OperLog>> jsonT) {
                super.onFail2(jsonT);
                showToast(jsonT.getMessage());
            }
        }, null);
    }

    void showIntent(List<OperLog> list){
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
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getData();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getData();
        refreshLayout.finishLoadMore();
    }
}
