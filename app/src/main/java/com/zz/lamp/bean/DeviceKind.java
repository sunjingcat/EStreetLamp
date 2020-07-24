package com.zz.lamp.bean;

import android.text.TextUtils;

import java.util.List;

public class DeviceKind {
    List<DeviceKindItem> deviceKinds;
    Center center;

    public Center getCenter() {
        return center;
    }

    public List<DeviceKindItem> getDeviceKinds() {
        return deviceKinds;
    }

   public class DeviceKindItem{
        int kindCode;
        String kindLabel;

        public int getKindCode() {
            return kindCode;
        }

        public String getKindLabel() {
            return kindLabel;
        }
    }
    public class Center{
        String lng;
        String lat;

        public double getLng() {
            if (TextUtils.isEmpty(lng))return 0.0;
            return Double.parseDouble(lng);
        }

        public double getLat() {
            if (TextUtils.isEmpty(lat))return 0.0;
            return Double.parseDouble(lat);
        }
    }

}
