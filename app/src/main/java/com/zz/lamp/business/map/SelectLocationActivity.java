package com.zz.lamp.business.map;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.business.entry.adapter.SearchLocationAdapter;
import com.zz.lamp.utils.AMapUtils;
import com.zz.lamp.utils.LngLat;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLocationActivity extends MyBaseActivity implements OnGetGeoCoderResultListener {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.rv)
    RecyclerView rv;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位
    public BDLocationListener myListener = new MyLocationListener();
    List<PoiInfo> mlist = new ArrayList<>();
    SearchLocationAdapter adapter;
    PoiInfo locationInfo = new PoiInfo();
    String mCity = "天津";
    LatLng lastLngLat = null;

    @Override
    protected int getContentView() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_select_location;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    SuggestionSearch mSuggestionSearch;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchLocationAdapter(R.layout.item_simple, mlist);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                tv_name.setText(mlist.get(position).getAddress() + "");

                double latitude = mlist.get(position).getLocation().latitude;
                double longitude = mlist.get(position).getLocation().longitude;
                showLocation(latitude, longitude);
                locationInfo = mlist.get(position);

            }
        });
        initMap();
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(searchEdit);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    String trim = v.getText().toString().trim();
                    if (!TextUtils.isEmpty(trim)) {
                        suggestSearch(trim);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * * 模糊搜索
     * *
     * * @param keyword 关键字
     */
    private void suggestSearch(String keyword) {

        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(keyword)
                .city("苏州"));
    }

    /**
     * 模糊搜索监听
     */
    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            } else {
                List<PoiInfo> list = new ArrayList<>();
                List<SuggestionResult.SuggestionInfo> resl = res.getAllSuggestions();
                for (int i = 0; i < resl.size(); i++) {
                    Log.i("result: ", "city" + resl.get(i).city + " dis " + resl.get(i).district + "key " + resl.get(i).key);
                    PoiInfo info = new PoiInfo();
                    info.setLocation(resl.get(i).getPt());
                    info.setAddress(resl.get(i).getKey());
                    list.add(info);
                }
                mlist.clear();
                mlist.addAll(list);
                adapter.notifyDataSetChanged();
            }
            //获取在线建议检索结果
        }
    };

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    GeoCoder geoCoder;

    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //开启热力图
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        // tv=(TextView) findViewById(R.id.editText1);
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
        mBaiduMap.showMapPoi(false);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //地图状态开始改变。
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            //地图状态改变结束
            public void onMapStatusChangeFinish(MapStatus status) {
                //改变结束之后，获取地图可视范围的中心点坐标
                LatLng latLng = status.target;
                showLocation(latLng.latitude, latLng.longitude);
                LogUtils.v("sj--",AMapUtils.calculateLineDistance(latLng, lastLngLat)+"");
                if (lastLngLat!=null&&AMapUtils.calculateLineDistance(latLng,lastLngLat)>5) {
                    double v = AMapUtils.calculateLineDistance(latLng, lastLngLat);
                    geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
                    LogUtils.v("sj--",latLng.toString());
                }

                //拿到经纬度之后，就可以反地理编码获取地址信息了
                //initGeoCoder(latLng)
                lastLngLat = latLng;

            }

            //地图状态变化中
            public void onMapStatusChange(MapStatus status) {
            }
        });


    }

    //配置定位SDK参数
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

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (null != reverseGeoCodeResult) {

            tv_name.setText(reverseGeoCodeResult.getAddress() + "");
            locationInfo.setLocation(reverseGeoCodeResult.getLocation());
            locationInfo.setAddress(reverseGeoCodeResult.getAddress());
            List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
            if (poiList == null) {
                return;
            }
            mlist.clear();
            mlist.addAll(poiList);
            adapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.toolbar_subtitle, R.id.my_site})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                Intent intent = new Intent();
                intent.putExtra("location", locationInfo);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.my_site:
                showLocation(latLng.latitude, latLng.longitude);
                break;
        }
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
                showLocation(location.getLatitude(), location.getLongitude());
                tv_name.setText(location.getAddrStr() + "");
                locationInfo = new PoiInfo();
                locationInfo.setAddress(location.getAddrStr());
                locationInfo.setLocation(latLng);
                ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
                reverseGeoCodeOption.location(latLng);
                geoCoder.reverseGeoCode(reverseGeoCodeOption);

            }
            if (location.getLocType() == BDLocation.TypeServerError) {
                Toast.makeText(SelectLocationActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Toast.makeText(SelectLocationActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                Toast.makeText(SelectLocationActivity.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private void showLocation(double latitude, double longitude) {


        LatLng ll = new LatLng(latitude,
                longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

}
