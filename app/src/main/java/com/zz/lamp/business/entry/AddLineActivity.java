package com.zz.lamp.business.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.UsableCode;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;
import static com.zz.lib.core.http.utils.ToastUtils.showToast;

/**
 * 线路
 */
public class AddLineActivity extends Activity {

    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.lblDialogMessage)
    EditText lblDialogMessage;
    @BindView(R.id.lblDialogSelect)
    TextView lblDialogSelect;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_OK)
    Button btnOK;
    String terminalId;
    String lineId="";
    String type;
    String [] UsableCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
        lineId = getIntent().getStringExtra("lineId");
        UsableCode = getIntent().getStringArrayExtra("UsableCode");
        String code = getIntent().getStringExtra("code");
        String lineName = getIntent().getStringExtra("lineName");
        if (!TextUtils.isEmpty(code)) {
            type = code;
            lblDialogSelect.setText(code+"");
        }
        if (!TextUtils.isEmpty(lineName)) {
            lblDialogMessage.setText(lineName+"");
        }

    }
    
    @OnClick({R.id.close, R.id.lblDialogSelect, R.id.btn_cancel, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.lblDialogSelect:
                final SelectPopupWindows selectPopupWindows2 = new SelectPopupWindows(this, UsableCode);
                selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        LogUtils.v("sj---",msg);
                        lblDialogSelect.setText(msg+"");
                        type = msg;
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_OK:
                String trim = lblDialogMessage.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    showToast("请输入别名");
                    return;
                }
                if (TextUtils.isEmpty(type)){
                    showToast("请选择线路编码");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",trim);
                intent.putExtra("code",type);
                if (!TextUtils.isEmpty(lineId)){
                    intent.putExtra("lineId",lineId);
                }
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

}
