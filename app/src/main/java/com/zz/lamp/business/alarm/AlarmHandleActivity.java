package com.zz.lamp.business.alarm;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.TestPost;
import com.zz.lamp.business.alarm.adapter.ImageDeleteItemAdapter;
import com.zz.lamp.business.alarm.mvp.Contract;
import com.zz.lamp.business.alarm.mvp.presenter.AlarmAddPresenter;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.BASE64;
import com.zz.lib.commonlib.utils.ToolBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;

public class AlarmHandleActivity extends MyBaseActivity<Contract.IsetAlarmAddPresenter> implements Contract.IGetAlarmAddView {
    ArrayList<String> imagesAnnex = new ArrayList<>();
    ImageDeleteItemAdapter adapterAnnex;
    @BindView(R.id.rv_images_annex)
    RecyclerView rvImagesAnnex;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.alarm_name)
    TextView alarmName;
    @BindView(R.id.alarm_des)
    TextView alarmDes;
    @BindView(R.id.alarm_time)
    TextView alarmTime;
    @BindView(R.id.alarm_address)
    TextView alarm_address;
    @BindView(R.id.alarm_content)
    EditText alarmContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_alarm_handle;
    }

    @Override
    public AlarmAddPresenter initPresenter() {
        return new AlarmAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rvImagesAnnex.setLayoutManager(new GridLayoutManager(this, 3));
        adapterAnnex = new ImageDeleteItemAdapter(this, imagesAnnex);
        rvImagesAnnex.setAdapter(adapterAnnex);
        adapterAnnex.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {

                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(4) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(imagesAnnex) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AlarmHandleActivity.this, 1102); // 打开相册

            }

            @Override
            public void onclickDelete(View v, int option) {
                imagesAnnex.remove(option);
                adapterAnnex.notifyDataSetChanged();
            }
        });
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        mPresenter.getData(map);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1102 && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if (images.size() > 0) {
                this.imagesAnnex.clear();
            }
            this.imagesAnnex.addAll(images);

            adapterAnnex.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        postData();
    }

    @Override
    public void showResult() {
        showToast("提交成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showDetailResult(AlarmBean alarmBean) {
        alarmDes.setText(alarmBean.getDescription() + "");
        alarmTime.setText(alarmBean.getCreateTime() + "");

    }
    void postData() {
        String handleDescription = alarmContent.getText().toString();
        String id = getIntent().getStringExtra("id");
        ArrayList<String> baseb4 = new ArrayList<>();
        for (String str : this.imagesAnnex) {
            baseb4.add("data:image/jpg;base64," + BASE64.imageToBase64(str));
        }
        String s = new Gson().toJson(baseb4);
        mPresenter.submitData(id,"0",handleDescription,id,s);

    }
}
