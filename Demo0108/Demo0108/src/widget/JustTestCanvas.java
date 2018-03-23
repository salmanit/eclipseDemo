package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class JustTestCanvas extends View{

	public JustTestCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(2);
		
		paintText.setTextSize(20);
		paintText.setColor(Color.BLACK);
	}

	Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint paintText=new Paint(Paint.ANTI_ALIAS_FLAG);
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(getWidth()/2, getHeight()/2);
		
		canvas.drawLine(0, -getHeight()/2, 0, getHeight()/2, paint);
		paintText.setTextAlign(Align.RIGHT);
		canvas.drawText("right", 0, -33, paintText);
		
		paintText.setTextAlign(Align.LEFT);
		canvas.drawText("left", 0, 33, paintText);
		
		
		
		canvas.rotate(135-90);
		for(int i=0;i<60;i++) {
			canvas.drawLine(0, getHeight()/2-10, 0, getHeight()/2-2, paint);
			canvas.rotate(4.5f);
		}
		canvas.restore();
		Rect rect=new Rect();
	}
}
