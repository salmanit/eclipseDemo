package captcha;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;

public class DefaultCaptchaStrategy extends CaptchaStrategy{

	public DefaultCaptchaStrategy(Context ctx) {
		super(ctx);
	}

	@Override
	public Path getBlockPath() {
		Path path=new Path();
		int width=dp2px(60);
		int heigth=dp2px(60);
		blockWidth=width;
		blockHeight=heigth;
		path.moveTo(0, 0);
		path.lineTo(width/3, 0);
		 path.cubicTo(width/3,0,width/2,-width/4,width*2/3,0);
		 path.lineTo(width, 0);
		 
		 path.lineTo(width, heigth);
		 
		 path.lineTo(0, heigth);
		 path.close();
         
		return path;
	}

	@Override
	public Paint getBlockPaint() {
		Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
		paint.setColor(Color.parseColor("#88000000"));
		paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
		return paint;
	}

	@Override
	public Paint getBlockBitmapPaint() {
		Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setColor(Color.BLACK);
		paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
		return paint;
	}

	@Override
	public Paint getBlockShaderPaint() {
		Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
		paint.setStyle(Style.STROKE);
		paint.setPathEffect(new DashPathEffect(new float[] {4,5}, 0));
		return paint;
	}

}
