package com.zz.lamp.business.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.utils.LogUtils;
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

    Unbinder unbinder;
    @BindView(R.id.main_layout)
    FrameLayout mainLayout;
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
        onChangeFragment(0);
        alarmTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    void onChangeFragment(int position){
        if (position == 0) {
            setContentView(getActivity(),LeftFragment.class,R.id.main_layout);
        }else {
            setContentView(getActivity(),RightFragment.class,R.id.main_layout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String tabStr = getActivity().getIntent().getStringExtra("tab");
        if (!TextUtils.isEmpty(tabStr)&&tabStr.equals("2")) {
            onChangeFragment(0);
            alarmTab.getTabAt(0).select();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onError() {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Fragment fragment = lastFragment;
            /*然后在碎片中调用重写的onActivityResult方法*/
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
