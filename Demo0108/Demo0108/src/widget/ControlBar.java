package widget;

import com.charlie.demo0108.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ControlBar extends RelativeLayout {

	public enum Alignment {
		LEFT, CENTER, RIGHT
	}

	private static final int ID_TITLE = 1;
	private int defaultHeight = 50;
	private LinearLayout mLeftLayout, mCenterLayout, mRightLayout;
	private int mLeftButtonID;// 默认的后退按钮的id
	private boolean mLeftButtonAdded;// 默认的后退按钮是否已经添加进布局
	private int mLastID = ID_TITLE + 1;
	private boolean mIsOrientationVertical;// 默认的方向是水平的。
	private int textAppearance, textSize;
	private ColorStateList colorStateList;
	private int screenWith;
	private int screenHeight;

	private int dp2px(int dp) {
		return (int) (getContext().getResources().getDisplayMetrics().density * dp);
	}

	public ControlBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		final int[] styledRes = new int[] { android.R.attr.textSize, android.R.attr.textColor, android.R.attr.orientation, android.R.attr.title,
				android.R.attr.textAppearance };
		TypedArray array = context.obtainStyledAttributes(attrs, styledRes);
		System.err.println("ControlBar==========="+array.getIndexCount()+"===="+array.length());
		String title=null ;
		for(int i=0;i<array.getIndexCount();i++) {
			int index=array.getIndex(i);
			switch (index) {
			case 0:
				textSize = array.getDimensionPixelSize(index, 15);
				break;
			case 1:
				colorStateList = array.getColorStateList(index);
				break;
			case 2:
				mIsOrientationVertical = array.getInt(index, LinearLayout.HORIZONTAL) == LinearLayout.VERTICAL;
				break;
			case 3:
				title = array.getString(index);
				break;
			case 4:
				textAppearance = array.getResourceId(index, 0);
				break;
			default:
				break;
			}
		}
		
		array.recycle();
		mLeftLayout = createSubLayout(Alignment.LEFT);
		mCenterLayout = createSubLayout(Alignment.CENTER);
		mRightLayout = createSubLayout(Alignment.RIGHT);
		screenWith = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		if (!TextUtils.isEmpty(title)) {
			setTitle(title);
		}
		addLeftButton();
	}
	private void addLeftButton() {
		if(mLeftButtonAdded) {
			return;
		}
		final OnClickListener listener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 ((Activity)v.getContext()).onBackPressed();
				
			}
		};
		mLeftButtonID=addAction(R.drawable.adp_global_back_bton, Alignment.LEFT, listener);
		mLeftButtonAdded=true;
	}
	private LinearLayout createSubLayout(Alignment alignment) {
		LinearLayout layout = new LinearLayout(getContext());
		int height = dp2px(defaultHeight);
		LayoutParams params;
		if (mIsOrientationVertical) {
			params = new LayoutParams(height, LayoutParams.WRAP_CONTENT);
			layout.setGravity(Gravity.CENTER_HORIZONTAL);
		} else {
			params = new LayoutParams(LayoutParams.WRAP_CONTENT, height);
			layout.setGravity(Gravity.CENTER_VERTICAL);
		}

		switch (alignment) {
		case LEFT:
			params.addRule(mIsOrientationVertical ? RelativeLayout.ALIGN_PARENT_TOP : RelativeLayout.ALIGN_PARENT_LEFT);
			break;
		case CENTER:
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			break;
		case RIGHT:
			params.addRule(
					mIsOrientationVertical ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_RIGHT);
			break;
		}

		addView(layout, params);
		return layout;
	}

	private LinearLayout getSubLayout(Alignment alignment) {
		if (alignment == Alignment.LEFT) {
			return mLeftLayout;
		} else if (alignment == Alignment.CENTER) {
			return mCenterLayout;
		} else {
			return mRightLayout;
		}
	}

	public void addView(View v, Alignment alignment) {
		LinearLayout layout = getSubLayout(alignment);
		if (alignment == Alignment.CENTER) {
			Button button = (Button) findViewById(ID_TITLE);
			if (button != null) {
				layout.removeView(button);
			}
		}

		layout.addView(v);
	}

	public void setTitle(String title) {
		if (mIsOrientationVertical) {
			return;
		}
		Button button = (Button) findViewById(ID_TITLE);
		boolean removeTitle = TextUtils.isEmpty(title);
		if (removeTitle) {
			if (button != null) {
				getSubLayout(Alignment.CENTER).removeView(button);
			}
		} else {
			if (button == null) {
				button = new Button(getContext());
				button.setId(ID_TITLE);
				button.setClickable(false);
				button.setBackgroundColor(Color.TRANSPARENT);
				applyTitleStyle(button);
				addView(button, Alignment.CENTER);
			}
			button.setText(title);
		}
	}

	private void applyTitleStyle(Button button) {
		if (textAppearance > 0) {
			button.setTextAppearance(getContext(), textAppearance);
		}
		if (colorStateList != null) {
			button.setTextColor(colorStateList);
		}
		button.setTextSize(textSize);
		
	}
	  public int addAction(int drawableId, Alignment alignment, OnClickListener listener)
	  {
	    ImageButton button = createImageButton(drawableId, listener);
	    addView(button, alignment);
	    return button.getId();
	  }

	public int addActionScale(int drawableId, Alignment alignment, OnClickListener listener) {
		ImageButton button = createImageButton(drawableId, listener);
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) button.getLayoutParams();
		if (mIsOrientationVertical) {
			params.height = screenHeight / 9;
		} else {
			params.width = screenWith / 9;
		}
		button.setLayoutParams(params);
		addView(button, alignment);
		return button.getId();
	}

	private ImageButton createImageButton(int drawableId, OnClickListener listener) {
		ImageButton button = new ImageButton(getContext());
		button.setId(mLastID++);
		button.setImageResource(drawableId);
		applyStyle(button);

		if (listener != null) {
			button.setOnClickListener(listener);
		} else {
			button.setClickable(false);
		}
		return button;
	}

	private void applyStyle(ImageButton button) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) button.getLayoutParams();
		if(params==null) {
			if(mIsOrientationVertical) {
				
				params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
			}else {
				
				params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
			}
			button.setLayoutParams(params);
		}
		button.setBackgroundResource(R.drawable.default_actioin_bg);
	}
	
	  public void showBackButton(final boolean visible)
	  {
	    if (mLeftButtonAdded)
	    {
	      showAction(mLeftButtonID, visible);
	    }
	  }
	  public void showAction(int actionId, boolean visible)
	  {
	    final View view = findViewById(actionId);
	    if (view != null)
	    {
	      view.setVisibility(visible ? View.VISIBLE : View.GONE);
	    }
	  }
}
