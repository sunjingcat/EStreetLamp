package com.zz.lamp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zz.lamp.net.OutDateEvent;
import com.zz.lamp.utils.woolglass.FragmentClass;
import com.zz.lib.core.ui.BaseFragment;
import com.zz.lib.core.utils.AnimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by admin on 2018/5/17.
 */

public abstract class MyBaseFragment<P extends  com.zz.lib.core.ui.mvp.BasePresenter> extends BaseFragment<P> {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getCreateView(), container, false);

    }
    protected abstract int getCreateView();

    protected abstract void initView(View view);




    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT < 16) {
            super.startActivity(intent);
        } else {
            ActivityCompat.startActivity(getContext(), intent, AnimeUtils.sceneTransAnime(getContext()));
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (Build.VERSION.SDK_INT < 16) {
            super.startActivityForResult(intent, requestCode);
        } else {
            ActivityCompat.startActivityForResult(getActivity(), intent, requestCode, AnimeUtils.sceneTransAnime(getContext()));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutDate(OutDateEvent event) {
        onOutDatePreExcuted();

    }

    protected void onOutDatePreExcuted() {

    }
    private Fragment lastFragment;
    private String firstFragment;
    protected Fragment setContentView(FragmentActivity activity, Class<? extends Fragment> fragmentClass, int FrameLayoutId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
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
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        lastFragment = fragment;
        transaction.commit();
        return fragment;
    }
}
