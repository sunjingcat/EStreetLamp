package com.zz.lamp.business.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;

import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.business.main.mvp.Contract;
import com.zz.lamp.business.main.mvp.presenter.MapPresenter;
import com.zz.lamp.business.mine.MineActivity;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lamp.utils.TabUtils;

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
                if (overlays!=null){overlays.clear();}
                getData(tab.getPosition());
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
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                String id = extraInfo.getString("id");
                int deviceKind = extraInfo.getInt("deviceKind",0);
                switch (deviceKind){
                    case 1:
                        mPresenter.getTerminalData(id);
                        break;
                    case 2:
                        mPresenter.getLightDeviceData(id);
                        break;
                }

                return false;
            }
        });
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

    void getData(int deviceKind) {
        Map<String, Object> map = new HashMap<>();
        map.put("deviceKind", deviceKind);
        mPresenter.getData(map);
    }


    @Override
    public void showResult(List<MapListBean> list) {
        addMarkers(list);
    }
    List<Overlay> overlays;
    void addMarkers(List<MapListBean> list) {
        List<OverlayOptions> overlayOptions = new ArrayList<>();
        for (MapListBean mapListBean : list) {
            if (mapListBean.getLat()==0.0||mapListBean.getLng()==0.0)continue;
            Bitmap bitmap1 = GlideUtils.base64ToBitmap(mapListBean.getMarkerIconPath());
            if (bitmap1==null)return;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(bitmap1);
            LatLng point = new LatLng(mapListBean.getLat(), mapListBean.getLng());
            Bundle bundle = new Bundle();
            bundle.putString("id",mapListBean.getId());
            bundle.putInt("deviceKind",mapListBean.getDeviceKind());
            OverlayOptions option = new MarkerOptions()
                    .extraInfo(bundle)
                    .position(point)
                    .icon(bitmap);
            overlayOptions.add(option);
        }
        overlays = mBaiduMap.addOverlays(overlayOptions);

    }

    @Override
    public void showTerminalData(ConcentratorBean concentratorBean) {
        startActivity(new Intent(getActivity(), InfoActivity.class).putExtra("TerminalInfo",concentratorBean));
    }

    @Override
    public void showLightDeviceData(LightDevice lightDevice) {
        startActivity(new Intent(getActivity(), InfoActivity.class).putExtra("DeviceInfo",lightDevice));
    }
}
