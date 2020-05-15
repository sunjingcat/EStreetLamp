package com.zz.lamp.utils;

import android.content.Context;
import android.content.Intent;

import com.zz.lamp.business.alarm.ImageLookActivity;

public class ImagePreview {

    public static void preview(Context context, String image){
       context.startActivity(new Intent(context, ImageLookActivity.class).putExtra("url", image));

    }
}
