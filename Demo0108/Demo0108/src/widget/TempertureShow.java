package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TempertureShow extends View {

	public TempertureShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSomeThing();
	}

	private Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);//����Բ�Ļ���
	private Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);//�����Ļ���
	private Paint paintLineShort = new Paint(Paint.ANTI_ALIAS_FLAG);//����ߵĻ���
	private Paint paintText = new Paint();//��ʼ�������¶����ֵĻ���
	private Paint paintTextCenter = new Paint();//�������ֻ���
	private Paint paintTextBottom = new Paint();//�ײ����ֻ���
	private int colorBg = Color.BLUE;
	private int colorFore = Color.RED;
	private int colorText = Color.RED;
	private int colorTextCenter = Color.RED;
	private int colorTextBottom = Color.BLACK;
	private int rCircle;//����Բ�İ뾶
	private int rLine;//�����İ뾶
	private int rLineShort;//��������ڵ�Բ�뾶
	private RectF ovalCircle = new RectF();//�м��Բ
	private RectF oval = new RectF();//ԲȦ����
	private RectF ovalLineShort = new RectF();//����ߵ�rect�������ǵ���һ��Բ������
	RectF ovalText = new RectF();//��ʼ�¶Ⱥͽ����¶ȣ����ֻ������ڵ�rect
	private int intervalAngle;//�ֳ�split�ݵļල
	private int minTemperture = 15;// ��Сֵ
	private int maxTemperTure = 30;// ���ֵ
	int split = 90;//��135�ȵ�405�ȷֳ�90��
	Path pStart = new Path();//��ʼ�¶ȵ�����·��
	Path pEnd = new Path();//�����¶ȵ�����·��
	private int rMax;//���뾶
	private Rect bounds = new Rect();//���������м����ֵĿ�ȵ�
	private double currentAngle = 135;
	Path pathArrow = new Path();//��ɫ�ļ�ͷ
	private void initSomeThing() {
		paintCircle.setColor(Color.WHITE);
		paintCircle.setStyle(Style.FILL_AND_STROKE);

		paintLine.setColor(colorBg);
		paintLine.setStyle(Style.STROKE);

		paintLineShort.setColor(colorBg);
		paintLineShort.setStrokeWidth(10);
		paintLineShort.setStyle(Style.STROKE);

		paintText.setColor(colorText);
		paintText.setTextSize(22);

		paintTextCenter.setColor(colorTextCenter);
		paintTextCenter.setTextSize(60);
		paintTextCenter.setTextAlign(Align.CENTER);

		paintTextBottom.setColor(colorTextBottom);
		paintTextBottom.setTextAlign(Align.CENTER);
		paintTextBottom.setTextSize(20);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (right != left && top != bottom) {
			int squareL = Math.min(getWidth(), getHeight());
			rMax = squareL / 2;
			rCircle = rMax / 2;// �м�Բ�İ뾶
			ovalCircle.set(-rCircle, -rCircle, rCircle, rCircle);
			rLine = rCircle + rCircle / 3;// ���ߵİ뾶
			rLineShort = rCircle + rCircle * 2 / 3;// ���ߵİ뾶
			oval.set(-rLine, -rLine, rLine, rLine);

			intervalAngle = 270 / split;
			ovalLineShort.set(-rLineShort, -rLineShort, rLineShort, rLineShort);
			ovalText.set(-rMax, -rMax, rMax, rMax);
			pStart.reset();
			pEnd.reset();
			pStart.addArc(ovalText, 135 + 20, -20);
			pEnd.addArc(ovalText, 45, -20);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getWidth() / 2, getHeight() / 2);

		// ��ʼ��Բ
//		canvas.drawCircle(0, 0, rCircle, paintCircle);
		canvas.save();
		canvas.rotate((float) (currentAngle - 90));
		pathArrow.addArc(ovalCircle, 90+ 6, 360-12);
		pathArrow.lineTo(0, rCircle + 9);
		pathArrow.close();
		canvas.drawPath(pathArrow, paintCircle);
		canvas.restore();

		canvas.drawArc(oval, 135, 270, false, paintLine);
		paintLineShort.setColor(colorFore);
		for (int i = 0; i <= split; i++) {
			float startAngle = (float) (135 + intervalAngle * i - 0.5);
			if (startAngle > currentAngle) {
				paintLineShort.setColor(colorBg);
			}
			canvas.drawArc(ovalLineShort, startAngle, 1, false, paintLineShort);
		}
		if(!isInEditMode()) {
			paintText.setTextAlign(Align.RIGHT);
			canvas.drawTextOnPath("" + minTemperture, pStart, 0, 0, paintText);
			paintText.setTextAlign(Align.LEFT);
			canvas.drawTextOnPath("" + maxTemperTure, pEnd, 0, 0, paintText);
		}
		

		canvas.drawText("����¶�����", 0, rLine, paintTextBottom);
		int currentTemp = (int) ((currentAngle - 135) / 270 * (maxTemperTure - minTemperture) + minTemperture + 0.5f);
		paintTextCenter.getTextBounds("99", 0, 2, bounds);
		canvas.drawText("" + currentTemp, 0, bounds.height() / 2, paintTextCenter);
		canvas.drawText("��", bounds.width() / 2 + 12, bounds.height() / 2, paintTextCenter);
		// canvas.drawLine(0, -rMax, 0, rMax, paintLineShort);
		canvas.restore();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		double currentAngle = calculateAngle(event.getX(), event.getY());
		if (currentAngle >= 135 && currentAngle <= 405) {
			this.currentAngle = currentAngle;
			postInvalidate();
		} else {
			if (currentAngle - this.currentAngle > 0 && currentAngle - this.currentAngle < 45) {
				this.currentAngle = 405;
				postInvalidate();
			}
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;

		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			break;
		}
		return true;
	}

	private void handleTouch() {

	}

	// ���ݴ����ĵ����Ƕȡ��Ի������ĵ�Ϊԭ�㣬�Ƕȴ���135�ȡ�
	private double calculateAngle(float x, float y) {
		float pX = x - getWidth() / 2f;
		float pY = y - getHeight() / 2f;
		if (Math.sqrt(pX * pX + pY * pY) < rCircle) {
			return 0;// ��ָ��ԲȦ�д���ɶҲ����
		}
		if (pX == 0) {
			return y > getHeight() / 2 ? 90 : 270;
		} else if (pY == 0) {
			return x > getWidth() / 2 ? 360 : 180;
		}
		double atan = Math.atan(Math.abs(pY) / Math.abs(pX));
		double angle = atan * 180 / Math.PI;
		// System.err.println("pi===="+atan+"====="+angle);
		if (x < getWidth() / 2) {

			// 90�ȵ�270��֮��
			if (y > getHeight() / 2) {
				// 90��180
				return 180 - angle;
			} else {
				// 180---270
				return 180 + angle;
			}
		} else {
			if (y > getHeight() / 2) {
				// 360��450
				return 360 + angle;
			} else {
				// 270---360
				return 360 - angle;
			}
		}

	}
}
