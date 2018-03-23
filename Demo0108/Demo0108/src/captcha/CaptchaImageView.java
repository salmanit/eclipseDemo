package captcha;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CaptchaImageView extends ImageView {

	public CaptchaImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initDefaultStrategy();
	}

	CaptchaStrategy mCaptchaStrategy;

	public void setmCaptchaStrategy(CaptchaStrategy mCaptchaStrategy) {
		this.mCaptchaStrategy = mCaptchaStrategy;
		assignment(mCaptchaStrategy);
	}

	private void initDefaultStrategy() {
		mCaptchaStrategy = new DefaultCaptchaStrategy(getContext());
		assignment(mCaptchaStrategy);
	}

	private Path path;
	private Path pathBlock;
	private Paint paintBlock;
	private Paint paintBlockBitmap;
	private Paint paintShader;
	private int blockWidth, blockHeight;

	private void assignment(CaptchaStrategy mCaptchaStrategy) {
		if (mCaptchaStrategy != null) {
			path = mCaptchaStrategy.getBlockPath();
			paintBlock = mCaptchaStrategy.getBlockPaint();
			paintBlockBitmap = mCaptchaStrategy.getBlockBitmapPaint();
			paintShader = mCaptchaStrategy.getBlockShaderPaint();
			blockWidth = mCaptchaStrategy.blockWidth;
			blockHeight = mCaptchaStrategy.blockHeight;
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		width = right - left;
		height = bottom - top;
		if (width != 0 && height != 0 && (startX == 0 || startY == 0)) {
			resetBlock();
		}
	}

	private void resetBlock() {
		// 抠图的位置，左右两边需要偏移1/20；
		startX = width / 20 + new Random().nextInt(width * 9 / 10 - blockWidth);
		startY = 10 + new Random().nextInt(height - blockHeight - 10);
		pathBlock = new Path();
		path.offset(startX, startY, pathBlock);
	}

	private void resetDefault() {
		resetBlock();
		bitmapBlock = null;
		currentMoveX = 0;
		postInvalidate();
	}
	private int width;
	private int height;
	private int startX, startY;// 抠图的起始坐标，左上角的
	private Bitmap bitmapBlock;
	private int currentMoveX;
	private Path pathShader = new Path();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (width == 0 || height == 0) {
			return;
		}
		if (bitmapBlock == null) {
			bitmapBlock = getClipBitmap();
//			bitmapBlock = getClipBitmap();
			
		}
		
		canvas.drawPath(pathBlock, paintBlock);
		canvas.drawBitmap(bitmapBlock, currentMoveX - startX, 0, null);
		pathBlock.offset(currentMoveX - startX, 0, pathShader);
		canvas.drawPath(pathShader, paintShader);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (bitmapBlock != null) {
			bitmapBlock.recycle();
			bitmapBlock = null;
		}
	}

	private Bitmap getClipBitmap() {
		Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_4444);
		Canvas c = new Canvas(b);
		c.clipPath(pathBlock);
		BitmapDrawable drawable=(BitmapDrawable) getDrawable();
//		Bitmap bb=drawable.getBitmap();
//		Rect srcR = new Rect(0, 0, bb.getWidth(), bb.getHeight());
//      RectF dstR = new RectF(0, 0, width, height);
//		c.drawBitmap(bb, srcR, dstR, new Paint());
		getDrawable().draw(c);
		return b;
	}

	public void updateProgress(int progress) {
		currentMoveX = (width - blockWidth) * progress / 100;
		postInvalidate();
		
	}
	public boolean checkResult() {
		boolean result=false;
		if (result=Math.abs(currentMoveX - startX) < 10) {
			if (mResultListener != null) {
				mResultListener.success();
			}
			currentMoveX=startX;
			postInvalidate();
		} else {
			if (mResultListener != null) {
				mResultListener.failed();
			}
			resetDefault();
			
		}
		return result;
	}
	
	public void bindWithSeekBar(final SeekBar sBar) {
		sBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(checkResult()) {
					sBar.setEnabled(false);
				}else {
					sBar.setProgress(0);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser) {
					updateProgress(progress*100/seekBar.getMax());
				}
				
			}
		});
	}
	
	
	
	
	private ResultListener mResultListener;

	public void setmResultListener(ResultListener mResultListener) {
		this.mResultListener = mResultListener;
	}

	public interface ResultListener {

		public void success();

		public void failed();
	}
}
