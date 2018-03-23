package event;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;

public class TouchEventViewGroup extends ViewGroup{
	private String tag=getClass().getSimpleName();
	public TouchEventViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.err.println(tag+"========onLayout======"+changed+"============="+l+"="+r+"=="+t+"==="+b);
		int top=0;
		for(int i=0;i<getChildCount();i++) {
			View child=getChildAt(i);
			if(child!=null) {
				LayoutParams params=(LayoutParams) child.getLayoutParams();
				top+=params.topMargin;
				child.layout(params.leftMargin, top, params.leftMargin+child.getMeasuredWidth(),top+child.getMeasuredHeight());
				top+=child.getMeasuredHeight()+params.bottomMargin;
			}
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.err.println(tag+"===========onMeasure===========h="+getMeasuredHeight()+"===w="+getMeasuredWidth());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mTotalLength=0;
		for(int i=0;i<getChildCount();i++) {
			View child=getChildAt(i);
			if(child!=null) {
				 measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
				//android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
				 
				 //下面这种是不考虑margin，也就不用自定义LayoutParams的简单使用
//				ViewGroup.LayoutParams lp=child.getLayoutParams();
//				final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
//		                getPaddingLeft() + getPaddingRight()+80, lp.width);
//		        final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
//		                getPaddingTop()+getPaddingBottom(), lp.height);
//
//		        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
				  MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				 mTotalLength+=child.getMeasuredHeight()+lp.bottomMargin+lp.topMargin;
			}
		}
		int heightSize = mTotalLength;

        // Check against our minimum height
        heightSize = Math.max(heightSize, getSuggestedMinimumHeight());
		int heightSizeAndState = resolveSizeAndState(heightSize, heightMeasureSpec, 0);
//        heightSize = heightSizeAndState & MEASURED_SIZE_MASK;
//        setMeasuredDimension(getMeasuredWidth(), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        setMeasuredDimension(getMeasuredWidth(),
                heightSizeAndState);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		System.err.println(tag+"=========dispatchTouchEvent======"+event.getAction());
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.err.println(tag+"=========onTouchEvent======"+event.getAction());
		
		return super.onTouchEvent(event);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		System.err.println(tag+"=========onInterceptTouchEvent======"+ev.getAction());
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		System.err.println(tag+"=========requestDisallowInterceptTouchEvent======"+disallowIntercept);
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}
	
	@Override
	protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
		// TODO Auto-generated method stub
		return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TouchEventViewGroup.class.getName();
    }
	
	public static class LayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         *
         * @see android.view.Gravity
         * 
         * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
         */
        public int gravity = -1;

        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

           
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int width, int height) {
            super(width, height);
        }

        /**
         * Creates a new set of layout parameters with the specified width, height
         * and weight.
         *
         * @param width the width, either {@link #MATCH_PARENT},
         *        {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height the height, either {@link #MATCH_PARENT},
         *        {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param gravity the gravity
         *
         * @see android.view.Gravity
         */
        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

		/**
		 * Copy constructor. Clones the width, height, margin values, and gravity of the
		 * source.
		 *
		 * @param source
		 *            The layout params to copy from.
		 */
		public LayoutParams(LayoutParams source) {
			super(source);

			this.gravity = source.gravity;
		}
    }
}
