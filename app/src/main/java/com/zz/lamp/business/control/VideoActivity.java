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
import com.zz.lamp.bean.RealTimeCtrlGroup;
import com.zz.lamp.bean.YsConfig;
import com.zz.lamp.business.control.mvp.Contract;
import com.zz.lamp.business.control.mvp.presenter.VideoControlPresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

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
        final int[] lastPosition = {100};
        dlRmv.setOnMenuTouchListener(new OnMenuTouchListener() {
            @Override
            public void OnTouch(MotionEvent event,int position) {
              if(lastPosition[0] == position)  {
                  return;
              }else {
                  control(position);

              }
              lastPosition[0] = position;
                LogUtils.v("sj--",position+"");
            }
        });
    }
    private void control(int position){
       if (cameraBean==null){
           return;
       }
//        操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
        Map<String,Object> map = new HashMap<>();
        map.put("id",cameraBean.getId());
        if (position==-1){
//            map.put("direction",0);
//            mPresenter.shopControl(map);
        }if (position==0){
            map.put("direction",0);
            mPresenter.shopControl(map);
        }if (position==1){
            map.put("direction",3);
            mPresenter.shopControl(map);
        }if (position==2){
            map.put("direction",1);
            mPresenter.shopControl(map);
        }if (position==3){
            map.put("direction",2);
            mPresenter.shopControl(map);
        }else if (position==-2){
            map.put("direction",0);
            mPresenter.shopControl(map);
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
