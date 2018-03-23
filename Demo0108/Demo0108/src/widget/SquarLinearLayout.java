package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SquarLinearLayout extends LinearLayout {

	public SquarLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		System.err.println("widthMeasureSpec==========="+widthMeasureSpec+"============heightMeasureSpec="+heightMeasureSpec);
//		System.err.println("getMode==============w="+MeasureSpec.getMode(widthMeasureSpec)+"===h="+MeasureSpec.getMode(heightMeasureSpec));
//		System.err.println("getSize========"+MeasureSpec.getSize(widthMeasureSpec)+" ========="+MeasureSpec.getSize(heightMeasureSpec));
//		
		System.err.println(getChildCount()+"===11111======"+MeasureSpec.UNSPECIFIED+"======"+MeasureSpec.EXACTLY+"======="+MeasureSpec.AT_MOST);
		for(int i=0;i<getChildCount();i++) {
			View child=getChildAt(i);
			if(child!=null) {
				if(getOrientation()==LinearLayout.HORIZONTAL) {
//					System.err.println("i=="+i+" =="+child.getHeight()+"==="+child.getMeasuredHeight());
					if(child.getMeasuredHeight()<getHeight()) {
						child.measure(child.getMeasuredWidth(), MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
					}
					
				}else {
					if(child.getMeasuredWidth()<getWidth()) {
						child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),child.getMeasuredHeight());
					}
				}
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.err.println("onLayout============="+changed+"========="+(b-t)+"============"+getChildAt(1).getHeight()+"=="+getChildAt(1).getMeasuredHeight());
		super.onLayout(changed, l, t, r, b);
	}
	
	//如果没有背景的话，这个是不会调用的。设置divier也可以
	@Override
	protected void onDraw(Canvas canvas) {
		System.err.println("=============onDraw");
		super.onDraw(canvas);
	}
	
	@Override
	public void draw(Canvas canvas) {
		System.err.println("===============draw");
		super.draw(canvas);
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		System.err.println("===============dispatchDraw");
		super.dispatchDraw(canvas);
	}
}
