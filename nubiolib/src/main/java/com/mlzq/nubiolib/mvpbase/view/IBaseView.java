package com.mlzq.nubiolib.mvpbase.view;

import android.content.Context;

/**
 * @author ldc
 * @Created at 2018/3/30 9:48.
 */

public interface IBaseView {
    /**
     * 获取上下文对象
     */
    public Context getContext();

    /**
     * 检查View是否是激活状态
     *
     * @return true 已激活 可以对View进行操作 false View无法被操作
     */
    boolean isActive();
    /**
     * 显示空视图
     */
    void showLoadDataEmptyView();


    /**
     * Toast
     *
     * @param message
     */
    void showToast(CharSequence message);


    void showLoading();

    void hideLoading();


}
