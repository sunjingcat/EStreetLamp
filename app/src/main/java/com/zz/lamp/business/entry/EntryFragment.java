package com.zz.lamp.business.entry;

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
import com.zz.lamp.base.ConcentratorBean;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.business.entry.adapter.ConcentratorBeanAdapter;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EntryFragment extends MyBaseFragment implements OnRefreshListener, OnLoadMoreListener {

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
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new ConcentratorBeanAdapter(R.layout.item_entry_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @OnClick({R.id.entry_qy, R.id.entry_jzq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_qy:

                break;
            case R.id.entry_jzq:
                break;
        }
    }
}
