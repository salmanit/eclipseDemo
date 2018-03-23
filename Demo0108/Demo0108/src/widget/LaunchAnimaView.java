package widget;

import java.util.ArrayList;
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
import android.view.View;

public class LaunchAnimaView extends View {

	public LaunchAnimaView(Context context) {
		super(context);
		initSomething();
	}

	public LaunchAnimaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSomething();
	}

	public LaunchAnimaView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSomething();
	}

	Paint paint = new Paint();
	int[] colors = { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN };// 4种球的颜色
	float[] maxSacles = { 2, 1, 2, 1 };// 4种球的最大方法比例
	ArrayList<Paint> paints = new ArrayList<Paint>();// 保存4种画笔
	ArrayList<PathMeasure> measures = new ArrayList<PathMeasure>();// 保存4跳路径的pathmeasure信息
	ArrayList<Path> paths = new ArrayList<Path>();// 保存4跳路径
	ArrayList<float[]> positions = new ArrayList<float[]>();// 最红要画的小球的实时位置信息，数组长度为2，分别保存x和y的坐标
	int[] startXPositioon = new int[4];// 4个小球的起始位置，这里只有x的坐标，而y都是0
	int radius = 1;// 小球的原始半径
	float factorSacle = 0;// 小球的实时放大比例
	// 画布被平移到正中心也就是0，0的位置，所以上边的坐标都是按照正中心为0，0计算的。

	private void initSomething() {
		for (int i = 0; i < 4; i++) {
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(colors[i]);
			paint.setStyle(Style.FILL);
			paints.add(paint);
		}
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(1);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getWidth() / 2, getHeight() / 2);

		for (int i = 0; i < positions.size(); i++) {
			float[] pos = positions.get(i);
			canvas.drawCircle(pos[0], pos[1], radius * (1 + maxSacles[i] * factorSacle), paints.get(i));
			// canvas.drawPath(paths.get(i), paint);
		}

		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		System.err.println("onLayout========================" + changed);
		makeMeasure();
	}

	private boolean haveMeasure = false;

	private void makeMeasure() {
		if (haveMeasure) {
			return;
		}
		haveMeasure = true;
		int halfWidth = getWidth() / 2;
		int halfHeigth = getHeight() / 2;
		radius = halfWidth / 16;
		startXPositioon[0] = -halfWidth / 2;
		startXPositioon[1] = -halfWidth / 4;
		startXPositioon[2] = halfWidth / 4;
		startXPositioon[3] = halfWidth / 2;
		Path path1 = new Path();
		path1.moveTo(-halfWidth / 2, 0);
		path1.cubicTo(-halfWidth / 2, 0, -halfWidth + radius * 4, -halfHeigth * 2 / 3, -halfWidth / 2 - radius * 2,
				-halfHeigth + radius * 4);
		path1.cubicTo(-halfWidth / 4, -halfHeigth + radius * 3, 0, -halfHeigth * 2 / 3, 0, 0);
		path1.moveTo(0, 0);

		Path path2 = new Path();
		path2.moveTo(-halfWidth / 4, 0);
		path2.cubicTo(-halfWidth / 4, 0, -halfWidth / 4 + radius, -halfHeigth * 2 / 3, halfWidth / 2,
				-halfHeigth * 2 / 3);
		path2.cubicTo(halfWidth / 2 + radius, -halfHeigth * 2 / 3 + radius * 3, halfWidth / 2 - radius, -halfHeigth / 3,
				0, 0);
		path2.moveTo(0, 0);

		Path path3 = new Path();
		path3.moveTo(halfWidth / 4, 0);
		path3.cubicTo(halfWidth / 4, 0, halfWidth / 4 - radius, halfHeigth * 2 / 3, 0, halfHeigth * 2 / 3);
		path3.cubicTo(-radius * 2, halfHeigth * 2 / 3 + radius * 2, -halfWidth + radius * 2, 0, -halfWidth / 2,
				-halfHeigth / 3);
		path3.cubicTo(-halfWidth / 2 + radius, -halfHeigth / 3 + radius, -halfWidth / 4, -halfHeigth / 4, 0, 0);

		Path path4 = new Path();
		path4.moveTo(halfWidth / 2, 0);
		path4.cubicTo(halfWidth / 2, 0, halfWidth + radius * 4, halfHeigth * 2 / 3, halfWidth / 2 - radius * 2,
				halfHeigth + radius * 4);
		path4.cubicTo(halfWidth / 2 - radius * 2, halfHeigth + radius * 4, -radius * 2, halfHeigth * 2 / 3, 0, 0);
		path4.moveTo(0, 0);

		paths.add(path1);
		paths.add(path2);
		paths.add(path3);
		paths.add(path4);
		for (Path path : paths) {
			measures.add(new PathMeasure(path, false));
			positions.add(new float[2]);
		}
		startTransX();
	}

	ValueAnimator animator;

	private void reset() {
		if (animator != null) {
			animator.cancel();
		}
		if (animator2 != null) {
			animator2.cancel();
		}
		for (int i = 0; i < positions.size(); i++) {
			float[] pos = positions.get(i);
			pos[0] = 0;
			pos[1] = 0;
		}
		factorSacle = 0;
		postInvalidate();
	}

	public void startTransX() {
		reset();

		factorSacle = 0;
		animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(1000);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = animation.getAnimatedFraction();
				for (int i = 0; i < measures.size(); i++) {
					float[] pos = positions.get(i);
					pos[0] = startXPositioon[i] * value;
				}
				postInvalidate();
				if (value >= 1) {
					startMove();
				}
			}
		});
		animator.start();
	}

	ValueAnimator animator2;

	private void startMove() {
		animator2 = ValueAnimator.ofFloat(0, 1);
		animator2.setDuration(2000);
		animator2.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = animation.getAnimatedFraction();
				for (int i = 0; i < measures.size(); i++) {
					PathMeasure pathMeasure = measures.get(i);
					float[] pos = positions.get(i);
					pathMeasure.getPosTan(value * pathMeasure.getLength(), pos, null);
				}
				if (value < 0.66) {
					factorSacle = value / 0.66f;
				} else {
					factorSacle = (1 - value) / (1 - 0.66f);
				}

				postInvalidate();
			}
		});
		animator2.start();
	}
}
