package com.zz.lamp.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;

import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.business.main.mvp.Contract;
import com.zz.lamp.business.main.mvp.presenter.MapPresenter;
import com.zz.lamp.business.mine.MineActivity;
import com.zz.lamp.utils.TabUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends MyBaseFragment<Contract.IsetMapPresenter> implements Contract.IGetMapView {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.main_mine)
    ImageView main_mine;
    @BindView(R.id.search_click)
    LinearLayout searchClick;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_device)
    TabLayout tabDevice;
    String[] tabs = {"全部设备", "集中器", "灯控器", "智慧灯杆", "LED", "摄像头", "IP音柱"};
    Unbinder unbinder;
    @BindView(R.id.bmapView)
    MapView bmapView;
    private BaiduMap mBaiduMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {
        mBaiduMap = bmapView.getMap();
        TabUtils.setTabs(tabDevice, this.getLayoutInflater(), tabs);
        tabDevice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 16);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabDevice.getTabAt(0).select();
        getData(0);
        mBaiduMap.showMapPoi(false);
    }
    private Animation getScaleAnimation() {
        //创建缩放动画
        ScaleAnimation mScale = new ScaleAnimation(1f, 2f, 1f);
        //设置动画执行时间
        mScale.setDuration(2000);
        //动画重复模式
        mScale.setRepeatMode(Animation.RepeatMode.RESTART);
        //动画重复次数
        mScale.setRepeatCount(1);
        //设置缩放动画监听
        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }
            @Override
            public void onAnimationEnd() {
            }
            @Override
            public void onAnimationCancel() {
            }
            @Override
            public void onAnimationRepeat() {
            }
        });
        return mScale;
    }
    @Override
    public MapPresenter initPresenter() {
        return new MapPresenter(this);
    }

    @Override
    public void onError() {

    }


    @OnClick({R.id.main_mine, R.id.search_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_mine:
                getActivity().startActivity(new Intent(getActivity(), MineActivity.class));
                break;
            case R.id.search_click:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bmapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bmapView.onDestroy();
    }
    void getData(int deviceKind){
        Map<String,Object> map = new HashMap<>();
        map.put("deviceKind",deviceKind);
        mPresenter.getData(map);
    }

    @Override
    public void showResult() {

    }

    @Override
    public void showDetailResult(List<MapListBean> alarmBean) {

    }
}
