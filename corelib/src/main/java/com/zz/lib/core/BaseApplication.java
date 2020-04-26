package com.zz.lib.core;

import com.zz.lib.commonlib.CommonApplication;
import com.tencent.bugly.crashreport.CrashReport;


public class BaseApplication extends CommonApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext());
    }


}
