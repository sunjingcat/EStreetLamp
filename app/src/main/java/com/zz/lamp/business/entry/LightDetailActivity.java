package com.zz.lamp.business.entry;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.RealTimeCtrlTerminal;
import com.zz.lamp.business.entry.adapter.LightDetailAdapter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class LightDetailActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bg)
    LinearLayout bg;
    LightDetailAdapter adapter ;
    List<LightDetailBean> mlist = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_light_detail;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new LightDetailAdapter(R.layout.item_detail_light,mlist);
        rv.setAdapter(adapter);
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }
    void getData() {
        String lightId = getIntent().getStringExtra("lightId");
        RxNetUtils.request(getCApi(ApiService.class).getLampDetail(lightId), new RequestObserver<JsonT<LightDevice>>(this) {
            @Override
            protected void onSuccess(JsonT<LightDevice> data) {
                if (data.isSuccess()) {
                    showIntent(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<LightDevice> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
    public void showIntent(LightDevice lightDevice) {
        if (lightDevice == null) return;
        mlist.clear();
        mlist.add(new LightDetailBean("路灯控制器地址",lightDevice.getDeviceAddr()+""));
        mlist.add(new LightDetailBean("支路",lightDevice.getLineName()+""));
        mlist.add(new LightDetailBean("路灯控制器编号",lightDevice.getDeviceCode()+""));
        mlist.add(new LightDetailBean("路灯控制器别名",lightDevice.getDeviceName()+""));
        mlist.add(new LightDetailBean("安装时间",lightDevice.getLightInstallTime()+""));

        mlist.add(new LightDetailBean("路杆编号",lightDevice.getLightPoleCode()+""));
        mlist.add(new LightDetailBean("路杆类型",lightDevice.getLightPoleType()+""));
        mlist.add(new LightDetailBean("路杆高度",lightDevice.getLightPoleHeight()+""));
        mlist.add(new LightDetailBean("灯头类型",lightDevice.getLightType()+""));

        mlist.add(new LightDetailBean("路灯类型",lightDevice.getDeviceType()+""));
        mlist.add(new LightDetailBean("主灯类型",lightDevice.getLightMainTypeName()+""));
        mlist.add(new LightDetailBean("主灯额定功率(W)",lightDevice.getLightMainPower()+""));
        mlist.add(new LightDetailBean("主灯功率阈值(W)",lightDevice.getLightMainPowerLimit()+""));

         if (lightDevice.getDeviceType()==2){
             mlist.add(new LightDetailBean("辅灯类型",lightDevice.getLightAuxiliaryTypeName()+""));
             mlist.add(new LightDetailBean("辅灯额定功率(W)",lightDevice.getLightAuxiliaryPower()+""));
             mlist.add(new LightDetailBean("辅灯功率阈值(W)",lightDevice.getLightAuxiliaryPowerLimit()+""));
        }
         adapter.notifyDataSetChanged();

    }
}
