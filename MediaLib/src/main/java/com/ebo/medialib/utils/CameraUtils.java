package com.ebo.medialib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;

import androidx.annotation.RequiresApi;

/**
 * Created by admin on 2018/4/25.
 */

public class CameraUtils {

    public static final int TYPE_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
    public static final int TYPE_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;

    //判断前置摄像头是否存在
    private static int getCameraIdKitkat(int type){
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {
            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo
            if ( cameraInfo.facing ==type ) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }


    @SuppressLint("NewApi")
    private static String getFrontCameraLollipop(Context ctx){
        CameraManager manager = (CameraManager) ctx.getSystemService(Context.CAMERA_SERVICE);
        try {
            //获取可用摄像头列表
            for (String cameraId : manager.getCameraIdList()) {
                //获取相机的相关参数
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                // 不使用前置摄像头。
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    return cameraId;
                }
                /*
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }
                // 检查闪光灯是否支持。
                Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mFlashSupported = available == null ? false : available;
                mCameraId = cameraId;
                Log.e(TAG," 相机可用 ");
                return;
                */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Camera getCamera(int type){
        int cameraId = getCameraIdKitkat(type);
        return Camera.open(cameraId);
    }

}
