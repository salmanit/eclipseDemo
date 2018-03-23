package widget;

import com.charlie.demo0108.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import bean.ShowTextParent;

public class FlowStepShow2 extends View {

	public FlowStepShow2(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSomeThing();
	}

	private boolean bottomText = true;// 文字是否在底部显示，默认在底部
	private boolean clickable = true;// 是否可选择
	private int checkedPosition;// 默认的选中位置
	private Paint paintLine = new Paint();
	private Paint paintText = new Paint();
	private int textHeight;
	private int drawablePadding = 5;
	private Bitmap bitmap1, bitmap2, bitmap3;

	private void initSomeThing() {
		paintText.setTextSize(20);
		paintText.setColor(Color.WHITE);
		paintText.setTextAlign(Align.CENTER);
		Rect bounds = new Rect();
		paintText.getTextBounds("您好", 0, 2, bounds);
		textHeight = bounds.height();
		bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_passed);
		bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_target);
		bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_normal);

	}

	ArrayList<ShowTextParent> data = new ArrayList<ShowTextParent>();
	private int lineY;
	private int textY;
	private int length;
	private int interval;

	public void setClick(boolean click) {
		clickable = click;
		postInvalidate();
	}

	public void setData(List<ShowTextParent> lists, int position) {
		data.clear();
		data.addAll(lists);
		checkedPosition = position;
		postInvalidate();
	}

	public void setData(List<ShowTextParent> lists) {
		setData(lists, 0);
	}

	public void setData(ShowTextParent[] arr, int position) {
		data.clear();
		if (arr != null) {
			data.addAll(Arrays.asList(arr));
		}
		postInvalidate();
	}

	public void setData(ShowTextParent[] arr) {
		setData(arr, 0);
	}

	public void setTextBottom(boolean bottom) {
		bottomText = bottom;
		postInvalidate();
	}

	public int getCheckedPosition() {
		return checkedPosition;
	}

	public void setCheckedPosition(int checkedPosition) {
		if (checkedPosition != this.checkedPosition) {
			this.checkedPosition = checkedPosition;
			postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(),
				textHeight * 2 + drawablePadding + getPaddingTop() + getPaddingBottom());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		lineY = getPaddingTop() + textHeight / 2;
		textY = getPaddingTop() + textHeight * 2 + drawablePadding;
		if (!bottomText) {
			lineY = getPaddingTop() + textHeight + drawablePadding + textHeight / 2;
			textY = getPaddingTop() + textHeight;
		}
		length = data.size();
		if (length > 0) {
			interval = (getWidth() - getPaddingLeft() - getPaddingRight()) / length;
			for (int i = 0; i < data.size(); i++) {
				int x = interval / 2 + interval * i;
				canvas.drawText(data.get(i).getShowText(), x, textY, paintText);

				Bitmap bitmap = getBitmap(i);
				canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, lineY - bitmap.getHeight() / 2, null);
				if (i > 0) {
					paintLine.setColor(i <= checkedPosition ? Color.WHITE : Color.GRAY);
					canvas.drawLine(x - interval + getBitmap(i - 1).getWidth() / 2, lineY, x - bitmap.getWidth() / 2,
							lineY, paintLine);
				}
			}
		}

	}

	private Bitmap getBitmap(int i) {
		if (i < checkedPosition) {
			return bitmap1;
		} else if (i > checkedPosition) {
			return bitmap3;
		} else {
			return bitmap2;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			if (clickable) {
				int position = getClickPosition(event);
				if (position >= 0) {
					if (listener != null && position != checkedPosition) {
						listener.positionChanged(position);
					}
					checkedPosition = position;
					postInvalidate();
				}
			}

			return true;
		}
		return super.onTouchEvent(event);
	}

	private int getClickPosition(MotionEvent event) {
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				int x = interval / 2 + interval * i;
				RectF rect = new RectF(x - 20, lineY - 20, x + 20, lineY + 20);
				if (rect.contains(event.getX(), event.getY())) {
					return i;
				}
			}
		}
		return -1;
	}

	private StateChangeListener listener;

	public void setListener(StateChangeListener listener) {
		this.listener = listener;
	}

	public interface StateChangeListener {
		public void positionChanged(int position);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		recycleBitmap(bitmap1);
		recycleBitmap(bitmap2);
		recycleBitmap(bitmap3);
	}
	private void recycleBitmap(Bitmap bitmap) {
		if(bitmap!=null&&!bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
}
