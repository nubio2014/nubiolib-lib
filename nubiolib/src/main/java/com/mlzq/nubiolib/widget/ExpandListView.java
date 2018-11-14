package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/****
 * 显示全部的ListView
 * 与ScrollView滑动冲突的时候可以用
 */
public class ExpandListView extends ListView {

    public ExpandListView(Context context) {
        super(context);
    }

    public ExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
                , MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
