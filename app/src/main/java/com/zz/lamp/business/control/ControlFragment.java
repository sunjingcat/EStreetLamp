package com.zz.lamp.business.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.business.alarm.LeftFragment;
import com.zz.lamp.business.alarm.RightFragment;
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
    TermialControlListFragment termialControlListFragment;
    VideoControlListFragment videoControlListFragment;
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

        TabUtils.setTabs(controlTab, this.getLayoutInflater(), tabs);
        controlTab.getTabAt(0).select();
        controlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 16);
                int position = tab.getPosition();
                onChangeFragment(position);
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
    void onChangeFragment(int position){
        if (position == 0) {
            if (termialControlListFragment==null) {
                termialControlListFragment = new TermialControlListFragment();
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, termialControlListFragment).commit();
        }else {
            if (videoControlListFragment==null) {
                videoControlListFragment = new VideoControlListFragment();
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, videoControlListFragment).commit();
        }
    }
}
