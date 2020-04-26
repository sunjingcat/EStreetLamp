package com.lmx.friends.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lmx.friends.net.OutDateEvent;
import com.lmx.lib.core.ui.BaseFragment;
import com.lmx.lib.core.utils.AnimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by admin on 2018/5/17.
 */

public abstract class MyBaseFragment<P extends  com.lmx.lib.core.ui.mvp.BasePresenter> extends BaseFragment<P> {
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
}
