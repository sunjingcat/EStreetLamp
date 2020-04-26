package com.zz.lib.core.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/4/26.
 */

public class BaseApiImpl<T> {

    protected Map<String, String> buildParams(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    protected Map<String, Object> buildParamsCompact(Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
