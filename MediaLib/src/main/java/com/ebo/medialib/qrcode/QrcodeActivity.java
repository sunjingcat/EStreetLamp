package com.ebo.medialib.qrcode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ebo.medialib.R;
import com.ebo.medialib.qrcode.google.zxing.activity.CaptureActivity;
import com.ebo.medialib.qrcode.util.Constant;
import com.zz.lib.commonlib.ui.CommonActivity;
import com.zz.lib.commonlib.utils.PermissionUtils;

public class QrcodeActivity extends CommonActivity implements View.OnClickListener {
    Button btnQrCode; // 扫码
    TextView tvResult; // 结果
    RelativeLayout actionbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initViews();
    }

    public void initViews() {
        btnQrCode = (Button) findViewById(R.id.btn_qrcode);
        btnQrCode.setOnClickListener(this);
        tvResult = (TextView) findViewById(R.id.txt_result);
        actionbar_back = (RelativeLayout) findViewById(R.id.actionbar_back);
        actionbar_back.setOnClickListener(this);
    }

    // 开始扫码
    private void startQrCode() {

        PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.CAMERA}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
                // 二维码扫码
                Intent intent = new Intent(QrcodeActivity.this, CaptureActivity.class);
                startActivityForResult(intent, Constant.REQ_QR_CODE);
            }

            @Override
            public void onDenied() {

            }
        });


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_qrcode) {
            startQrCode();
        } else if (i == R.id.actionbar_back) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            tvResult.setText(scanResult);
        }
    }

}
