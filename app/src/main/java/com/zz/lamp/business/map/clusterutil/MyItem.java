package com.zz.lamp.business.map.clusterutil;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.model.LatLng;
import com.zz.lamp.business.map.clusterutil.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private String id;
    private int deviceKind;
    private LatLng latLng;
    private BitmapDescriptor bitmapDescriptor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeviceKind() {
        return deviceKind;
    }

    public void setDeviceKind(int deviceKind) {
        this.deviceKind = deviceKind;
    }


    public MyItem(String id, int deviceKind, LatLng latLng, BitmapDescriptor bitmapDescriptor) {
        this.id = id;
        this.deviceKind = deviceKind;
        this.latLng = latLng;
        this.bitmapDescriptor = bitmapDescriptor;
    }


    public MyItem() {
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setBitmapDescriptor(BitmapDescriptor bitmapDescriptor) {
        this.bitmapDescriptor = bitmapDescriptor;
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return bitmapDescriptor;
    }
}
