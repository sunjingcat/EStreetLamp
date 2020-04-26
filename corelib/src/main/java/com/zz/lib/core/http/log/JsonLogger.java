package com.zz.lib.core.http.log;
import com.zz.lib.commonlib.utils.LogFormatUtils;
import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by admin on 2018/4/28.
 */

public class JsonLogger implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();
    private String tag = null;

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            tag = message.replace("--> POST","").trim();
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = LogFormatUtils.formatJson(LogFormatUtils.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            if(tag != null){
                Logger.t(tag).d(mMessage.toString());
                tag = null;
            }

        }
    }
}
