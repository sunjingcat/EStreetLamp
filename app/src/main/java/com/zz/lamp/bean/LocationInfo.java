package com.zz.lamp.bean;

public class LocationInfo {
    private String address;
    private double latitude;
    private double longtitu;

    public LocationInfo(String address, double latitude, double longtitu) {
        this.address = address;
        this.latitude = latitude;
        this.longtitu = longtitu;
    }

    public LocationInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitu() {
        return longtitu;
    }

    public void setLongtitu(double longtitu) {
        this.longtitu = longtitu;
    }
}
