package com.zz.lamp.business.entry;

import android.content.DialogInterface;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.adapter.RegionAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.RegionPresenter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.widget.InputDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegionActivity extends MyBaseActivity<Contract.IsetRegionPresenter> implements Contract.IGetRegionlView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    RegionAdapter adapter;
    private InputDialog customDialog;
    BaseNode selectNode;

    @Override
    protected int getContentView() {
        return R.layout.activity_region;
    }

    //    https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/6-BaseNodeAdapter.md
    @Override
    public RegionPresenter initPresenter() {
        return new RegionPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegionAdapter();
        rv.setAdapter(adapter);
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent(List<RegionExpandItem> list) {
        if (list == null) return;
        adapter.addData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPostIntent() {

    }

    void getData() {
        Map<String, Object> map = new HashMap<>();
//        map.put("searchValue",pageSize);
        mPresenter.getAreaList(map);
    }
    void postData(BaseNode node,String str) {
        Map<String, Object> map = new HashMap<>();
        map.put("areaName",str);
        map.put("areaPid",(RegionExpandItem)node);
        mPresenter.getAreaList(map);
    }

    private void showInputDialog(BaseNode node) {
        InputDialog.Builder builder = new InputDialog.Builder(RegionActivity.this)
                .setTitle("提示")
                .setMessage("")
                .setNegativeButton("取消", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();
                        selectNode = null;
                    }
                })
                .setPositiveButton("确定", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();
                        LogUtils.v("sj---" + msg);
                        LogUtils.v("sj---" + node.toString());

                    }
                });
        selectNode = node;
        customDialog = builder.create();
        customDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }



    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        showInputDialog(new RegionExpandItem(1,""));
    }
}
