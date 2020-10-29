package com.zz.lamp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeviceInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_service_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        int flag = getIntent().getIntExtra("flag", 0);
        if (flag==1){
            toolbarTitle.setText("用户服务协议");
            webView.loadUrl("file:///android_asset/user_protocol.html");
        }else {
            toolbarTitle.setText("智慧城市隐私政策");
            webView.loadUrl("file:///android_asset/privacy_policy.html");
        }


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //支持App内部javascript交互
        webView.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.getSettings().setLoadWithOverviewMode(true);

        //设置可以支持缩放
        webView.getSettings().setSupportZoom(true);

        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.clearCache(true);

        //设置是否出现缩放工具
        webView.getSettings().setBuiltInZoomControls(false);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null){
            webView = null;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
