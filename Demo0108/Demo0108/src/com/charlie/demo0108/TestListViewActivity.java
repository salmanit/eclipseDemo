package com.charlie.demo0108;

import java.util.ArrayList;

import android.content.res.Resources.Theme;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import captcha.CaptchaImageView;
import captcha.CaptchaImageView.ResultListener;
import widget.ClipImageView;
import widget.SwipeCaptchaView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TestListViewActivity extends ActivityBase{
	
	private ListView lv;
	ToggleButton tgbtn1;
	ToggleButton tgbtn2;
	private SparseArray<MyTestAdapter> adapters=new SparseArray<TestListViewActivity.MyTestAdapter>();
	ArrayList<String>  lists1=new ArrayList<String>();
	ArrayList<String>  lists2=new ArrayList<String>();
	SwipeCaptchaView mSwipeCaptchaView;
    SeekBar mSeekBar;
    CaptchaImageView captchaImageView;
	@Override
	protected void onCreate(Bundle arg0) {
		setTheme(R.style.AppTheme2);
		super.onCreate(arg0);
		setContentView(R.layout.test_listview_activity);
		lv=(ListView) findViewById(R.id.lv);
		
		for(int i=0;i<10;i++) {
			lists1.add("task-----------"+i+"=="+getFilesDir());
			lists2.add("route-------------"+i+"=="+getCacheDir());
		}
		lv.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_for_app_infos, lv,false));
		lv.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_for_app_infos, lv,false));
		lv.setAdapter(mAdapter=new MyTestAdapter(lists1));
		
		tgbtn1=(ToggleButton) findViewById(R.id.tgbtn1);
		tgbtn2=(ToggleButton) findViewById(R.id.tgbtn2);
		
		tgbtn1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.err.println("============"+isChecked+"======="+adapters.get(1));
				if(isChecked) {
					tgbtn2.setChecked(false);
					MyTestAdapter adapter=adapters.get(1);
					if(adapter!=null) {
						mAdapter=adapter;
						
					}else {
						
						mAdapter=new MyTestAdapter(lists1);
						adapters.put(1, mAdapter);
					}
					lv.setAdapter(mAdapter);
				}else {
					lv.setAdapter(null);
				}
				 mSwipeCaptchaView.createCaptcha();
	                mSeekBar.setEnabled(true);
	                mSeekBar.setProgress(0);
			}
		});
		tgbtn2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked) {
					tgbtn1.setChecked(false);
					MyTestAdapter adapter=adapters.get(2);
					if(adapter!=null) {
						mAdapter=adapter;
						
					}else {
						
						mAdapter=new MyTestAdapter(lists2);
						adapters.put(2, mAdapter);
					}
					lv.setAdapter(mAdapter);
					
				}else {
					lv.setAdapter(null);
				}
			}
		});
		
		
//		WebView wv1=(WebView) findViewById(R.id.wv1);
//		wv1.getSettings().setJavaScriptEnabled(true);
//		wv1.loadUrl("http://192.168.0.132/OTA/1/0943/3_98/version.ini");
		mSeekBar=(SeekBar) findViewById(R.id.sb);
		mSwipeCaptchaView = (SwipeCaptchaView) findViewById(R.id.swipeCaptchaView);
		 captchaImageView=(CaptchaImageView) findViewById(R.id.captchaImageView1);
//		captchaImageView.bindWithSeekBar(mSeekBar);
		captchaImageView.setmResultListener(new ResultListener() {
			
			@Override
			public void success() {
				
				Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void failed() {
				
				Toast.makeText(getBaseContext(), "failed", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		mSwipeCaptchaView.setOnCaptchaMatchCallback(new SwipeCaptchaView.OnCaptchaMatchCallback() {
            @Override
            public void matchSuccess(SwipeCaptchaView swipeCaptchaView) {
                Toast.makeText(TestListViewActivity.this, "恭喜你啊 验证成功 可以搞事情了", Toast.LENGTH_SHORT).show();
                //swipeCaptcha.createCaptcha();
                mSeekBar.setEnabled(false);
            }

            @Override
            public void matchFailed(SwipeCaptchaView swipeCaptchaView) {
                Log.d("zxt", "matchFailed() called with: swipeCaptchaView = [" + swipeCaptchaView + "]");
                Toast.makeText(TestListViewActivity.this, "你有80%的可能是机器人，现在走还来得及", Toast.LENGTH_SHORT).show();
                swipeCaptchaView.resetCaptcha();
                mSeekBar.setProgress(0);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSwipeCaptchaView.setCurrentSwipeValue(progress);
                if(fromUser) {
                	captchaImageView.updateProgress(progress*100/seekBar.getMax());
				}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //随便放这里是因为控件
                mSeekBar.setMax(mSwipeCaptchaView.getMaxSwipeValue());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("zxt", "onStopTrackingTouch() called with: seekBar = [" + seekBar + "]");
                mSwipeCaptchaView.matchCaptcha();
                if(captchaImageView.checkResult()) {
					mSeekBar.setEnabled(false);
				}else {
					mSeekBar.setProgress(0);
				}
            }
        });
	}
String url="http://mmbiz.qpic.cn/mmbiz_jpg/trm5VMeFp9kuuGYcoeg06IYM4TSEE1eCEQXeTMTK6QP1yoQFOI0icxU80icWGhOq1UhDpp3suJhLNHBzR4qticicaw/640";
	private MyTestAdapter mAdapter;
	

	
	public class MyTestAdapter extends BaseAdapter{
		
		public MyTestAdapter(ArrayList<String> lists) {
			
			this.lists=lists;
		}
		ArrayList<String> lists=new ArrayList<String>();
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(mBackgroundDrawable==null) {
				initSomething();
			}
			if(convertView==null) {
				convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_app_infos, parent,false);
				if(mBackgroundDrawable!=null) {
					convertView.setBackground(mBackgroundDrawable);
				}
			}
			TextView tView=(TextView) convertView.findViewById(R.id.tv_info);
			tView.setText(getItem(position));
			return convertView;
		}
		private  Drawable mBackgroundDrawable=null;
		private void initSomething() {
			TypedValue themedBackground = new TypedValue();
		      if (getTheme().resolveAttribute(R.attr.listviewBackgroundTwocolour, themedBackground, true))
		      {
		        mBackgroundDrawable=new BitmapDrawable(BitmapFactory.decodeResource(getResources(), themedBackground.resourceId));
		      }
		      else
		      {
		        mBackgroundDrawable = null;
		      }
		}
	
	}
}
