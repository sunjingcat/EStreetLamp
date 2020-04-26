package com.lmx.friends.base;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2018/4/23.
 */

public class CreateUtil {
    static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}