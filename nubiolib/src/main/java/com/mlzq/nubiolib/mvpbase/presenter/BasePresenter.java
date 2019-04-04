package com.mlzq.nubiolib.mvpbase.presenter;


import com.mlzq.nubiolib.mvpbase.view.BaseView;

/**
 * @author ldc
 * @Created at 2018/6/21 14:55.
 */

public abstract class BasePresenter<T extends BaseView> implements IBasePresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }



}
