package com.zz.lamp.business.entry;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.business.entry.adapter.ConcentratorBeanAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.TerminalPresenter;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EntryFragment  extends MyBaseFragment<Contract.IsetTerminalPresenter> implements Contract.IGetTerminalView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ConcentratorBeanAdapter adapter;
    List<ConcentratorBean> mlist = new ArrayList<>();
    Unbinder unbinder;
    int pageNum = 1;
    int pageSize = 20;
    @Override
    protected int getCreateView() {
        return R.layout.fragment_entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new ConcentratorBeanAdapter(R.layout.item_entry_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        getData();
    }

    @Override
    public TerminalPresenter initPresenter() {
        return new TerminalPresenter(this);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 0;
        getData();
        refreshlayout.finishRefresh();
    }


    @Override
    public void onLoadMore(RefreshLayout refresh) {
        pageNum++;
        getData();
        refresh.finishLoadMore();
    }

    @OnClick({R.id.entry_qy, R.id.entry_jzq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_qy:
                getActivity().startActivity(new Intent(getActivity(),RegionActivity.class));
                break;
            case R.id.entry_jzq:
                getActivity().startActivity(new Intent(getActivity(),EntryJzqActivity.class));
                break;
        }
    }
    @Override
    public void showIntent(List<ConcentratorBean> list) {
        if (list==null)return;
        if (pageNum ==0){
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
    void getData(){
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
//        map.put("searchValue",pageSize);
        mPresenter.getTerminalList(map);
    }
}
