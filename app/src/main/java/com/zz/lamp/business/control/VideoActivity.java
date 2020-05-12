package com.zz.lamp.business.control;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dlong.rep.dlroundmenuview.DLRoundMenuView;
import com.dlong.rep.dlroundmenuview.Interface.OnMenuClickListener;
import com.dlong.rep.dlroundmenuview.Interface.OnMenuLongClickListener;
import com.dlong.rep.dlroundmenuview.Interface.OnMenuTouchListener;
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

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class VideoActivity extends MyBaseActivity {

    @BindView(R.id.player_ui)
    EZUIPlayer playerUi;
    CameraBean cameraBean;
    @BindView(R.id.dl_rmv)
    DLRoundMenuView dlRmv;
    @BindView(R.id.video_add)
    ImageView videoAdd;
    @BindView(R.id.video_cut)
    ImageView videoCut;

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

        dlRmv.setOnMenuTouchListener(new OnMenuTouchListener() {
            @Override
            public void OnTouch(MotionEvent event,int position) {
                LogUtils.v("sj--",position+"");
            }
        });
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
        String url = "ezopen://" + "open.ys7.com/" + cameraBean.getSeriesNumber() + "/" + cameraBean.getChannelNo() + ".live";
        playerUi.setUrl(url);
        playerUi.startPlay();

    }

    @OnClick({R.id.video_add, R.id.video_cut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_add:
                break;
            case R.id.video_cut:
                break;
        }
    }
}
