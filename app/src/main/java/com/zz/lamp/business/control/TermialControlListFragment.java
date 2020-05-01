package com.zz.lamp.business.control;

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
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.RealTimeCtrlTerminal;
import com.zz.lamp.business.control.adapter.ControlJzqAdapter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class TermialControlListFragment extends MyBaseFragment implements OnRefreshListener, OnLoadMoreListener {
    List<RealTimeCtrlTerminal> mlist = new ArrayList<>();
    ControlJzqAdapter adapter;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int pageNum = 1;
    int pageSize = 20;
    public TermialControlListFragment() {
    }

    Unbinder unbinder;

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
    protected int getCreateView() {
        return R.layout.fragment_terminal_control_list;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ControlJzqAdapter(R.layout.item_control_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        getData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum=1;
        getData();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getData();
        refreshLayout.finishLoadMore();
    }
    public void showIntent(List<RealTimeCtrlTerminal> list) {
        if (list == null) return;
        if (pageNum == 0) {
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
    void getData(){
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        RxNetUtils.request(getCApi(ApiService.class).getRealTimeCtrlTerminalList(map), new RequestObserver<JsonT<List<RealTimeCtrlTerminal>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<RealTimeCtrlTerminal>> data) {
                if (data.isSuccess()) {
                    showIntent(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<RealTimeCtrlTerminal>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(getActivity()));
    }

}
