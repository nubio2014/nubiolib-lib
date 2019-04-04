package com.mlzq.nubiolib.mvpbase.model;

/**
 * 用于加载数据回调
 * <p>用于Model层</p>
 *
 * @author ldc
 * @Created at 2018/3/30 9:44.
 */

public interface ValueCallback<T> {
    /***
     * 加载数据成功时调用
     * @param data
     */
    void onSuccess(T data);

    void onFailed(String msg);

    /**
     * 加载异常
     *
     * @param e
     */
    void onError(Throwable e);


}
