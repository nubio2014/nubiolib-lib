package com.mlzq.nubiolib.mvpbase.presenter;


import com.mlzq.nubiolib.mvpbase.view.IBaseView;

/**
 * @author ldc
 * @Created at 2018/3/30 9:48.
 */

public interface IBasePresenter<T extends IBaseView> {


//    /**
//     * 回收View
//     */
//    void detachView();
    /**
     * View初始化完成调用
     * <p>一般是开始加载数据</p>
     */
    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * View销毁时调用
     * <p>用于释放资源 如撤销网络请求</p>
     */
    void detachView();

}
