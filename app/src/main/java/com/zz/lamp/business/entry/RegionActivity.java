package com.zz.lamp.business.entry;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.RegionExpandItem1;
import com.zz.lamp.bean.RegionExpandItem2;
import com.zz.lamp.business.entry.adapter.RegionAdapter;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegionActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    RegionAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_region;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        List<BaseNode> entity = getEntity();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegionAdapter();
        rv.setAdapter(adapter);

        adapter.addData(entity);
        adapter.notifyDataSetChanged();

    }

    private List<BaseNode> getEntity() {
        //总的 list，里面放的是 FirstNode
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            //SecondNode 的 list
            List<BaseNode> secondNodeList = new ArrayList<>();
            for (int n = 0; n <= 5; n++) {
                List<BaseNode> thirdNodeList = new ArrayList<>();
                for (int z = 0; z <= 3; z++) {
                    RegionExpandItem2 thNode = new RegionExpandItem2("third Node " + z);
                    thirdNodeList.add(thNode);
                }
                RegionExpandItem1 seNode = new RegionExpandItem1("Second Node " + n,thirdNodeList);
                secondNodeList.add(seNode);
            }
            RegionExpandItem entity = new RegionExpandItem( "First Node " + i,secondNodeList);
            list.add(entity);
        }

        return list;
    }


    @Override
    protected void initToolBar() {

    }
}
