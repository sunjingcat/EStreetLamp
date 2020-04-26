package com.zz.lamp.utils.woolglass;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

/**
 * 高斯模糊工具类
 * <p>
 * Created by Bamboy on 2016/12/30.
 */
public class UtilBlurBitmap {

    private static final float BITMAP_SCALE = 0.3f;

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float blurRadius) {
        if (blurRadius < 0) {
            blurRadius = 0;
        }
        if (blurRadius > 25) {
            blurRadius = 25;
        }
        Bitmap outputBitmap = null;
        try {

            Class.forName("android.renderscript.ScriptIntrinsicBlur");
            int width = Math.round(bitmap.getWidth() * BITMAP_SCALE);
            int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);
            if (width < 2 || height < 2) {
                return null;
            }

            Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            GetHighView.showMessage();
            blurScript.setRadius(blurRadius);
            blurScript.setInput(tmpIn);
            blurScript.forEach(tmpOut);

            tmpOut.copyTo(outputBitmap);

            return outputBitmap;
        } catch (Exception e) {
            Log.e("Bemboy_Error", "Android版本过低");
            return null;
        }
    }
}
