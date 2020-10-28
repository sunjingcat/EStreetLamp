package com.zz.lamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.business.control.LigitDeviceControlActivity;
import com.zz.lamp.business.entry.adapter.LineAdapter;
import com.zz.lamp.business.login.LoginActivity;
import com.zz.lamp.widget.CustomDialog;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.ui.mvp.BasePresenter;


/**
 * 启动页
 */
public class StartpageActivity extends MyBaseActivity implements View.OnClickListener {
    private TextView skip;
    private TextView tv_info;
    CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_startpage;
    }

    private void initSeconds() {
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                skip.setText("跳过" + millisUntilFinished / 1000 + "s");

            }

            @Override
            public void onFinish() {
                skip.setText("跳过" + "0s");
//                CacheUtility.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyIiwiaXNfZmlyc3RfbG9naW4iOiJmYWxzZSIsImJ1aWxkaW5nX2N1cnJlbnRfY29uc3RydWN0aW9uX3NpdGVfbmFtZSI6IuWkqeeJqeacquadpeWfjiIsImJ1aWxkaW5nX3VzZXJfdHlwZSI6IjEiLCJzY29wZSI6ImJ1aWxkaW5ncyIsImJ1aWxkaW5nX2N1cnJlbnRfY29uc3RydWN0aW9uX3NpdGVfY29kZSI6IjIwMTkwMTI0MDEiLCJ1dWlkIjoiMTczNTcyMDlhZWFhNDkxMWIzMDJhZWE2ZjIyODAyNDkifQ.hpLEIw0dX2gdyhXnA7qS5Jbjjc3Eo1ta50k8B1tEIEfLofjG_9hMGVNiH7eRnu02rJ4H6TuDgSEBP5YOyYWZ_w");
//                CacheUtility.spSave(CacheUtility.KEY_PROGRAM_CODE,"2019012401");
                String token = CacheUtility.getToken();
                if (TextUtils.isEmpty(CacheUtility.getToken())) {
                    startActivity(new Intent(StartpageActivity.this, LoginActivity.class));
                } else {
                    int indexType = CacheUtility.getIndexType();
                    if (indexType == 1) {
                        startActivity(new Intent(StartpageActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(StartpageActivity.this, MainActivity.class));
                    }
                }

                finish();
            }
        }.start();
    }

    @Override
    protected void initView() {
        skip = (TextView) findViewById(R.id.skip);
        tv_info = (TextView) findViewById(R.id.tv_info);

        skip.setOnClickListener(this);
        if (CacheUtility.spGetOut(CacheUtility.KEY_FIRST,false)){

            initSeconds();
        } else {
            showDialog();
        }

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }

        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:

                if (TextUtils.isEmpty(CacheUtility.getToken())) {
                    startActivity(new Intent(StartpageActivity.this, LoginActivity.class));
                } else {
                    int indexType = CacheUtility.getIndexType();
                    if (indexType == 1) {
                        startActivity(new Intent(StartpageActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(StartpageActivity.this, MainActivity.class));
                    }
                }
                if (timer != null) {
                    timer.cancel();
                }

                finish();
                break;
        }
    }

    private CustomDialog customDialog;

    private void showDialog() {
        String TEXT_STRING = "感谢您选择智慧城市APP！我们非常重视您的个人信息的隐私保护。为了更好的保障您的权益，请您务必审慎阅读、充分理解“服务协议”和“隐私政策”各条款。您可以阅读《服务协议》和《隐私政策》了解详细信息。如果您同意以上协议内容，请点击“同意”开始使用我们的产品和服务。";
        String TEXT_KEY = "《服务协议》";
        String TEXT_KEY2 = "《隐私政策》";

        int startIndex = TEXT_STRING.indexOf(TEXT_KEY);
        int endIndex = startIndex + TEXT_KEY.length();

        int startIndex2 = TEXT_STRING.indexOf(TEXT_KEY2);
        int endIndex2 = startIndex2 + TEXT_KEY2.length();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(TEXT_STRING);

//    可单独文本前景色
        stringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex,
                endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        stringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex2,
                endIndex2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //设置文本点击事件
        stringBuilder.setSpan(new ClickSpannable(this) {
                                  @Override
                                  public void onClick(@NonNull View widget) {
                                      super.onClick(widget);
                                      startActivity(new Intent(StartpageActivity.this, SeviceInfoActivity.class).putExtra("flag", 1));
                                  }
                              }, startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文本点击事件
        stringBuilder.setSpan(new ClickSpannable(this) {
                                  @Override
                                  public void onClick(@NonNull View widget) {
                                      super.onClick(widget);
                                      startActivity(new Intent(StartpageActivity.this, SeviceInfoActivity.class).putExtra("flag", 2));
                                  }
                              }, startIndex2, endIndex2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        CustomDialog.Builder builder = new CustomDialog.Builder(this)
                .setTitle("服务协议和隐私政策")
                .setMessage(stringBuilder)
                .setCancelOutSide(false)
                .setNegativeButton("暂不使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("同意", new CustomDialog.Builder.OnPClickListener() {
                    @Override
                    public void onClick(CustomDialog v, String msg) {
                        CacheUtility.spSave(CacheUtility.KEY_FIRST, true);
                        initSeconds();
                    }
                });
        customDialog = builder.create();
        customDialog.show();
    }

    /**
     * SpannableStringBuilder 点击事件
     */
    public class ClickSpannable extends ClickableSpan {


        public ClickSpannable(View.OnClickListener onClickListener) {

        }


        @Override
        public void onClick(@NonNull View widget) {


        }
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
