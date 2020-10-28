package com.zz.lamp.business.mine;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.SeviceInfoActivity;
import com.zz.lamp.StartpageActivity;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.Version;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.UpdateManager;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

/**
 * 关于
 */
public class AboutActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_icon)
    ImageView tvIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @BindView(R.id.tv_service)
    TextView tv_service;
    @BindView(R.id.bt_update)
    Button btUpdate;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvVersion.setText(getVersioName());
        tv_service.setMovementMethod(LinkMovementMethod.getInstance());
        getData();
        String TEXT_STRING = "《服务协议》和《隐私政策》";
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
                                      startActivity(new Intent(AboutActivity.this, SeviceInfoActivity.class).putExtra("flag", 1));
                                  }
                              }, startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文本点击事件
        stringBuilder.setSpan(new ClickSpannable(this) {
                                  @Override
                                  public void onClick(@NonNull View widget) {
                                      super.onClick(widget);
                                      startActivity(new Intent(AboutActivity.this, SeviceInfoActivity.class).putExtra("flag", 2));
                                  }
                              }, startIndex2, endIndex2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_service.setText(stringBuilder);

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        new UpdateManager(this).checkUpdate();
    }

    private String getVersioName() {
        try {
            String versionName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return "";
    }

    void getData() {
        RxNetUtils.request(getCApi(ApiService.class).getVersionInfo(getVersionCode() + ""), new RequestObserver<JsonT<Version>>(this) {
            @Override
            protected void onSuccess(JsonT<Version> data) {
                if (data.isSuccess() && data.getData() != null) {
                    tvInfo.setText(data.getData().getChanges() + "");
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<Version> jsonT) {
                super.onFail2(jsonT);
                showToast(jsonT.getMessage());
            }
        }, null);
    }

    //获取app版本
    private int getVersionCode() {
        try {
            return getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
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

}
