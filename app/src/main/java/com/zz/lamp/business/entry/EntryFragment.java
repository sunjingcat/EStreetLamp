package com.zz.lamp.business.entry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.business.entry.adapter.ConcentratorBeanAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.TerminalPresenter;
import com.zz.lib.core.ui.widget.decorations.RecycleViewDivider;

import java.util.ArrayList;
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
import butterknife.Unbinder;

/**
 * 录入首页
 */
public class EntryFragment  extends MyBaseFragment<Contract.IsetTerminalPresenter> implements Contract.IGetTerminalView, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    ConcentratorBeanAdapter adapter;
    List<ConcentratorBean> mlist = new ArrayList<>();
    Unbinder unbinder;
    int pageNum = 1;
    int pageSize = 20;
    String searchValue;
    @Override
    protected int getCreateView() {
        return R.layout.fragment_entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new ConcentratorBeanAdapter(R.layout.item_entry_jzq, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getActivity(),LampListActivity.class).putExtra("terminalId",mlist.get(position).getId()));
            }
        });
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(searchEdit);
                    //点击搜索的时候隐藏软键盘
                    searchValue = v.getText().toString();
                    if (!TextUtils.isEmpty(searchValue)) {
                        pageNum = 1;
                        getData();
                    }
                    // 在这里写搜索的操作,一般都是网络请求数据
                    return true;
                }

                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    searchValue = "";
                    pageNum = 1;
                    getData();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
    }

    @Override
    public TerminalPresenter initPresenter() {
        return new TerminalPresenter(this);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 1;
        getData();
        refreshlayout.finishRefresh();
    }


    @Override
    public void onLoadMore(RefreshLayout refresh) {
        pageNum++;
        getData();
        refresh.finishLoadMore();
    }

    @OnClick({R.id.entry_qy, R.id.entry_jzq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_qy:
                getActivity().startActivity(new Intent(getActivity(),RegionActivity.class).putExtra("shouldBack",1));
                break;
            case R.id.entry_jzq:
                getActivity().startActivityForResult(new Intent(getActivity(),EntryJzqActivity.class),1001);
                break;
        }
    }
    @Override
    public void showIntent(List<ConcentratorBean> list) {
        if (list==null)return;
        if (pageNum ==1){
            mlist.clear();
        }
        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        if (mlist.size()>0){
            llNull.setVisibility(View.GONE);
        }else {
            llNull.setVisibility(View.VISIBLE);
        }
    }
    void getData(){
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        if (!TextUtils.isEmpty(searchValue)){
            map.put("searchValue", searchValue);
        }
        mPresenter.getTerminalList(map);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1) {
            pageNum = 1;
            getData();
        }
    }
}
