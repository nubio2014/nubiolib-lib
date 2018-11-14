package com.mlzq.nubiolib.widget;/**
 * Created by dell on 2017/4/6/0006.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mlzq.nubiolib.R;


public class BottomBar extends LinearLayout {

    private Context mContext;
    RadioGroup mGroup;
    ImageView centerIamge;
    private RadioButton mFirst_bottom, mSecond_bottom, mThird_bottom, mFouth_bottom,mFive_bottom;
    //FrameLayout mCenter_bottom;
    private OnBottonbarClick mOnBottonbarClick;

    public BottomBar(Context context) {
        super(context);
        init(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.bottom, this, true);
        //获取控件id
        initId();
        onBottomBarClick();
    }



    private void initId() {
        mFirst_bottom = (RadioButton) findViewById(R.id.first);
        mSecond_bottom = (RadioButton) findViewById(R.id.second);
        mThird_bottom = (RadioButton) findViewById(R.id.third);
        mFouth_bottom = (RadioButton) findViewById(R.id.four);
       mFive_bottom = (RadioButton) findViewById(R.id.five);
        mGroup= (RadioGroup) findViewById(R.id.bottom_group);

    }

    /**
     * 底部按钮点击监听器
     */
    private void onBottomBarClick() {

        mFirst_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFirstClick();
                }
            //    centerIamge.setImageResource(R.drawable.btn_wu);
            }
        });
        mSecond_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onSecondClick();
                }
              //  centerIamge.setImageResource(R.drawable.btn_wu);
            }
        });
        mThird_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onThirdClick();
                }
              //  centerIamge.setImageResource(R.drawable.btn_wu);
            }
        });
        mFouth_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFouthClick();
                }
               // centerIamge.setImageResource(R.drawable.btn_wu);
            }
        });
        mFive_bottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBottonbarClick != null) {
                    mOnBottonbarClick.onFiveClick();
                }
            }
        });

    }
    public void setOnBottombarOnclick(OnBottonbarClick onBottonbarClick) {

        mOnBottonbarClick = onBottonbarClick;
    }

    public void setOnBottombarPress(int code){
        switch (code){
            case 1:
                mFirst_bottom.setChecked(true);
              //  centerIamge.setImageResource(R.drawable.btn_wu);
                break;
            case 2:
                mSecond_bottom.setChecked(true);
               // centerIamge.setImageResource(R.drawable.btn_wu);
                break;
            case 3:
                mThird_bottom.setChecked(true);
              //  centerIamge.setImageResource(R.drawable.btn_wu);
                break;
            case 4:
                mFouth_bottom.setChecked(true);

                break;
            case 5:
                mFive_bottom.setChecked(true);
                break;
        }

    }

    public void clearStatue(){
        mGroup.clearCheck();
    }
    public interface OnBottonbarClick {
        void onFirstClick();

        void onSecondClick();

        void onThirdClick();

        void onFouthClick();

        void onFiveClick();
    }
}
