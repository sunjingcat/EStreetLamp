package com.zz.lib.commonlib.ui;


import androidx.fragment.app.Fragment;

import com.zz.lib.commonlib.utils.IToast;


/**
 * Created by admin on 2018/4/23.
 */

public class CommonFragment extends Fragment {

    public  void show_Toast(String text) {
        IToast.show(this.getContext(),text);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

}
