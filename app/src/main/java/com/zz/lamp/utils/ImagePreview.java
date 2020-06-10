package com.zz.lamp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.previewlibrary.GPreviewBuilder;
import com.zz.lamp.bean.ImageViewInfo;
import com.zz.lamp.business.alarm.ImageLookActivity;

import java.util.ArrayList;
import java.util.List;

public class ImagePreview {

    public static void preview(Context context, String image){

        List<ImageViewInfo> stringList=new ArrayList<>();
        stringList.add(new ImageViewInfo(image));
        GPreviewBuilder.from((Activity) context)
                .setData(stringList)
                .setCurrentIndex(0)
                .setSingleFling(true)//是否在黑屏区域点击返回
                .setDrag(true)//是否禁用图片拖拽返回
                .setType(GPreviewBuilder.IndicatorType.Dot).start();//指示器类型

    }
}
