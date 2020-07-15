package com.zz.lamp.business.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.utils.TabUtils;
import com.zz.lamp.utils.woolglass.FragmentClass;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * 控制首页
 */
public class ControlFragment extends MyBaseFragment {
    String[] tabs = {"集中器", "摄像头"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.control_tab)
    TabLayout controlTab;
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
        onChangeFragment(0);
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
            setContentView(getActivity(),TermialControlListFragment.class,R.id.control_layout);
        }else {
            setContentView(getActivity(),VideoControlListFragment.class,R.id.control_layout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
