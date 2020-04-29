package com.zz.lamp.business.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.mvp.Contract;

import java.util.List;


public class EntryJzqActivity extends MyBaseActivity<Contract.IsetRegionPresenter> implements Contract.IGetRegionlView {

    @Override
    protected int getContentView() {
        return R.layout.activity_entry_jzq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_jzq);
    }

    @Override
    public Contract.IsetRegionPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void showIntent(List<RegionExpandItem> list) {

    }
}
