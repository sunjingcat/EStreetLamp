package com.zz.lamp.business.entry;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.troila.customealert.CustomDialog;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.adapter.LineAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LinePresenter;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.http.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LineActivity extends MyBaseActivity<Contract.IsetLinePresenter> implements Contract.IGetLineView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    LineAdapter adapter;
    List<LineBean> mlist = new ArrayList<>();
    String terminalId;
    String[] UsableCode;
    int shouldBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_line;
    }

    @Override
    public LinePresenter initPresenter() {
        return new LinePresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
        shouldBack = getIntent().getIntExtra("shouldBack", 0);
        if (shouldBack==1){
            toolbarSubtitle.setText("新建");
        }else {
            toolbarSubtitle.setText("编辑");
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LineAdapter(R.layout.item_simple, mlist,shouldBack);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (shouldBack == 1) return;
                Intent intent = new Intent();
                intent.putExtra("lineId", mlist.get(position).getId() + "");
                intent.putExtra("lineName", mlist.get(position).getLineName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        adapter.setOnClickListner(new LineAdapter.OnClickListener() {
            @Override
            public void onEditClick(int position) {
                Intent intent = new Intent(LineActivity.this, AddLineActivity.class);
                intent.putExtra("lineName", mlist.get(position).getLineName());
                intent.putExtra("code", mlist.get(position).getLineCode()+"");
                intent.putExtra("lineId", mlist.get(position).getId()+"");
                intent.putExtra("UsableCode", UsableCode);
                startActivityForResult(intent, 1004);
            }

            @Override
            public void onDeleteClick(int position) {
                showDeleteDialog(mlist.get(position).getId() + "");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showIntent(List<LineBean> list) {
        mlist.clear();
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        if (mlist.size() > 0) {
            llNull.setVisibility(View.GONE);
        } else {
            llNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUsableCode(String[] list) {
        if (list == null) return;
        UsableCode = list;
    }

    @Override
    public void showPostIntent() {
        ToastUtils.showToast("操作成功");
        getData();
    }

    @Override
    public void showDeleteIntent() {
        ToastUtils.showToast("操作成功");
        getData();
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (shouldBack==1) {
                    startActivityForResult(new Intent(this, AddLineActivity.class).putExtra("UsableCode", UsableCode), 1003);
                }else {
                    startActivity(new Intent(this,LineActivity.class).putExtra("shouldBack",1).putExtra("terminalId",terminalId));
                }

                break;
        }
    }

    void getData() {
        mPresenter.getLineList(terminalId);
        mPresenter.getUsableCode(terminalId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String code = data.getStringExtra("code");
            String lineId = data.getStringExtra("lineId");
            switch (requestCode) {
                case 1003:
                    postData(name, code,null);
                    break;
                case 1004:
                    postData(name, code,lineId);
                    break;
                default:

            }
        }
    }

    void postData(String lineName, String lineCode, String lineId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lineCode", lineCode + "");
        map.put("lineName", lineName + "");
        map.put("terminalId", terminalId);
        if (!TextUtils.isEmpty(lineId)){
            map.put("id", lineId);
        }
        mPresenter.postLine(map);
    }
    private CustomDialog customDialog;
    private void showDeleteDialog(String id) {
        CustomDialog.Builder builder = new com.troila.customealert.CustomDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除支路")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteLine(id);
                    }
                });
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
}
