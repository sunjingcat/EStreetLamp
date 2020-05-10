package com.zz.lamp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

import androidx.annotation.NonNull;

public class NavUtils {
    /**
     * 根据包名检测某个APP是否安装
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2016/6/27,13:02
     * <h3>UpdateTime</h3> 2016/6/27,13:02
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     * @return true 安装 false 没有安装
     */
    public static boolean isInstalled() {
        return new File("/data/data/com.baidu.BaiduMap").exists();
    }

    /**
     * (此处输入方法执行任务.)
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2017/11/9,15:31
     * <h3>UpdateTime</h3> 2017/11/9,15:31
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     * @param context 上下文
     * @param coord_type coord_type 可选 坐标类型，可选参数，默认为bd09经纬度坐标
     * @param src  必选 调用来源，规则：companyName|appName。
     * @param location 经纬度 例如：39.9761,116.3282
     */
    public static  void invokeNavi(Context context, String coord_type , @NonNull String src, @NonNull String location){
        StringBuffer stringBuffer  = new StringBuffer("baidumap://map/navi?");
        if (!TextUtils.isEmpty(coord_type)){
            stringBuffer.append("coord_type=").append(coord_type);
        }
        stringBuffer.append("&src=").append(src);
        stringBuffer.append("&location=").append(location);
        Intent intent = new Intent();
        intent.setData(Uri.parse(stringBuffer.toString()));
        context.startActivity(intent);
    }
}
