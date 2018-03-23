package event;

import java.util.Arrays;

import android.R.anim;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchEventView extends View{

	private String tag=getClass().getSimpleName()+getId();
	public TouchEventView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		System.err.println(tag+"========onLayout======"+changed+"============="+l+"="+r+"=="+t+"==="+b);
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.err.println(tag+"===========onMeasure===========h="+getMeasuredHeight()+"===w="+getMeasuredWidth());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		System.err.println(tag+"=========dispatchTouchEvent======"+event.getAction());
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.err.println(tag+"=========onTouchEvent======"+event.getAction());
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:	
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		ridus=Math.min(getWidth(), getHeight())/2;
		if(ridus==0) {
			return;
		}
		canvas.save();
		canvas.translate(getWidth()/2, getHeight()/2);
		if(path==null) {
			path=new Path();
			path.moveTo(0, -ridus/2);
			path.cubicTo(0, -ridus/2, ridus*3/4, -ridus*3/4, ridus, 0);
			path.lineTo( 0, ridus);
			path.lineTo(-ridus, 0);
			path.cubicTo(-ridus, 0, -ridus*3/4, -ridus*3/4, 0, -ridus/2);
			path.close();
			pathMeasure.setPath(path, false);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
		}
		
		paint.setColor(Color.BLACK);
		
		canvas.drawPath(path, paint);
		paint.setColor(Color.RED);
		if(animator!=null)
		canvas.drawCircle(pos[0], pos[1], 5, paint);
		canvas.restore();
	}
	int ridus;
	Path path;
	Paint paint=new Paint();
	PathMeasure pathMeasure=new PathMeasure();
	float[] pos=new float[2];
	float[] tan=new float[2];
	ValueAnimator animator;
	public void startAnimatiion() {
		if(animator!=null) {
			animator.cancel();
		}
		 animator=ValueAnimator.ofFloat(0,1f);
		animator.setDuration(5000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value=animation.getAnimatedFraction();
				pathMeasure.getPosTan(pathMeasure.getLength()*value, pos, tan);
			
				postInvalidate();
				
			}
		});

		animator.start();
	}
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
	if(animator!=null) {
		animator.cancel();
	}
		super.onDetachedFromWindow();
	}
}
