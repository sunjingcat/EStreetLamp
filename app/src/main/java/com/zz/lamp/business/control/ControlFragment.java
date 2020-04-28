package com.zz.lamp.business.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.ConcentratorBean;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.business.control.adapter.ControlJzqAdapter;
import com.zz.lamp.utils.TabUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ControlFragment extends MyBaseFragment {
    String[] tabs = {"集中器", "摄像头"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.control_tab)
    TabLayout controlTab;
    @BindView(R.id.rv_jzq)
    RecyclerView rvJzq;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    List<ConcentratorBean> mlist = new ArrayList<>();
    List<ConcentratorBean> mlistVideo = new ArrayList<>();
    ControlJzqAdapter adapter;
    @Override
    protected int getCreateView() {
        return R.layout.fragment_control;
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
    protected void initView(View view) {
        rvJzq.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvJzq.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new ControlJzqAdapter(R.layout.item_control_jzq, mlist);
        rvJzq.setAdapter(adapter);

        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvVideo.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new ControlJzqAdapter(R.layout.item_control_jzq, mlistVideo);
        rvVideo.setAdapter(adapter);

        TabUtils.setTabs(controlTab, this.getLayoutInflater(), tabs);
        controlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 16);
                int position = tab.getPosition();
                if (position==0){
                    rvJzq.setVisibility(View.VISIBLE);
                    rvVideo.setVisibility(View.GONE);
                }else {
                    rvJzq.setVisibility(View.GONE);
                    rvVideo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onError() {

    }

    public void onRefresh() {
        if (mPresenter != null) {
//            mPresenter.getData();
        }
    }
}
