package com.zz.lamp.bean;

import android.text.TextUtils;

public class ImageBean {
    private String imgName;//lmx_3f8fbba997374d128417fcb65120eb9b.jpg,

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;//lmx_3f8fbba997374d128417fcb65120eb9b.jpg,
    private String ImageUrl;//http://221.225.80.128:8888/lmx-file/images/2018-11-01/
    private String imageUrl;//http://221.225.80.128:8888/lmx-file/images/2018-11-01/

    public String getImgName() {
        return imgName;
    }

    public String getImageUrl() {
        return TextUtils.isEmpty(ImageUrl)?imageUrl+imgName:ImageUrl+imgName;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
