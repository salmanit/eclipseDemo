package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout{

	public CustomRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
//		setBackgroundColor(Color.parseColor("#125980"));
	}

	
	@Override
	public void draw(Canvas canvas) {
		System.err.println("RelativeLayout===============draw");
		super.draw(canvas);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		System.err.println("RelativeLayout===============onDraw");
		super.onDraw(canvas);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		System.err.println("RelativeLayout===============dispatchDraw");
		super.dispatchDraw(canvas);
	}
}
