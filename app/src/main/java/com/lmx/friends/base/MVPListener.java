package com.lmx.friends.base;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface MVPListener<E> {

    /**
     * 成功的时候回调
     */
    void onSuccess(E pJoke);
    /**
     * 失败的时候回调
     */
    void onError();



}