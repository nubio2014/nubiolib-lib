package com.mlzq.nubiolib.mvpbase.view;


/**
 * @author ldc
 * @Created at 2018/6/21 14:53.
 */

public interface BaseView extends IBaseView {
        void showLoading();//
        void showSuccess(String message);
        void showLoadError(String errorstr);//获取失败


}
