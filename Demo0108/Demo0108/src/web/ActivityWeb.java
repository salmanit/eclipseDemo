package web;

import com.charlie.demo0108.ActivityBase;
import com.charlie.demo0108.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import widget.NormalProgressView;

public class ActivityWeb extends ActivityBase{
	
	
	private String url="https://www.jianshu.com/p/16713361bbd3";
	private CustomWebView wv;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_web);
//		wv=(CustomWebView) findViewById(R.id.wv);
//		wv.getSettings().setJavaScriptEnabled(true);
//			
//		wv.loadUrl(url);
		
		RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.layout_rv);
		LinearLayout linearLayout=(LinearLayout) findViewById(R.id.layout_linear);
		FrameLayout frameLayout=(FrameLayout) findViewById(R.id.layout_frame);
		
		final NormalProgressView p_rv=new NormalProgressView(relativeLayout, this);
		final NormalProgressView p_linear=new NormalProgressView(linearLayout, this);
		final NormalProgressView p_frame=new NormalProgressView(frameLayout, this);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast=Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP, 0, 0);
				toast.show();
			}
		});
		
		p_rv.show();
		
		p_linear.show();
		
		p_frame.show();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
			p_rv.hide();
			
			p_linear.hide();
			p_frame.hide();
				
			}
		}, 5000);
	}

}
