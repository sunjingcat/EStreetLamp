package com.zz.lib.core.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.zz.lib.core.BaseApplication;
import com.zz.lib.core.R;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.IOException;
import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @author Allen
 *         Created by Allen on 2017/10/23.
 *         异常类型
 */

public class ApiException extends Exception {

    private final int code;
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
            try {
                ex.message = httpException.response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
                ex.message = e1.getMessage();
            }
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_time_out);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_time_out);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_time_out);
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_time_out);
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULL_POINTER_EXCEPTION);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_nullpointer);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_certificate_check_fail);
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_conventor);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSyntaxException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_parse);
            return ex;
        } else if (e instanceof IllegalStateException) {
            ex = new ApiException(e, ERROR.ILLEGAL_STATE_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if (e instanceof NoRouteToHostException) {
            ex = new ApiException(e, ERROR.NO_ROUTE_TO_HOST);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_no_route);
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = BaseApplication.getAppContext().getString(R.string.err_unknown);
            return ex;
        }
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1001;
        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1002;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1003;

        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1004;

        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1005;

        /**
         * 非法数据异常
         */
        public static final int ILLEGAL_STATE_ERROR = 1006;
        /**
         * No route to host
         */
        public static final int NO_ROUTE_TO_HOST = 1007;

    }
}
