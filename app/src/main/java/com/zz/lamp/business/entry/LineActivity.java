package com.zz.lamp.business.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.InfoActivity;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.UsableCode;
import com.zz.lamp.business.entry.adapter.ConcentratorBeanAdapter;
import com.zz.lamp.business.entry.adapter.LineAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LinePresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;
import com.zz.lib.core.utils.LoadingUtils;

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

import static com.zz.lamp.net.RxNetUtils.getCApi;
import static com.zz.lib.core.http.utils.ToastUtils.showToast;

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
        shouldBack = getIntent().getIntExtra("shouldBack",0);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LineAdapter(R.layout.item_simple, mlist);
        rv.setAdapter(adapter);
        getData();
        mPresenter.getUsableCode(terminalId);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (shouldBack ==1)return;
                Intent intent = new Intent();
                intent.putExtra("lineId",mlist.get(position).getId());
                intent.putExtra("lineName",mlist.get(position).getLineName());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
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
        if (mlist.size()>0){
            llNull.setVisibility(View.GONE);
        }else {
            llNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUsableCode(String[] list) {
        if (list==null)return;
        UsableCode = list;
    }

    @Override
    public void showPostIntent() {
        showToast("新建成功");
        getData();
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddLineActivity.class).putExtra("UsableCode",UsableCode),1003);
                break;
        }
    }
    void getData(){
        mPresenter.getLineList(terminalId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK&&data!=null){
            switch (requestCode){
                case 1003:
                    //TODO 接口错误
                    String name = data.getStringExtra("name");
                    String code = data.getStringExtra("code");
                    Map<String,Object> map = new HashMap<>();
                    map.put("lineCode",code);
                    map.put("lineName",name);
                    map.put("terminalId",terminalId);
                    mPresenter.postLine(map);
                    break;
            }
        }
    }
}
