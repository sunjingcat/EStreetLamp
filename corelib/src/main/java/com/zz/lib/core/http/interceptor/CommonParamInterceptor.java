package com.zz.lib.core.http.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/5/17.
 */

public class CommonParamInterceptor implements Interceptor {

    private Map<String, Object> paramsMap = new TreeMap<>();

    public CommonParamInterceptor(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (request.method().equals("POST") && request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();
            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                bodyBuilder.addEncoded(entry.getKey(), (String) entry.getValue());
            }
            formBody = bodyBuilder
                    .build();
            request = request.newBuilder().post(formBody).build();
            return chain.proceed(request);
        } else  {
            HttpUrl.Builder builder = request.url()
                    .newBuilder();
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                builder.addQueryParameter(entry.getKey(), (String) entry.getValue());
            }
            HttpUrl httpUrl = builder.build();
            request = request.newBuilder().url(httpUrl).build();
        }
        return chain.proceed(request);
    }
}


