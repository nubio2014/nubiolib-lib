package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;


public class MyNineGridView extends GridView {
	private int rownum;//定义行数
	public MyNineGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyNineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyNineGridView(Context context, AttributeSet attrs, int defStyle) {
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

	@Override
	protected void dispatchDraw(Canvas canvas){
		super.dispatchDraw(canvas);
		View localView1 = getChildAt(0);
		int column = getWidth() / localView1.getWidth();
		int childCount = getChildCount(); //获取Item总数
		Paint localPaint;
		localPaint = new Paint();
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setColor(Color.parseColor("#EAEAEA"));
		for(int i = 0;i < childCount;i++){
			View cellView = getChildAt(i);
			if((i + 1) % column == 0){
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
			}else if((i + 1) > (childCount - (childCount % column))){
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
			}else{
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
			}
		}
	}

}
