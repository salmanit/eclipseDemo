package com.charlie.demo0108;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import widget.DotRotate;
import widget.TaiJiView;

public class ActivityAnimation extends ActivityBase{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		final DotRotate dotRotate=(DotRotate) findViewById(R.id.dotRotate1);
//		dotRotate.startAnimation();
//		dotRotate.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				dotRotate.cancelAnimation();
//				
//			}
//		}, 20000);
		
		TaiJiView taiJiView=(TaiJiView) findViewById(R.id.taiJiView1);
		RotateAnimation rotateAnimation=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		rotateAnimation.setDuration(2000);
//		rotateAnimation.setRepeatCount(2);
//		taiJiView.startAnimation(rotateAnimation);
		
	}
	
	
}
