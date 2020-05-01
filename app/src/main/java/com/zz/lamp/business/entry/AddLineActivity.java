package com.zz.lamp.business.entry;

import android.app.Activity;
import android.os.Bundle;
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
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.lamp.net.RxNetUtils.getCApi;
import static com.zz.lib.core.http.utils.ToastUtils.showToast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);
        ButterKnife.bind(this);
        terminalId = getIntent().getStringExtra("terminalId");
    }


    @OnClick({R.id.close, R.id.lblDialogSelect, R.id.btn_cancel, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.lblDialogSelect:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_OK:
                break;
        }
    }
//    private void getData(){
//        RxNetUtils.request(getCApi(ApiService.class).getUsableCode(terminalId), new RequestObserver<JsonT<List<UsableCode>>>(this) {
//            @Override
//            protected void onSuccess(JsonT<List<UsableCode>> data) {
//                if (data.isSuccess()) {
//
//                }else {
//
//                }
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<UsableCode>> userInfoJsonT) {
//                super.onFail2(userInfoJsonT);
//                showToast(userInfoJsonT.getMessage());
//            }
//        }, LoadingUtils.build(this));
//    }
}
