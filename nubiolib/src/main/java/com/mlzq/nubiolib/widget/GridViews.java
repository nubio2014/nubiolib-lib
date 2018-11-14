package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/****
 * 显示全部的GridView
 * 与ScrollView滑动冲突的时候可以用
 */
public class GridViews extends GridView {
    public GridViews(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public GridViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public GridViews(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
