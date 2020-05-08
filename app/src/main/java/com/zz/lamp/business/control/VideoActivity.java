package com.zz.lamp.business.control;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.YsConfig;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class VideoActivity extends MyBaseActivity {

    @BindView(R.id.player_ui)
    EZUIPlayer playerUi;
    CameraBean cameraBean;

    @Override
    protected int getContentView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return R.layout.activity_video;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        cameraBean = (CameraBean) getIntent().getSerializableExtra("camera");
//创建loadingview
        ProgressBar mLoadView = new ProgressBar(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mLoadView.setLayoutParams(lp);

//设置loadingview
        playerUi.setLoadingView(mLoadView);
        //设置播放回调callback
        playerUi.setCallBack(new EZUIPlayer.EZUIPlayerCallBack() {
            @Override
            public void onPlaySuccess() {
                LogUtils.v("sj----success");
            }

            @Override
            public void onPlayFail(EZUIError ezuiError) {
                LogUtils.v("sj----" + ezuiError.getErrorString());
            }

            @Override
            public void onVideoSizeChange(int i, int i1) {
                LogUtils.v("sj----success");
            }

            @Override
            public void onPrepared() {
                LogUtils.v("sj----onPrepared");
            }

            @Override
            public void onPlayTime(Calendar calendar) {

            }

            @Override
            public void onPlayFinish() {

            }
        });
        getData();
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void onStop() {
        super.onStop();

        //停止播放
        playerUi.stopPlay();
    }

    void getData() {
        RxNetUtils.request(getCApi(ApiService.class).getYsConfig(), new RequestObserver<JsonT<YsConfig>>(this) {
            @Override
            protected void onSuccess(JsonT<YsConfig> data) {
                if (data.isSuccess()) {
                    initVideo(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<YsConfig> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

    void initVideo(YsConfig ysConfig) {
        EZUIKit.initWithAppKey(getApplication(), ysConfig.getAppKey());
        EZUIKit.setAccessToken(ysConfig.getAccessToken());
        String url = "ezopen://" + "open.ezviz.com/" + cameraBean.getSeriesNumber() + "/" + cameraBean.getChannelNo() + ".live";
        playerUi.setUrl(url);
        playerUi.startPlay();
    }
}
