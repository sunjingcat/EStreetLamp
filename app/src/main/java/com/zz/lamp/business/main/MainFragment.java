package com.zz.lamp.business.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;

import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceKind;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.main.mvp.Contract;
import com.zz.lamp.business.main.mvp.presenter.MapPresenter;
import com.zz.lamp.business.map.clusterutil.MyItem;
import com.zz.lamp.business.map.clusterutil.clustering.ClusterManager;
import com.zz.lamp.business.mine.MineActivity;
import com.zz.lamp.utils.AMapUtils;
import com.zz.lamp.utils.FileUtils;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lamp.utils.JsonCompress;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.TabUtils;
import com.zz.lamp.utils.TimeUtils;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.core.http.utils.GsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */
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
    Integer[] tabsType = {0, 1, 2, 3, 4, 5, 6};
    Unbinder unbinder;
    @BindView(R.id.bmapView)
    MapView bmapView;
    private BaiduMap mBaiduMap;
    private String searchValue;
    public LocationClient mLocationClient;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位
    public BDLocationListener myListener = new MyLocationListener();
    private static final String CUSTOM_FILE_NAME_CX = "custom_map_config_CX.sty";

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

    ClusterManager mClusterManager;

    @Override
    protected int getCreateView() {
        return R.layout.fragment_main;
    }


    @Override
    protected void initView(View view) {
        mBaiduMap = bmapView.getMap();
        if (beLogin()) return;
        mPresenter.deviceKindList();
//        mBaiduMap.showMapPoi(false);
        mClusterManager = new ClusterManager<MyItem>(getActivity(), mBaiduMap);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                switch (item.getDeviceKind()) {
                    case 1:
                        mPresenter.getTerminalData(item.getId());
                        break;
                    case 2:
                        mPresenter.getLightDeviceData(item.getId());
                        break;
//                    case 3:
//                        mPresenter.getLightDeviceData(id);
//                        break;
                }

                return false;
            }
        });
        mPresenter.getUserInfoData();
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(searchEdit);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    searchValue = v.getText().toString().trim();
                    clearMarkers();
                    getData(tabsType[tabDevice.getSelectedTabPosition()]);

                    return true;
                }

                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    searchValue = "";
                    clearMarkers();
                    getData(tabsType[tabDevice.getSelectedTabPosition()]);
                }
            }
        });

        String customStyleFilePath = FileUtils.getAssetsCacheFile(getActivity(), CUSTOM_FILE_NAME_CX);

        // 设置个性化地图样式文件的路径和加载方式
        bmapView.setMapCustomStylePath(customStyleFilePath);
        // 动态设置个性化地图样式是否生效
        bmapView.setMapCustomStyleEnable(true);
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        showLoading("");


    }

    private void moveCenter(LatLng latLng) {
        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(status1, 500);
    }

    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public MapPresenter initPresenter() {
        return new MapPresenter(this);
    }

    @Override
    public void onError() {


    }


    @OnClick({R.id.main_mine, R.id.my_site, R.id.refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_mine:
                getActivity().startActivity(new Intent(getActivity(), MineActivity.class));
                break;

            case R.id.my_site:
                PermissionUtils.getInstance().checkPermission(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        isFirstLoc = true;
                        //开启定位
                        mLocationClient.start();
                        //图片点击事件，回到定位点
                        mLocationClient.requestLocation();
                    }

                    @Override
                    public void onDenied() {

                    }
                });

                break;
            case R.id.refresh:
                clearMarkers();
                getData(tabsType[tabDevice.getSelectedTabPosition()]);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bmapView.onPause();
    }

    void clearMarkers() {
        try {
            if (myItems != null && !myItems.isEmpty()) {
                myItems.clear();
                mClusterManager.clearItems();
                mBaiduMap.clear();
                bmapView.invalidate();


            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bmapView != null) {
            bmapView.onDestroy();
        }
        dismissLoading();
    }
    List<MapListBean> array = new ArrayList<>();
    void getData(int deviceKind) {
        LogUtils.v("0------请求开始", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
        Map<String, Object> map = new HashMap<>();
        map.put("deviceKind", deviceKind);
        if (!TextUtils.isEmpty(searchValue)) {
            map.put("searchValue", searchValue);
        }
        mPresenter.getData(map);

    }
    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return new String(buffer, 0, buffer.length, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void showResult(List<MapListBean> list) {
        LogUtils.v("1------解析结果", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
        addMarkers(list);
    }

    @Override
    public void showResult( String result) {
        LogUtils.v("1------请求结果", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = JsonCompress.unzipString(result);
                    LogUtils.v("------2");
                    array = new Gson().fromJson(
                            s,
                            new TypeToken<List<MapListBean>>(){}.getType());
                    LogUtils.v("------3");
                    mHandler.sendEmptyMessage(2);
                } catch (IOException e) {
                    LogUtils.v("------解析错误", e.toString());
                }
            }
        }).start();
    }

    @Override
    public void showDeviceKindList(DeviceKind listBean) {
        if (listBean == null) return;
        List<DeviceKind.DeviceKindItem> listBeans = listBean.getDeviceKinds();
        if (listBeans != null && listBeans.size() > 0) {
            List<String> tabList = new ArrayList<>();
            List<Integer> tabTypeList = new ArrayList<>();
            for (int i = 0; i < listBeans.size(); i++) {
                tabList.add(listBeans.get(i).getKindLabel());
                tabTypeList.add(listBeans.get(i).getKindCode());
            }
            tabs = tabList.toArray(new String[tabList.size()]);
            tabsType = tabTypeList.toArray(new Integer[tabTypeList.size()]);
        }

        TabUtils.setTabs(tabDevice, this.getLayoutInflater(), tabs);
        tabDevice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 16);
                clearMarkers();
                getData(tabsType[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabUtils.setTabSize(tab, 14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getData(tabsType[0]);
        if (listBean.getCenter() != null) {
            moveCenter(new LatLng(listBean.getCenter().getLat(), listBean.getCenter().getLng()));
        }
    }


    List<MapListBean> mapListList = new ArrayList<>();

    List<MyItem> myItems = new ArrayList<>();
    Map<String, Bitmap> icons = new HashMap<>();

    void addMarkers(List<MapListBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mapListList.clear();
        mapListList.addAll(list);
        myItems.clear();
        showLoading("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.v("2------图片下载", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
                    for (MapListBean mapListBean : list) {
                        if (mapListBean.getLat() == 0.0 || mapListBean.getLng() == 0.0) continue;
                        Bitmap bitmap1 = null;
                        if (icons.containsKey(mapListBean.getMarkerIconPath())){
                            bitmap1 = icons.get(mapListBean.getMarkerIconPath());
                        }else {
                            FutureTarget<Bitmap> target = Glide.with(getActivity())
                                    .asBitmap()
                                    .load(CacheUtility.getURL() + mapListBean.getMarkerIconPath())
                                    .submit();
                            bitmap1 = target.get();
                            icons.put(mapListBean.getMarkerIconPath(),bitmap1);
                        }
                        if (bitmap1 == null) continue;
                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(bitmap1);
                        LatLng point = new LatLng(mapListBean.getLat(), mapListBean.getLng());
                        myItems.add(new MyItem(mapListBean.getId(), mapListBean.getDeviceKind(), point, bitmap));

                    }
                    LogUtils.v("3------下载完毕", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
                    if (myItems.size() > 0) {
                        mHandler.sendEmptyMessage(1);
                    }

                } catch (Exception e) {
                    clearMarkers();
                    LogUtils.v("sj--", e.getMessage().toString());
                }
            }
        }).start();


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        LogUtils.v("4------开始画点", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
                        mClusterManager.addItems(myItems);
                        AMapUtils.setMapZoom(mapListList, mBaiduMap);
                        LogUtils.v("5------画点完毕", TimeUtils.getTime(new Date().getTime(), TimeUtils.DEFAULT_DATE_FORMAT));
                        dismissLoading();
                    } catch (Exception e) {

                    }

                    break;
                case 2:
                    showResult(array);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void showTerminalData(ConcentratorBean concentratorBean) {
        startActivity(new Intent(getActivity(), InfoActivity.class).putExtra("TerminalInfo", concentratorBean));
    }

    @Override
    public void showLightDeviceData(LightDevice lightDevice) {
        startActivity(new Intent(getActivity(), InfoActivity.class).putExtra("DeviceInfo", lightDevice));
    }

    @Override
    public void showUserInfo(UserBasicBean userInfo) {
        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
            try {
                GlideUtils.loadCircleImage(getActivity(), userInfo.getAvatar(), main_mine);
            } catch (Exception e) {

            }

        }
    }

    @Override
    public void showError() {
        dismissLoading();
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据

            if (location != null) {
                MyLocationData locData = new MyLocationData.Builder()
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
                LogUtils.v(location.getAddrStr() + "location");
                // 当不需要定位图层时关闭定位图层
                mBaiduMap.setMyLocationEnabled(true);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    // 设置定位数据
                    mBaiduMap.setMyLocationData(locData);
                    AMapUtils.setMyMapZoom(mapListList, mBaiduMap, latLng);
                }

            }
            if (location.getLocType() == BDLocation.TypeServerError) {
                Toast.makeText(getActivity(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Toast.makeText(getActivity(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                Toast.makeText(getActivity(), "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
