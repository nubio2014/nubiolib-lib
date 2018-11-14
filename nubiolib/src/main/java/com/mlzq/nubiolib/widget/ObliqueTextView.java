package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 文字倾斜文本
 */
public class ObliqueTextView extends TextView {

    public ObliqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(-45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }

}
