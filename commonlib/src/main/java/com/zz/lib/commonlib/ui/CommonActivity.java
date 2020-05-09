package com.zz.lib.commonlib.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.zz.lib.commonlib.CommonApplication;
import com.zz.lib.commonlib.utils.PermissionUtils;

/**
 * Created by admin on 2018/4/21.
 */

public abstract class CommonActivity extends AppCompatActivity {

    public Context context;
    private long time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonApplication.activity = this;
        this.context = this;


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);


    }
}