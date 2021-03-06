package com.zz.lamp.business.control;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlong.rep.dlroundmenuview.DLRoundMenuView;
import com.dlong.rep.dlroundmenuview.Interface.OnMenuTouchListener;
import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.YsConfig;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.VideoControlPresenter;
import com.zz.lamp.utils.LogUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 监控控制
 */
public class VideoActivity extends MyBaseActivity<Contract.IsetVideoControlPresenter> implements Contract.IGetVideoControlView {

    @BindView(R.id.player_ui)
    EZUIPlayer playerUi;
    CameraBean cameraBean;
    @BindView(R.id.dl_rmv)
    DLRoundMenuView dlRmv;
    @BindView(R.id.video_add)
    ImageView videoAdd;
    @BindView(R.id.video_cut)
    ImageView videoCut;
    int lastPosition = 100;
    @BindView(R.id.video_zoom)
    TextView videoZoom;
    @BindView(R.id.video_zoomOut)
    TextView videoZoomOut;
    @BindView(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected int getContentView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return R.layout.activity_video;
    }


    @Override
    public VideoControlPresenter initPresenter() {
        return new VideoControlPresenter(this);
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
        mPresenter.getYsConfig();
        dlRmv.setOnMenuTouchListener(new OnMenuTouchListener() {
            @Override
            public void OnTouch(MotionEvent event, int position) {
                int realPosition=100;
                if (position == -1) {

                }
                if (position == 0) {
                    realPosition=0;
                } else if (position == 1) {
                    realPosition=3;
                } else if (position == 2) {
                    realPosition=1;
                } else if (position == 3) {
                    realPosition=2;
                } else if (position == -2) {
                    realPosition=-2;
                }
                control(realPosition);
                lastPosition = realPosition;

            }
        });
        videoAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        control(8);
                        lastPosition = 8;
                        break;
                    case MotionEvent.ACTION_UP:
                        control(-2);
                        lastPosition = -2;
                        break;
                }
                return true;
            }
        });
        videoCut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        control(9);
                        lastPosition = 9;
                        break;
                    case MotionEvent.ACTION_UP:
                        control(-2);
                        lastPosition = -2;
                        break;
                }
                return true;
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void control(int position) {
        if (cameraBean == null) {
            return;
        }
        if (lastPosition == position)return;
//        操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
        Map<String, Object> map = new HashMap<>();
        map.put("id", cameraBean.getId());
        map.put("channelNo", cameraBean.getChannelNo());
        map.put("deviceSerial", cameraBean.getSeriesNumber());
        LogUtils.v("sj---",position+","+lastPosition);
        if (position == -2) {
            map.put("direction", lastPosition);
            LogUtils.v("sj---lastPosition",""+lastPosition);
            mPresenter.shopControl(map);
        }else {
            map.put("direction", position);
            LogUtils.v("sj---position",""+position);
            mPresenter.startControl(map);
        }
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


    @Override
    public void showYsConfig(YsConfig ysConfig) {
        initVideo(ysConfig);
    }

    @Override
    public void showIntent() {

    }

}
