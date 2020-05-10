package com.zz.lamp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lamp.R;
import com.zz.lamp.bean.Version;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lamp.widget.UploadDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.zz.lamp.net.RxNetUtils.getApi;

/**
 * 检查是否有更新
 *
 * @author Administrator
 */

public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    Builder builder;
    private String apkURL = "";
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private String apkName = "android.apk";
    private String versionName = "1.0";
    private String location;
    /* 下载保存路径 */
    private String mSavePath;
    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD:// 正在下载
                    mProgress.setProgress(progress);// 设置进度条位置
                    break;
                case DOWNLOAD_FINISH:
                    installApk();// 安装文件
                    btn_install.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };
    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    public UpdateManager(Context context) {
        this.mContext = context;
        builder = new Builder(mContext);

    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        initAppName();

        location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        location = Environment.getExternalStorageDirectory() + "/";
        mSavePath = location;
        updateVersion();
    }

    //获取app名称
    public void initAppName() {
        try {
            versionName = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
    }

    //获取app版本
    private int getVersionCode() {
        try {
            return mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        return 0;
    }

    private void updateVersion() {
        //网络请求
        RxNetUtils.request(getApi(ApiService.class).getVersion(""), new RequestObserver<JsonT<Version>>() {
            @Override
            protected void onSuccess(JsonT<Version> json) {
                if (json.isSuccess()) {
                    if (json.getData().getVersion_code() > getVersionCode()) {
                        if (!TextUtils.isEmpty(json.getData().getDownload())) {
                            apkURL = json.getData().getDownload();
                            showNoticeDialog(json.getData());
                        }
                    }
                } else {
                }
            }

            @Override
            protected void onFail2(JsonT<Version> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, null);
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog(Version content) {
        UploadDialog.Builder builder = new UploadDialog.Builder(mContext, "", versionName, content);
        builder.setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDownload();
            }
        });
        CustomDialog dia = builder.create();//.show();
        dia.setOnKeyListener(keylistener);
        dia.show();
    }

    private void toDownload() {
        PermissionUtils.getInstance().checkPermission((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
                showDownloadDialog();// 显示下载对话框
            }

            @Override
            public void onDenied() {

            }
        });
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 显示软件下载对话框
     */
    View btn_install;
    View btn_cancel;

    private void showDownloadDialog() {
        // 构造软件下载对话框

        builder.setTitle("正在更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update, null);
        mProgress = (ProgressBar) v.findViewById(R.id.updateProgress);
        builder.setView(v);

//        builder.setNegativeButton("取消更新", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                cancelUpdate = true;
//            }
//        });
        btn_cancel = v.findViewById(R.id.btn_01);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUpdate = true;
                mDownloadDialog.dismiss();
            }
        });
        btn_install = v.findViewById(R.id.btn_02);
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installApk();
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        mDownloadDialog.setOnKeyListener(keylistener);
        mDownloadDialog.show();
        cancelUpdate = false;
        new downloadApkThread().start();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkFile =
                new File(mSavePath, apkName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    startInstallPermissionSettingActivity();
                    return;
                }
            }
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivity(intent);
        }
        mContext.startActivity(intent);
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    URL url = new URL(apkURL);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, apkName);
                    Log.d("---------------------", apkFile.toString());
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (Exception e) {
                e.getMessage();
            }

        }
    }

}

