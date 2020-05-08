package com.zz.lamp.business.control;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends MyBaseActivity {

    @BindView(R.id.player_ui)
    EZUIPlayer playerUi;

    @Override
    protected int getContentView() {
        return R.layout.activity_video;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EZUIKit.setAccessToken("at.acfhokxvbalxqagicf0folue1u00nmkx-4cj3d6z143-1cl9mfi-w5imfmvgp");
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

            }

            @Override
            public void onPlayFail(EZUIError ezuiError) {

            }

            @Override
            public void onVideoSizeChange(int i, int i1) {

            }

            @Override
            public void onPrepared() {

            }

            @Override
            public void onPlayTime(Calendar calendar) {

            }

            @Override
            public void onPlayFinish() {

            }
        });
//        playerUi.setUrl();
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

}
