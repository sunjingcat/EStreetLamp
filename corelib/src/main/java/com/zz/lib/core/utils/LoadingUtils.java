package com.zz.lib.core.utils;


import android.content.Context;

import com.zz.lib.core.R;
import com.zz.lib.core.ui.widget.CustomProgressDialog;


public class LoadingUtils {



	public static CustomProgressDialog build(Context context) {
		CustomProgressDialog dialog = new CustomProgressDialog(context, "", R.style.Dialog_Fullscreen, R.drawable.animation_loading_running);
		dialog.setCancelable(false); // 设置不响应返回按钮点击事件
		return dialog;
	}
}
