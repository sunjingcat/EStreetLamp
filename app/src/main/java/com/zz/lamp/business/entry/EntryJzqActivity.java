package com.zz.lamp.business.entry;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.business.entry.mvp.presenter.TerminalAddPresenter;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EntryJzqActivity extends MyBaseActivity<Contract.IsetTerminalAddPresenter> implements Contract.IGetTerminalAddView {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.terminalAddr)
    EditText terminalAddr;
    @BindView(R.id.terminalName)
    EditText terminalName;
    @BindView(R.id.terminalType)
    TextView terminalType;
    @BindView(R.id.loopCount)
    EditText loopCount;
    @BindView(R.id.lineCount)
    EditText lineCount;
    @BindView(R.id.loopTransformerRatio)
    EditText loopTransformerRatio;
    @BindView(R.id.lineTransformerRatio)
    EditText lineTransformerRatio;
    @BindView(R.id.alarmDelayedTime)
    EditText alarmDelayedTime;
    @BindView(R.id.relayOnDelayedTime)
    EditText relayOnDelayedTime;
    @BindView(R.id.lat)
    TextView lat;

    @Override
    protected int getContentView() {
        return R.layout.activity_entry_jzq;
    }

    @Override
    public TerminalAddPresenter initPresenter() {
        return new TerminalAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    @OnClick({R.id.toolbar_subtitle, R.id.tv_area, R.id.terminalType, R.id.lat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:

                break;
            case R.id.tv_area:
                break;
            case R.id.terminalType:
                String[] PLANETS2 = new String[]{"塔吊", "升降机", "塔吊黑匣子","环境监测设备","其他"};
                final SelectPopupWindows selectPopupWindows2 = new SelectPopupWindows(this, PLANETS2);
                selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
//                        equipmentType=index+1;
//                        deviceEquipmentType.setText(msg);
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
            case R.id.lat:

                break;
        }
    }

    @Override
    public void showIntent() {

    }
}
