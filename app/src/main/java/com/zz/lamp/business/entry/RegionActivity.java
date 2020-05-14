package com.zz.lamp.business.entry;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.RegionExpandItem1;
import com.zz.lamp.bean.RegionExpandItem2;
import com.zz.lamp.bean.RegionExpandItem3;
import com.zz.lamp.business.entry.adapter.RegionAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.RegionPresenter;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.SoftKeyboardUtils;
import com.zz.lamp.widget.InputDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
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
    int shouldBack;
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
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,1, Color.parseColor("#EDEFF7")));
        shouldBack = getIntent().getIntExtra("shouldBack",0);
        adapter = new RegionAdapter(new RegionAdapter.OnProviderOnClick() {

            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (type == 1){
                    showInputDialog(node);
                }else{
                    if (shouldBack==1)return;
                    Intent intent = new Intent();
                    String areaPid="";
                    String userId="";
                    int orderNum=1;
                    if (node instanceof RegionExpandItem) {
                        RegionExpandItem node1 = (RegionExpandItem) node;
                        areaPid=node1.getAreaPid();
                        orderNum=node1.getOrderNum()+1;
                        userId=node1.getUserId();
                        intent.putExtra("areaId",node1.getId());
                        intent.putExtra("areaName",node1.getAreaName());
                    } else if (node instanceof RegionExpandItem1) {
                        RegionExpandItem1 node1 = (RegionExpandItem1) node;
                        areaPid=node1.getAreaPid();
                        orderNum=node1.getOrderNum()+1;
                        userId=node1.getUserId();
                        intent.putExtra("areaId",node1.getId());
                        intent.putExtra("areaName",node1.getAreaName());
                    } else if (node instanceof RegionExpandItem2) {
                        RegionExpandItem2 node1 = (RegionExpandItem2) node;
                        areaPid=node1.getAreaPid();
                        orderNum=node1.getOrderNum()+1;
                        userId=node1.getUserId();
                        intent.putExtra("areaId",node1.getId());
                        intent.putExtra("areaName",node1.getAreaName());
                    }else if (node instanceof RegionExpandItem3) {
                        RegionExpandItem3 node1 = (RegionExpandItem3) node;
                        areaPid=node1.getAreaPid();
                        orderNum=node1.getOrderNum()+1;
                        userId=node1.getUserId();
                        intent.putExtra("areaId",node1.getId());
                        intent.putExtra("areaName",node1.getAreaName());
                    }

                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });
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
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPostIntent() {
        showToast("添加成功");
        getData();
    }

    void getData() {
        Map<String, Object> map = new HashMap<>();
//        map.put("searchValue",pageSize);
        mPresenter.getAreaList(map);
    }
    void postData(BaseNode node,String str) {
        String areaPid="";
        String userId="";
        int orderNum=1;
        if (node instanceof RegionExpandItem) {
            RegionExpandItem node1 = (RegionExpandItem) node;
            areaPid=node1.getId();
            orderNum=node1.getOrderNum()+1;
            userId=node1.getUserId();
        } else if (node instanceof RegionExpandItem1) {
            RegionExpandItem1 node1 = (RegionExpandItem1) node;
            areaPid=node1.getId();
            orderNum=node1.getOrderNum()+1;
            userId=node1.getUserId();
        } else if (node instanceof RegionExpandItem2) {
            RegionExpandItem2 node1 = (RegionExpandItem2) node;
            areaPid=node1.getId();
            orderNum=node1.getOrderNum()+1;
            userId=node1.getUserId();
        }else if (node instanceof RegionExpandItem3) {
            RegionExpandItem3 node1 = (RegionExpandItem3) node;
            areaPid=node1.getId();
            orderNum=node1.getOrderNum()+1;
            userId=node1.getUserId();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("areaName",str);
        map.put("areaPid", TextUtils.isEmpty(areaPid)?"":areaPid);
        map.put("orderNum",orderNum);
//        LogUtils.v("sj------",map.toString());
//        map.put("userId",TextUtils.isEmpty(userId)?"":userId);
        mPresenter.postArea(map);
    }

    private void showInputDialog(BaseNode node) {
        InputDialog.Builder builder = new InputDialog.Builder(RegionActivity.this)
                .setTitle("区域名")
                .setMessage("")
                .setNegativeButton("取消", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();
                        selectNode = null;
                        SoftKeyboardUtils.closeInoutDecorView(RegionActivity.this);
                    }
                })
                .setPositiveButton("确定", new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, String msg) {
                        dialog.dismiss();

                        postData(node,msg);

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
        showInputDialog(new RegionExpandItem(0,""));
    }
}
