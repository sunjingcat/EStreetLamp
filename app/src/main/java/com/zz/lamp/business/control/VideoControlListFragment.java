package com.zz.lamp.business.control;

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
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.business.control.adapter.ControlCameraAdapter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zz.lamp.net.RxNetUtils.getCApi;
/**
 * 监控首页
 */
public class VideoControlListFragment extends MyBaseFragment implements OnRefreshListener, OnLoadMoreListener {
    List<CameraBean> mlist = new ArrayList<>();
    ControlCameraAdapter adapter;
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
    public VideoControlListFragment() {
        // Required empty public constructor
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
        return R.layout.fragment_video_control_list;
    }

    @Override
    protected void initView(View view) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ControlCameraAdapter(R.layout.item_simple, mlist);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getActivity(),VideoActivity.class).putExtra("camera",mlist.get(position)));
            }
        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        getData();
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(searchEdit);
                    searchValue = v.getText().toString();
                    pageNum = 1;
                    getData();
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
    public BasePresenter initPresenter() {
        return null;
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

    public void showIntent(List<CameraBean> list) {
        if (list == null) return;
        if (pageNum == 1) {
            mlist.clear();
        }
        mlist.addAll(list);
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
        if (!TextUtils.isEmpty(searchValue)){
            map.put("searchValue", searchValue);
        }
        if (beLogin()) return;
        RxNetUtils.request(getCApi(ApiService.class).getCameraDevicelist(map), new RequestObserver<JsonT<List<CameraBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<CameraBean>> data) {
                if (data.isSuccess()) {
                    showIntent(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<CameraBean>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, null);
    }

}
