package com.lmx.friends.utils.woolglass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.view.Window;

import com.lmx.friends.utils.authority.AuthorityUtils;


/**
 * 截屏工具类
 * <p>
 * Created by Bamboy on 2016/12/30.
 */
public class GetHighView {

    /**
     * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
     *
     * @return
     */
    private static int getOtherHeight(Window window) {
        try {
            Rect frame = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int contentTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentTop - statusBarHeight;
            return statusBarHeight + titleBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取屏幕截屏 【不包含状态栏】
     *
     * @param activity
     * @return
     */
    public static Bitmap getScreenshot(Activity activity) {
        try {
            Window window = activity.getWindow();
            View view = window.getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap bmp1 = view.getDrawingCache();
            /**
             * 除去状态栏和标题栏
             **/
            int height = getOtherHeight(window);
            return Bitmap.createBitmap(bmp1, 0, height, bmp1.getWidth(), bmp1.getHeight() - height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Activity截图
     *

     * @return bitmap
     */
    public static void   showMessage() {
        new Thread(new Runnable() {
            public void run() {
                while (AuthorityUtils.ImmersionBar > 0) {
                    try {
                        Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AuthorityUtils.ImmersionBar--;
                }
//                App.context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            String s = new String();
//                        }
//                    }
//                });
            }
        }).start();
    }
}
