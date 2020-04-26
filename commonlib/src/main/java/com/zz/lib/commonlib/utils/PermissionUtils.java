package com.zz.lib.commonlib.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;


import com.zz.lib.commonlib.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2018/4/13.
 */

public class PermissionUtils {

    public static final int CODE_REQUEST_PERMISSION = 4455;

    //test
    //static修饰的静态变量在内存中一旦创建，便永久存在
    private static PermissionUtils instance = new PermissionUtils();
    private OnPermissionChangedListener listener;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        return instance;
    }

    private static ArrayList<String> checkOpsPermission(Context context, String[] permissions) {
        ArrayList<String> list = new ArrayList<>();
        for (String per :
                permissions) {
            if (!checkOpsPermission(context, per)) {
                list.add(per);
            }
        }
        return list;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean checkOpsPermission(Context context, String permission) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            String opsName = AppOpsManager.permissionToOp(permission);
            if (opsName == null) {
                return true;
            }
            int opsMode = appOpsManager.checkOpNoThrow(opsName, getUid(context), context.getPackageName());
            return opsMode == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {
            return true;
        }
    }

    public static int getUid(Context context) {
        int uid = -999;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            uid = Integer.valueOf(ai.uid).intValue();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return uid;
    }

    public void checkPermission(Activity context, String[] permissions, OnPermissionChangedListener listener) {
        this.listener = listener;
        if (Build.VERSION.SDK_INT < 23) {
            listener.onGranted();
            return;
        }
        String[] unGrantedPermissions = getUnGrantedPermissions(context, permissions);
        if (unGrantedPermissions == null || unGrantedPermissions.length <= 0) {
            listener.onGranted();
        } else {
            ActivityCompat.requestPermissions(context, unGrantedPermissions, CODE_REQUEST_PERMISSION);
        }
    }

    private String[] getUnGrantedPermissions(Activity context, String[] permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            return null;
        }
        List<String> readyPerList = new ArrayList<>();
        for (String permission :
                permissions) {
            boolean isGranted = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            if (!isGranted) readyPerList.add(permission);
        }

        if (readyPerList.size() > 0) {
            return readyPerList.toArray(new String[readyPerList.size()]);
        } else if (OSHelper.isMIUI()) {
            ArrayList<String> list = checkOpsPermission(context, permissions);
            return list.toArray(new String[list.size()]);
        } else {
            return readyPerList.toArray(new String[readyPerList.size()]);
        }
    }

    public void onRequestPermissionsResult(final Activity context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_REQUEST_PERMISSION) {
            boolean granted = true;
            for (int re :
                    grantResults) {
                granted = granted && (re == PackageManager.PERMISSION_GRANTED);
            }
            if (granted) {
                // Permission Granted
                listener.onGranted();

            } else {
                // Permission Denied
                if (requestCode == CODE_REQUEST_PERMISSION) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        String[] unGrantedPermissions = getUnGrantedPermissions(context, permissions);
                        String permissionCollectStr = "";
                        for (String persmission :
                                unGrantedPermissions) {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, persmission)) {
                                permissionCollectStr = perStr(context, persmission) + "、";
                            }
                        }
                        if (!TextUtils.isEmpty(permissionCollectStr)) {
                            permissionCollectStr = permissionCollectStr.substring(0, permissionCollectStr.length() - 1);
                            AskForPermission2(context, permissionCollectStr);
                        }
                    }
                }
            }
        }
    }

    public void AskForPermission2(final Context context, String permissionNames) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.permission_3) + permissionNames +
                        context.getString(R.string.permission_4))
                .setPositiveButton(context.getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        // 根据包名打开对应的设置界面
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(context.getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDenied();
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    private String perStr(Context context, String permission) {

        if (Manifest.permission.CAMERA.equalsIgnoreCase(permission)) {
            return context.getString(R.string.permission_camera);
        } else if (Manifest.permission.READ_EXTERNAL_STORAGE.equalsIgnoreCase(permission)) {

            return context.getString(R.string.permission_read_external_storage);
        } else if (Manifest.permission.READ_PHONE_STATE.equalsIgnoreCase(permission)) {

            return context.getString(R.string.permission_read_phone_state);
        } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equalsIgnoreCase(permission)) {

            return context.getString(R.string.permission_write_external_storage);
        } else if (Manifest.permission.VIBRATE.equalsIgnoreCase(permission)) {

            return context.getString(R.string.permission_vibrate);
        }
        return permission;
    }


    public interface OnPermissionChangedListener {

        void onGranted();

        void onDenied();


    }

}


