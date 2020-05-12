package com.zz.lib.commonlib.utils;

import android.content.Context;
import android.widget.Toast;



/**
 * Created by admin on 2017/3/20.
 */

public class IToast {
//    public static void show(String text) {
////        try {
////            ToastCompat.makeText(CommonApplication.getAppContext(), text, Toast.LENGTH_SHORT).show();
////        } catch (Exception e) {
////            Toast.makeText(CommonApplication.getAppContext(), text, Toast.LENGTH_SHORT).show();
////            Lg.e(e.toString());
////        }
//        com.ebo.commonlib.utils.toastcompat.Toast.makeText(CommonApplication.getAppContext(), text, EToast2.LENGTH_SHORT).show();
//    }
    public static void show(Context context,String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }

}
