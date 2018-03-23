package widget;

import com.charlie.demo0108.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawBitmapPartView extends View{

	public DrawBitmapPartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
	Bitmap bitmap;
	private void initPaint() {
		paint.setStyle(Style.FILL_AND_STROKE);
		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.i_settings_wifi_4);
//		paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
//		int radius=bitmap.getWidth()/2;
//		oval.set(-radius, -radius, radius, radius);
		paint.setShader(new BitmapShader(bitmap, TileMode.MIRROR, TileMode.MIRROR));
//		paint.setShadowLayer((float) Math.sqrt(radius*radius*2), -radius, -radius, 0);
		p.setTextSize(40);
		p.setShadowLayer(2, 1, 1, Color.YELLOW);
		p.setColor(Color.parseColor("#55000000"));
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		radius=Math.min(getWidth()/2, getHeight()/2)*2/3;
		radius=getWidth();
		oval.set(0,0, radius, radius);
		
		System.err.println("========="+bitmap.getWidth() +"//"+getMeasuredWidth());
		float scale=getMeasuredWidth()/bitmap.getWidth();
		Matrix matrix=new Matrix();
		matrix.setScale(scale, scale);
		if(scale!=1) {
//			bitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//			paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		
	}int[] colors= {Color.RED,Color.BLUE};
	int sweepAngle;
	int radius;
	RectF oval=new RectF();
	Paint p=new Paint();
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(radius==0) {
			return;
		}
		canvas.save();
//		canvas.translate(getWidth()/2, getHeight()/2);
//		canvas.drawRect(oval, p);
//		switch(type) {
//		case 0://扫把
//			paint.setShader(new SweepGradient(0, 0, Color.RED,Color.BLUE));;
//			break;
//		case 1://线性
//			paint.setShader(new LinearGradient(-radius,-radius,radius,radius, colors, null, TileMode.CLAMP));
//			break;
//		case 2://彩虹
//			paint.setShader(new RadialGradient(0, 0, radius, Color.RED,Color.BLUE, TileMode.CLAMP));
//			break;
//		case 3://组合的
//			paint.setShader(new ComposeShader(new SweepGradient(0, 0, Color.RED,Color.BLUE),
//					new LinearGradient(-radius,-radius,radius,radius, colors, null, TileMode.CLAMP)
//					,PorterDuff.Mode.ADD));
//			break;
//		case 4://图片
//			paint.setShader(new BitmapShader(bitmap, TileMode.MIRROR, TileMode.MIRROR));
//			
//			break;
//		}
		
//		canvas.drawRect(oval, paint);
//		canvas.drawArc(oval, 270, -sweepAngle, true, paint);
//		canvas.restore();
//		sweepAngle+=5;
//		postInvalidateDelayed(200);
//		
//		canvas.drawText("内号", -getWidth()/2, 0, p);
		
//		canvas.drawRect(oval, paint);
	}
	int type=0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN) {
			type++;
			type%=5;
			postInvalidate();
		}
		return super.onTouchEvent(event);
	}
}
