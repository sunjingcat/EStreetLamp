package com.zz.lamp.business.entry;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.troila.customealert.CustomDialog;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.bean.LightDetailBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.business.alarm.adapter.ImageItemAdapter;
import com.zz.lamp.business.entry.adapter.LightDetailAdapter;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.LampDetailPresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.BASE64;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

/**
 * 灯控器详情
 */
public class LightDetailActivity extends MyBaseActivity<Contract.IsetLampDetailPresenter> implements Contract.IGeLampDetailView {
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter imageItemAdapter;
    @BindView(R.id.rv_images_annex)
    RecyclerView rvImagesAnnex;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bg)
    LinearLayout bg;
    LightDetailAdapter adapter;
    List<LightDetailBean> mlist = new ArrayList<>();
    String lightId;
    @Override
    protected int getContentView() {
        return R.layout.activity_light_detail;
    }
    LightDevice lightDevice;
    @Override
    public LampDetailPresenter initPresenter() {
        return new LampDetailPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new LightDetailAdapter(R.layout.item_detail_light, mlist);
        rv.setAdapter(adapter);
         lightId = getIntent().getStringExtra("lightId");
        rvImagesAnnex.setLayoutManager(new GridLayoutManager(this, 3));
        imageItemAdapter = new ImageItemAdapter(R.layout.item_image, images);
        rvImagesAnnex.setAdapter(imageItemAdapter);

        mPresenter.getLightDetail(lightId);
    }


    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (lightDevice == null) return;
                startActivityForResult(new Intent(this, EntryLampActivity.class).putExtra("terminalId", lightDevice.getTerminalId()).putExtra("device", lightDevice).putExtra("id",lightDevice.getId()),1001);
                break;
            case R.id.delete:
                showDeleteDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1001){
            mPresenter.getLightDetail(lightId);
            mPresenter.getImage("lightDevice",lightDevice.getId());
        }
    }

    private CustomDialog customDialog;
    private void showDeleteDialog() {
        if (lightDevice == null) return;
        CustomDialog.Builder builder = new com.troila.customealert.CustomDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除设备")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteLight(lightId);
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

    @Override
    public void showDeleteIntent() {
        finish();
    }

    @Override
    public void showImage(List<ImageBack> list) {
        dismissLoading();
        if (list == null) return;

        showLoading("");
        List<String> showList = new ArrayList<>();
        for (ImageBack imageBack : list) {
            String bitmapName = "termial_"+imageBack.getId()+".jpg";
            String path = getCacheDir() + "/zhongzhi/light/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                showList.add(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                showList.add(s);
            }

        }
        images.clear();
        images.addAll(showList);
        imageItemAdapter.notifyDataSetChanged();
        dismissLoading();
    }

    @Override
    public void showLightDetail(LightDevice lightDevice) {
        if (lightDevice == null) return;
        this.lightDevice  = lightDevice;
        mlist.clear();
        mlist.add(new LightDetailBean("路灯控制器地址", lightDevice.getDeviceAddr() + ""));
        mlist.add(new LightDetailBean("支路", lightDevice.getLineName() + ""));
        mlist.add(new LightDetailBean("路灯控制器编号", lightDevice.getDeviceCode() + ""));
        mlist.add(new LightDetailBean("路灯控制器别名", lightDevice.getDeviceName() + ""));
        mlist.add(new LightDetailBean("安装时间", lightDevice.getLightInstallTime() + ""));

        mlist.add(new LightDetailBean("路杆编号", lightDevice.getLightPoleCode() + ""));
        mlist.add(new LightDetailBean("路杆类型", lightDevice.getLightPoleTypeText() + ""));
        mlist.add(new LightDetailBean("路杆高度(米)", lightDevice.getLightPoleHeight() + ""));
        mlist.add(new LightDetailBean("灯头类型", lightDevice.getLightTypeText() + ""));

        mlist.add(new LightDetailBean("路灯类型", lightDevice.getDeviceTypeText() + ""));
        mlist.add(new LightDetailBean("主灯类型", lightDevice.getLightMainTypeName() + ""));
        mlist.add(new LightDetailBean("主灯额定功率(W)", lightDevice.getLightMainPower() + ""));
        mlist.add(new LightDetailBean("主灯功率阈值(W)", lightDevice.getLightMainPowerLimit() + ""));

        if (lightDevice.getDeviceType() == 2) {
            mlist.add(new LightDetailBean("辅灯类型", lightDevice.getLightAuxiliaryTypeName() + ""));
            mlist.add(new LightDetailBean("辅灯额定功率(W)", lightDevice.getLightAuxiliaryPower() + ""));
            mlist.add(new LightDetailBean("辅灯功率阈值(W)", lightDevice.getLightAuxiliaryPowerLimit() + ""));
        }
        adapter.notifyDataSetChanged();
        mPresenter.getImage("lightDevice",lightId);
        showLoading("");
    }

}
