package com.zz.lamp.utils;

import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.zz.lamp.bean.MapListBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AMapUtils {
    /**
     * 根据用户的起点和终点经纬度计算两点间距离，此距离为相对较短的距离，单位米。
     *
     * @param start 起点的坐标
     * @param end   终点的坐标
     * @return
     */
    public static double calculateLineDistance(LngLat start, LngLat end) {
        if ((start == null) || (end == null)) {
            throw new IllegalArgumentException("非法坐标值，不能为null");
        }
        double d1 = 0.01745329251994329D;
        double d2 = start.longitude;
        double d3 = start.latitude;
        double d4 = end.longitude;
        double d5 = end.latitude;
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

    /**
     * 比较选出集合中最大经纬度
     */
    private static double maxLatitude;
    private static double minLatitude;
    private static double maxLongitude;
    private static double minLongitude;
    public static void getMax(List<MapListBean> list) {
        List<Double> latitudeList = new ArrayList<Double>();
        List<Double> longitudeList = new ArrayList<Double>();
        for (MapListBean mapListBean : list) {
            if (mapListBean.getLat() == 0.0 || mapListBean.getLng() == 0.0) continue;
            double latitude = mapListBean.getLat();
            double longitude = mapListBean.getLng();
            latitudeList.add(latitude);
            longitudeList.add(longitude);
        }
        maxLatitude = Collections.max(latitudeList);
        minLatitude = Collections.min(latitudeList);
        maxLongitude = Collections.max(longitudeList);
        minLongitude = Collections.min(longitudeList);
    }

    /**
     * 计算两个Marker之间的距离
     */
    private static double distance;
    public static void calculateDistance() {
        distance = GeoHasher.GetDistance(maxLatitude, maxLongitude, minLatitude, minLongitude);
    }

    /**
     *根据距离判断地图级别
     */
    private static float level;
    private static LatLng center;
    public static void getLevel(BaiduMap mBaiduMap) {
        int zoom[] = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 1000, 2000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        for (int i = 0; i < zoom.length; i++) {
            int zoomNow = zoom[i];
            if (zoomNow - distance * 1000 > 0) {
                level = 18 - i + 6;
                //设置地图显示级别为计算所得level
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(level).build()));
                break;
            }
        }
    }
    /**
     * 计算中心点经纬度，将其设为启动时地图中心
     */
    public static void setCenter(BaiduMap mBaiduMap) {
        center = new LatLng((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2);
        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(center);
        mBaiduMap.animateMapStatus(status1, 500);
    }
}
