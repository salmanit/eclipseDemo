package widget;

import java.util.Random;

import com.charlie.demo0108.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable.ShaderFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ClipImageView extends ImageView{

	public ClipImageView(Context context) {
		super(context);
		initPaint();
	}

	public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public ClipImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	private void initPaint() {
//		setLayerType(LAYER_TYPE_HARDWARE, new Paint());
		paintGray.setColor(Color.parseColor("#88000000"));
		paintGray.setStyle(Style.FILL);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		paintWhiteLine.setColor(Color.WHITE);
		paintWhiteLine.setStrokeWidth(2);
		paintWhiteLine.setStyle(Style.STROKE);
		paintWhiteLine.setPathEffect(new DashPathEffect(new float[] {4,10}, 0));
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.err.println("width==="+getWidth()+"==="+getHeight());
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(getWidth()==0||getHeight()==0) {
			return;
		}
		if(!init) {
			init=true;
			initClipPosition();
			
			bitmap=getClipBitmap();
		}
		canvas.drawPath(path, paintGray);
		if(bitmap!=null) {
			canvas.drawBitmap(bitmap,currentMoveX-x, 0, paint);
			path.offset(currentMoveX-x, 0,pathMove);
			canvas.drawPath(pathMove, paintWhiteLine);
		}
	}
	private Bitmap getClipBitmap() {
		Bitmap b=Bitmap.createBitmap(getWidth(),getHeight(),Config.ARGB_4444);
		Canvas c=new Canvas(b);
		getDrawable().setBounds(0, 0, getWidth(), getHeight());
        c.clipPath(path);
        getDrawable().draw(c);
		return b;
	}
	private Bitmap bitmap;
	Path pathMove=new Path();
	Paint paintWhiteLine=new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint paintGray=new Paint(Paint.ANTI_ALIAS_FLAG);
	int x,y,length,currentMoveX;
	Path path=new Path();
	boolean init=false;
	Paint paint=new Paint();
	private void initClipPosition() {
		int width=getWidth();
		int heigth=getHeight();
		 length=heigth/6;
		 y=heigth/2-length/2;//抠图的left top位置，我们的抠图按照正方形处理了,抠图的大小按照高度的1/4算
		//x的取值范围，左右两边留1/10，所以取值范围就是1/10到 9/10-length
		 x=width/10+new Random().nextInt(width*9/10-length);
		System.err.println("x======"+x+" y=="+y);
		path.moveTo(x, y);
		path.lineTo(x+length/5, y);
		path.lineTo(x+length/5, y-length/5);
		path.lineTo(x+length*2/5, y-length/5);
		path.lineTo(x+length*2/5, y);
		
		
		
		path.lineTo(x+length, y);
		path.lineTo(x+length, y+length);
		path.lineTo(x, y+length);
		path.close();
		
	}
	public void reset() {
		init=false;
		postInvalidate();
	}
	private void showToast(String msg) {
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		
	}
	private SeekBar sBar;
	public void bindWithSb(SeekBar sb) {
		sBar=sb;
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(Math.abs(currentMoveX-x)<10) {
					//success
					showToast("success");
					if(sBar!=null) {
						sBar.setEnabled(false);
						currentMoveX=x;
					}
				}else {
					//failed
					showToast("failed");
					sBar.setProgress(0);
					currentMoveX=0;
				}
				postInvalidate();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser) {
					currentMoveX=(getWidth()-length)*progress/100;
					System.err.println("progress============"+progress);
					postInvalidate();
				}
				
			}
		});
	}
}
