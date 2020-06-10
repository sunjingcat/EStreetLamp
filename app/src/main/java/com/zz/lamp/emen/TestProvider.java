package com.zz.lamp.emen;

import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.net.CompactModel;
import com.zz.lamp.net.JsonT;
import com.zz.lib.commonlib.model.GsonModel;

public @interface TestProvider {
    int id();
    String type();
    java.lang.Class<? extends Base> json();
}
