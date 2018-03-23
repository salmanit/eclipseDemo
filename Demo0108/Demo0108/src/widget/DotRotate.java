package widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

public class DotRotate extends View {

	public DotRotate(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	int[] colors = { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.parseColor("#215980") };
	float startAngle = 0;
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint paintBG = new Paint(Paint.ANTI_ALIAS_FLAG);
	Path path=new Path();
	private void initPaint() {
		paint.setStyle(Style.FILL);
		paintBG.setColor(Color.WHITE);
		paintBG.setStyle(Style.STROKE);
	}

	int radius = 0;//原始几个小球的距离中心点的距离
	float radiusFactor = 1;//小球距离中心点的比例，收缩用
	
	int radiusMax;//扩散圆的最大值，用的宽高一半的
	float radiusTrans;//透密空心大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=getMeasuredWidth()/2;
		int height=getMeasuredHeight()/2;
		radius =Math.min(width, height) * 2 / 3;//留点边界，所以就取2/3大小好啦
		radiusMax = (int) Math.sqrt(width*width+ height*height);
	}
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (radius == 0) {
			return;
		}
		
		float intervalAngle = (float) (2 * Math.PI / colors.length);
		canvas.save();
		canvas.translate(getWidth() / 2, getHeight()/2);
		
		if (radiusTrans > 0) {//开始中心扩散的时候，就不用画小球拉
			//通过裁剪实现
			path.reset();
			path.addCircle(0, 0, radiusTrans, Direction.CW);;
			canvas.clipPath(path, Region.Op.DIFFERENCE);
			canvas.drawColor(Color.WHITE);
			//或者用下边的代码
//			paintBG.setStrokeWidth(radiusMax-radiusTrans);//画笔的宽就是减去透明部分的其他部分
			//举例说下，比如半径为40，画笔宽为10，那么最终线条是画在40-10/2，和40+10/2，就是说画笔宽度是平分在中心点的两边的，
			//所以下边就是计算中心点的坐标的代码拉
//			canvas.drawCircle(0, 0, radiusTrans+(radiusMax-radiusTrans)/2, paintBG);
		} else {
			canvas.drawColor(Color.WHITE);
			for (int i = 0; i < colors.length; i++) {
				double angle = startAngle + i * intervalAngle;
				float x = (float) (radius * radiusFactor * Math.cos(angle));
				float y = (float) (radius * radiusFactor * Math.sin(angle));
				paint.setColor(colors[i]);
				canvas.drawCircle(x, y, 10, paint);
			}
		}

		canvas.restore();
	}

	ValueAnimator animator;

	public void startAnimation() {
		animator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				startAngle = (Float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				startShrink();

			}
		});
		animator.setRepeatCount(0);
		animator.setDuration(2000);
		animator.setInterpolator(new LinearInterpolator());
		animator.start();
	}

	private void startShrink() {
		animator = ValueAnimator.ofFloat(1, 0.0f);//小球的半径缩放因子，从最大值到0
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				radiusFactor = (Float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.setDuration(1000);
		animator.setInterpolator(new AnticipateInterpolator());//有一个向外弹的动作，就靠这个加速器的
		animator.start();
		startEnd();
	}

	public void startEnd() {
		animator = ValueAnimator.ofFloat(10,radiusMax);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				radiusTrans =  (Float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				cancelAnimation();
				setVisibility(GONE);
			}
		});
		animator.setDuration(1000);
		animator.setInterpolator(new AccelerateInterpolator());//用的加速插值器
		animator.setStartDelay(900);
		animator.start();
	}

	public void cancelAnimation() {
		if (animator != null) {
			animator.cancel();
			animator = null;
		}
	}
	
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		startAnimation();
	}
	@Override
	protected void onDetachedFromWindow() {
		if (animator != null) {
			animator.cancel();
			animator = null;
		}
		super.onDetachedFromWindow();
	}
}
