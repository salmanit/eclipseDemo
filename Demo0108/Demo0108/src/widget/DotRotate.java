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

	int radius = 0;//ԭʼ����С��ľ������ĵ�ľ���
	float radiusFactor = 1;//С��������ĵ�ı�����������
	
	int radiusMax;//��ɢԲ�����ֵ���õĿ��һ���
	float radiusTrans;//͸�ܿ��Ĵ�С
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=getMeasuredWidth()/2;
		int height=getMeasuredHeight()/2;
		radius =Math.min(width, height) * 2 / 3;//����߽磬���Ծ�ȡ2/3��С����
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
		
		if (radiusTrans > 0) {//��ʼ������ɢ��ʱ�򣬾Ͳ��û�С����
			//ͨ���ü�ʵ��
			path.reset();
			path.addCircle(0, 0, radiusTrans, Direction.CW);;
			canvas.clipPath(path, Region.Op.DIFFERENCE);
			canvas.drawColor(Color.WHITE);
			//�������±ߵĴ���
//			paintBG.setStrokeWidth(radiusMax-radiusTrans);//���ʵĿ���Ǽ�ȥ͸�����ֵ���������
			//����˵�£�����뾶Ϊ40�����ʿ�Ϊ10����ô���������ǻ���40-10/2����40+10/2������˵���ʿ����ƽ�������ĵ�����ߵģ�
			//�����±߾��Ǽ������ĵ������Ĵ�����
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
		animator = ValueAnimator.ofFloat(1, 0.0f);//С��İ뾶�������ӣ������ֵ��0
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				radiusFactor = (Float) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.setDuration(1000);
		animator.setInterpolator(new AnticipateInterpolator());//��һ�����ⵯ�Ķ������Ϳ������������
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
		animator.setInterpolator(new AccelerateInterpolator());//�õļ��ٲ�ֵ��
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
