package widget;

import com.charlie.demo0108.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import junit.framework.Assert;

public class NormalProgressView {

	private View mViewAdd;
	private TextView mTv_toast;
	private ViewGroup mViewRoot;
	private boolean mIsOpen;

	public NormalProgressView(ViewGroup viewGroup, Activity activity) {
		Assert.assertNotNull(viewGroup);
		Assert.assertNotNull(activity);
		mViewAdd = LayoutInflater.from(activity).inflate(R.layout.view_normal_progress, null);
		if (viewGroup instanceof LinearLayout) {
			mViewAdd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
		} else if (viewGroup instanceof RelativeLayout) {
			mViewAdd.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT));
		} else if (viewGroup instanceof FrameLayout) {
			mViewAdd.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT));
		}

		mTv_toast = (TextView) mViewAdd.findViewById(R.id.progressText);
		mViewRoot = viewGroup;
		mIsOpen = false;
	}

	public void setText(int resId) {
		if (mTv_toast != null) {
			mTv_toast.setText(resId);
		}
	}

	public void setText(String text) {
		if (mTv_toast != null) {
			mTv_toast.setText(text);
		}
	}

	public void setVisibility(boolean visible) {
		if (visible) {
			show();
		} else {
			hide();
		}
	}

	public void show() {
		if (!mIsOpen) {
			mIsOpen = true;
			int index=-1;
			if(mViewRoot instanceof LinearLayout) {
				index=0;
			}
			mViewRoot.addView(mViewAdd,index);
		}
	}

	public void hide() {
		if (mIsOpen) {
			mIsOpen = false;
			mViewRoot.removeView(mViewAdd);
		}
	}

	public boolean isOpen() {
		return mIsOpen;
	}
}
