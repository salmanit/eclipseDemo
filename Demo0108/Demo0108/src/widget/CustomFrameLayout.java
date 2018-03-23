package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CustomFrameLayout extends FrameLayout {

	public CustomFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.parseColor("#125980"));
	}
	@Override
	public void draw(Canvas canvas) {
		System.err.println("FrameLayout===============draw");
		super.draw(canvas);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		System.err.println("FrameLayout===============onDraw");
//		super.onDraw(canvas);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		System.err.println("FrameLayout===============dispatchDraw");
		super.dispatchDraw(canvas);
	}
}
