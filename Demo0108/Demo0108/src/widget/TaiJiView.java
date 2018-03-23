package widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.gesture.GesturePoint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
/**太极图就是外边一个大圆，中心线上两个小圆各取一半组成的*/
public class TaiJiView extends View {

	public TaiJiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
		int[] rotate= {android.R.attr.fromDegrees,android.R.attr.toDegrees,android.R.attr.pivotX,android.R.attr.pivotY};
		TypedArray typedArray=context.obtainStyledAttributes(attrs, rotate);
		float fromDegress=typedArray.getFloat(0, -1);
		float toDegress=typedArray.getFloat(1, -1);
		float pivotX=typedArray.getFloat(2, -1);
		float pivotY=typedArray.getFraction(3, 200, 50, 33);
		TypedValue typedValue=typedArray.peekValue(3);
		
		System.err.println(fromDegress+"=========="+toDegress+"======="+pivotX+"=="+pivotY);
		System.err.println("type=="+typedValue.type+"==="+typedValue.data +"=="+ TypedValue.complexToFloat(typedValue.data));
		typedArray.recycle();
		
	}

	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);

	private void initPaint() {
		paint.setStyle(Style.FILL);
		
		paintCircle.setStyle(Style.STROKE);
		paintCircle.setStrokeWidth(20);
		
	}
	
	private int radius;// 太极的半径，我们取宽高最小值的2/3
	private Path pathLeft = new Path();// 太极左边的路径
	private Path pathRight = new Path();// 右边的路径
	RectF oval = new RectF();//大圆的范围
	RectF ovalTop = new RectF();//上边小圆的
	RectF ovalBottom = new RectF();//下边小圆的范围

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		radius = (Math.min(getMeasuredWidth(), getMeasuredHeight()) * 2 / 3) >> 1;
		if (radius > 0) {
			pathLeft.reset();
			//下边的坐标计算都是把中心点偏移到画布正中心来计算的
			oval.set(-radius, -radius, radius, radius);
			ovalTop.set(-radius / 2, -radius, radius / 2, 0);
			ovalBottom.set(-radius / 2, 0, radius / 2, radius);
			
			pathLeft.addArc(oval, 90, 180);
			pathLeft.addArc(ovalTop, -90, 180);
			pathLeft.addArc(ovalBottom, 90, 180);

			pathRight.addArc(oval, 90, -180);
			pathRight.addArc(ovalTop, -90, 180);
			pathRight.addArc(ovalBottom, 90, 180);

		}
	}
	int[] colors= {Color.RED,Color.BLUE};
	private Point getPoint(float degress) {
		Point point=new Point();
		
		point.x=(int) ((radius+20)*Math.cos(degress*Math.PI/180));
		point.y=(int) ((radius+20)*Math.sin(degress*Math.PI/180));
		return point;
	}
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (radius == 0) {
			return;
		}
		canvas.save();
		canvas.translate(getWidth() >> 1, getHeight() >> 1);//画布平移到中心位置
//		canvas.rotate(degress,0,0);
		
		Point startP=getPoint(270);
		Point endP=getPoint(270-degress);
		Point centerP=getPoint(270-degress/2);
//		paintCircle.setShader(new LinearGradient(startP.x,startP.y,endP.x,endP.y, colors, null, TileMode.CLAMP));
//		paintCircle.setShader(new RadialGradient(centerP.x, centerP.y, radius*2, Color.RED,Color.BLUE, TileMode.CLAMP));
		paintCircle.setShader(new SweepGradient(0, 0, Color.RED,Color.BLUE));
//		canvas.drawCircle(0, 0, radius+20, paintCircle);
		canvas.drawArc(oval, 270, -degress, false, paintCircle);
		paint.setColor(Color.WHITE);
		canvas.drawPath(pathLeft, paint);
		paint.setColor(Color.BLACK);
		canvas.drawPath(pathRight, paint);
		//画两个太极眼， 这里坐标点其实不用算，就是在y轴上，最开始我是偏移了一定角度的才这样写的
		canvas.drawCircle(0,-radius/2, radius / 6, paint);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(0,radius/2,radius / 6, paint);
//		canvas.drawCircle((float) (radius / 2f * Math.cos(-Math.PI / 2)),
//				(float) (radius / 2f * Math.sin(-Math.PI / 2)), radius / 6, paint);
//		paint.setColor(Color.WHITE);
//		canvas.drawCircle((float) (radius / 2f * Math.cos(Math.PI / 2)), (float) (radius / 2f * Math.sin(Math.PI / 2)),
//				radius / 6, paint);
		
		canvas.restore();
	}
	float degress=0;
	ValueAnimator animator;
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(animator==null) {
			animator=ValueAnimator.ofFloat(0,360);
			animator.setDuration(5000);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					degress=(Float) animation.getAnimatedValue();
					postInvalidate();
				}
			});
			animator.start();
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if(animator!=null) {
			animator.cancel();
			animator=null;
		}
	}
}
