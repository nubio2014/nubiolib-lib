package com.mlzq.nubiolib.mvpbase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mlzq.nubiolib.R;
import com.mlzq.nubiolib.app.BaseActivity;
import com.mlzq.nubiolib.mvpbase.presenter.BasePresenter;
import com.mlzq.nubiolib.mvpbase.view.BaseView;


/**
 * Created by 科能 on 2018/11/16.
 * desc :
 */

public abstract class MVPBaseActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    LinearLayout ll_content;
    protected T mPrensenter;
    protected ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTitleLayout();
        if (mPrensenter == null) {
            mPrensenter = createPresenter();
        }
        if (mPrensenter != null) {
            mPrensenter.attachView(this);
        }
    }
    /**
     * 创建 Presenter对象
     *
     * @return
     */
    protected abstract T createPresenter();

    public void initTitleLayout(){
        setTitleBG(R.color.maincolor);//设置标题背景色
        setLeftIcon(R.drawable.ic_back);//设置左图标
        setBaseTitleColor(R.color.white);//设置标题字体颜色
    }



    @Override
    protected void onDestroy() {
        if (mPrensenter != null) {
            mPrensenter.detachView();
        }
        super.onDestroy();

    }



}
