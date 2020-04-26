package com.zz.lib.commonlib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.contrarywind.view.WheelView;
import com.zz.lib.commonlib.R;


/**
 * Created by user on 2017-09-25.
 */

public class SelectPopupWindows extends PopupWindow {

    private static final String TAG = "FinishProjectPopupWindows";
    private String[] PLANETS ;
    public Button btnCancelProject, btnOkProject;
    private View mView;
    private WheelView wheel_select;
    private OnItemClickListener listener;

    public SelectPopupWindows(final Activity context, String[] strings
                              ) {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.sex_popupwindow, null);
        PLANETS = strings;
        wheel_select = (WheelView) mView.findViewById(R.id.wheel_select);
        wheel_select.setCyclic(false);
        ArrayWheelAdapter wheelAdapter = new ArrayWheelAdapter(PLANETS);
        wheel_select.setAdapter(wheelAdapter);
        btnCancelProject = (Button) mView.findViewById(R.id.popupwindow_cancelButton);
        btnOkProject = (Button) mView.findViewById(R.id.popupwindow_okButton);
        btnOkProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = wheel_select.getCurrentItem();
                listener.onSelected(currentItem,PLANETS[currentItem]);
                dismiss();
            }
        });
        // 设置按钮监听
        btnCancelProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(context, 1f);
                dismiss();
            }
        });
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x70000000);
//        设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(context, 0.5f);
        this.setFocusable(true);


    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onSelected(int index, String msg);

        void onCancel();
    }

}
