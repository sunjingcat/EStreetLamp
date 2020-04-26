package com.zz.lib.commonlib.utils;

import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.zz.lib.commonlib.CommonApplication;
import com.zz.lib.commonlib.R;

/**
 * Created by 77 on 2018/4/23.
 */

public class ToolBarUtils {

    private static ToolBarUtils toolBarUtils;

    //用单一实例来调用这个方法
    public static ToolBarUtils getInstance() {
        if (toolBarUtils == null) {
            toolBarUtils = new ToolBarUtils();
        }
        return new ToolBarUtils();
    }

    public void setNavigation(Toolbar toolbar) {
        toolbar.setTitle("");
        CommonApplication.activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.return_button);
        //设置回退监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonApplication.activity.finish();
            }
        });

    }
}
