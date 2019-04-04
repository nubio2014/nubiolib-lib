package com.mlzq.nubiolib.widget;/**
 * Created by dell on 2017/4/6/0006.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mlzq.nubiolib.R;
import com.mlzq.nubiolib.app.GeneralBean;

import java.util.List;


public class MyBottomBar extends LinearLayout {

    private Context mContext;
    RadioGroup mGroup;
    ImageView centerIamge;
    private RadioButton mFirst_bottom, mSecond_bottom, mThird_bottom, mFouth_bottom,mFive_bottom;
    //FrameLayout mCenter_bottom;
    private OnBottonbarClick mOnBottonbarClick;
    private int layoutid=R.layout.radiobutton;//radiobuttom布局
    public int getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(int textcolor) {
        this.textcolor = textcolor;
    }

    private int textcolor=R.color.fontcolor3;

    public MyBottomBar(Context context) {
        super(context);
        init(context);
    }

    public MyBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.mybottom, this, true);
        //获取控件id
        initId();
    }


public void setRadioButtomLayout(int radioButtomLayout){
    this.layoutid=radioButtomLayout;
}
    private void initId() {
//        mFirst_bottom = (RadioButton) findViewById(R.id.first);
//        mSecond_bottom = (RadioButton) findViewById(R.id.second);
//        mThird_bottom = (RadioButton) findViewById(R.id.third);
//        mFouth_bottom = (RadioButton) findViewById(R.id.four);
//       mFive_bottom = (RadioButton) findViewById(R.id.five);
        mGroup= (RadioGroup) findViewById(R.id.bottom_group);

    }

    public void setGroupItems( String[] titles,int[]drawables){
        initView(titles,drawables);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mOnBottonbarClick.onItemClick(checkedId);
            }
        });
    }
    public void setGroupItems(List<GeneralBean>list){
        initListView(list);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mOnBottonbarClick.onItemClick(checkedId);
            }
        });
    }
    private void initView( String[] titles,int[]drawables){
        for (int i = 0; i < titles.length; i++) {
            RadioButton radioButton= (RadioButton) LayoutInflater.from(mContext).inflate(layoutid,null,false);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.MATCH_PARENT,1);
            Drawable drawable = getResources().getDrawable(drawables[i]);
//            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));//button样式为透明
//            radioButton.setCompoundDrawablePadding(20);//设置drawablePadding
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            radioButton.setId(i);
            radioButton.setText(titles[i]);
            radioButton.setChecked(i==0);
            radioButton.setLayoutParams(layoutParams);
            mGroup.addView(radioButton);

        }
    }

    private void initListView(List<GeneralBean>list){
        for (int i = 0; i < list.size(); i++) {
            RadioButton radioButton= (RadioButton) LayoutInflater.from(mContext).inflate(layoutid,null,false);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.MATCH_PARENT,1);
            Drawable drawable = getResources().getDrawable(list.get(i).getUrl());
//            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));//button样式为透明
//            radioButton.setCompoundDrawablePadding(20);//设置drawablePadding
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            radioButton.setId(i);
            radioButton.setText(list.get(i).getName());
            radioButton.setChecked(i==0);
            radioButton.setLayoutParams(layoutParams);
            mGroup.addView(radioButton);

        }
    }

    public void setOnBottombarOnclick(OnBottonbarClick onBottonbarClick) {
        mOnBottonbarClick = onBottonbarClick;
    }



    public void clearStatue(){
        mGroup.clearCheck();
    }
    public interface OnBottonbarClick {
     void onItemClick(int position);
    }
}
