package com.zz.lib.core.ui.widget.listener;


import android.view.View;

/**
 * Created by admin on 2018/5/31.
 */

public interface OnMenuItemClickListener<T extends MenuInterface> {

    void onMenuClick(T model, View v);

}
