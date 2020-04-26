package com.lmx.friends.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lmx.friends.App;
import com.lmx.friends.net.OutDateEvent;
import com.lmx.friends.utils.woolglass.FragmentClass;
import com.lmx.lib.commonlib.CommonApplication;
import com.lmx.lib.commonlib.utils.CacheUtility;
import com.lmx.lib.commonlib.utils.IToast;
import com.lmx.lib.core.ui.BaseActivity;
import com.lmx.lib.core.utils.AnimeUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by admin on 2018/5/14.
 */

public abstract class MyBaseActivity<P extends com.lmx.lib.core.ui.mvp.BasePresenter> extends BaseActivity<P> {
    private Fragment lastFragment;
    private String firstFragment;
    private Bundle mTransAnimation;
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        CommonApplication.activity = this;
        App.context=this;

    }
    //获取Activity布局
    protected abstract int getContentView();
    public void clearTransAnimation() {
        mTransAnimation = null;
    }

    public void setTransAnimation(Bundle transAnimation) {
        mTransAnimation = transAnimation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mTransAnimation = AnimeUtils.sceneTransAnime(this);
        initView();
        initToolBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutDate(OutDateEvent event) {
        onOutDatePreExcuted();
        CacheUtility.saveToken("");
//        Intent intent = new Intent();
//        intent.setClass(this, LoginActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("drawback", true);
//        startActivity(intent);
        IToast.show(this,"跳登录");
    }

    protected void onOutDatePreExcuted() {

    }

    //初始化视图
    protected abstract void initView();

    //初始化标题栏
    protected abstract void initToolBar();
    @Override
    public void finish() {
        super.finish();
        AnimeUtils.sceneTransAnimeExit(this);
    }


    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT < 16 || mTransAnimation == null) {
            super.startActivity(intent);
        } else {
            ActivityCompat.startActivity(this, intent, mTransAnimation);
        }
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (Build.VERSION.SDK_INT < 16 || mTransAnimation == null) {
            super.startActivityForResult(intent, requestCode);
        } else {
            ActivityCompat.startActivityForResult(this, intent, requestCode, mTransAnimation);
        }
    }


    protected Fragment setContentView(Class<? extends Fragment> fragmentClass, int FrameLayoutId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String fragmentName = fragmentClass.getSimpleName();
        Fragment fragment = (Fragment) fragmentManager.findFragmentByTag(fragmentName);

        try {
            if (fragment == null) {
                fragment = fragmentClass.newInstance();
                transaction.add(FrameLayoutId, fragment, fragmentName);
                firstFragment = FragmentClass.newInstance();
            }
            if (lastFragment != null)
                transaction.hide(lastFragment);
            transaction.show(fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        lastFragment = fragment;
        transaction.commit();
        return fragment;
    }

    @Override
    public void onError() {

    }
}
