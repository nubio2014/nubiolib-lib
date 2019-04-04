package com.mlzq.nubiolib.mvpbase.model;

/**
 * @author ldc
 * @Created at 2018/3/30 9:48.
 */

public interface IBaseModel {
    /**
     * 取消请求
     * @param cancleTag
     */
    void cancelByTag(Object cancleTag);
}
