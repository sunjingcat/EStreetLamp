package com.zz.lamp.business.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.utils.TabUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AlarmFragment extends MyBaseFragment {
    String[] tabs = {"未处理", "已处理"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.alarm_tab)
    TabLayout alarmTab;
    @BindView(R.id.rv_processed)
    RecyclerView rvProcessed;
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
        return R.layout.fragment_alarm;
    }

    @Override
    protected void initView(View view) {
        TabUtils.setTabs(alarmTab, this.getLayoutInflater(), tabs);
        alarmTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 16);
                int position = tab.getPosition();

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
}
