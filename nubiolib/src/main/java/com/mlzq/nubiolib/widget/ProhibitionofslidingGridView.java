package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 这是一个禁止滑动的GridView
 */

public class ProhibitionofslidingGridView extends GridView {
    public ProhibitionofslidingGridView(Context context) {
        super(context);
    }

    public ProhibitionofslidingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//true:禁止滚动
        }

        return super.dispatchTouchEvent(ev);
    }
}
