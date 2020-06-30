package com.zz.lamp.business.alarm;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.business.alarm.adapter.AlarmAdapter;
import com.zz.lamp.business.control.adapter.ControlCameraAdapter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class LeftFragment extends MyBaseFragment implements OnRefreshListener, OnLoadMoreListener {
    List<AlarmBean> mlist = new ArrayList<>();
    AlarmAdapter adapter;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int pageNum = 1;
    int pageSize = 20;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_click)
    LinearLayout searchClick;
    String searchValue;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    Unbinder unbinder;

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
    protected int getCreateView() {
        return R.layout.fragment_left;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AlarmAdapter(mlist);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivityForResult(new Intent(getActivity(),AlarmHandleActivity.class).putExtra("id",mlist.get(position).getId()),1001);
            }
        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    searchValue = v.getText().toString();
                    hideKeyboard(searchEdit);
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
    public void onError() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getData();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getData();
        refreshLayout.finishLoadMore();
    }

    public void showIntent(List<AlarmBean> list) {
        if (list == null) return;
        if (pageNum == 1) {
            mlist.clear();
        }
        formatData(list);

    }

    public void formatData(List<AlarmBean> list) {
        List<AlarmBean> timeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                if (mlist.size() > 0) {
                    if (!mlist.get(mlist.size() - 1).getUpdateTime().equals(list.get(i).getUpdateTime())) {
                        timeList.add(new AlarmBean(1, list.get(i).getUpdateTime()));
                    }
                } else {
                    timeList.add(new AlarmBean(1, list.get(i).getUpdateTime()));
                }
                list.get(i).setItemType(2);
                timeList.add(list.get(i));
            } else {
                if (!list.get(i).getUpdateTime().equals(list.get(i - 1).getUpdateTime())) {
                    timeList.add(new AlarmBean(1, list.get(i).getUpdateTime()));
                }
                list.get(i).setItemType(2);
                timeList.add((list.get(i)));
            }
        }

        mlist.addAll(timeList);
        adapter.notifyDataSetChanged();
        if (mlist.size() > 0) {
            llNull.setVisibility(View.GONE);
        } else {
            llNull.setVisibility(View.VISIBLE);
        }
    }

    void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("status", 0);
        if (!TextUtils.isEmpty(searchValue)) {
            map.put("searchValue", searchValue);
        }
        RxNetUtils.request(getCApi(ApiService.class).getAlarmList(map), new RequestObserver<JsonT<List<AlarmBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<AlarmBean>> data) {
                if (data.isSuccess()) {
                    showIntent(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<AlarmBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
        refreshLayout.finishRefresh();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==-1){
//            pageNum = 1;
//            getData();
//            refreshLayout.finishRefresh();
//        }
//    }
}
