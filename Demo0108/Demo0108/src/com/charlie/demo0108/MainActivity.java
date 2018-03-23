package com.charlie.demo0108;


import java.io.File;
import java.util.ArrayList;

import com.charlie.demo0108.java.ActivityJava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import bean.Student;
import event.ActivityTouchEventTest;
import preference.ActivityPreferenceSetting;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import testlifecycle.ActivityLife1;
import testlifecycle.ActivityLife2;
import weather.ActivityWeather;
import web.ActivityWeb;

@SuppressLint("NewApi")
public class MainActivity extends ActivityBase {
	ArrayList<AppInfo> infos = new ArrayList<AppInfo>();
	String[] classTitle= {"测试类","loading测试","4个小球，battery",
			"listview清空测试,图形验证码","生命周期测试，自定义温度仪表盘","fragment带参数测试",
			"测试触摸事件的流程","爱心表白","天气",
			"preference Learn","java类测试","自定义过度动画",
			};
	Class[] cla= {ActivityFunctionTest.class,ActivityWeb.class,Activity4Balls.class,
			TestListViewActivity.class,ActivityLife1.class,ActivityLife2.class
			,ActivityTouchEventTest.class,ActivityHeartView.class,ActivityWeather.class
			,ActivityPreferenceSetting.class,ActivityJava.class,ActivityAnimation.class,
			};
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		System.err.println("onCreate=====================");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(adapter);

		findViewById(R.id.btn_system).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				infos = ApplicationInfoUtil.getAllSystemProgramInfo(v.getContext());
				adapter.notifyDataSetChanged();

			}
		});
		findViewById(R.id.btn_non_system).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				infos = ApplicationInfoUtil.getAllNonsystemProgramInfo(v.getContext());
				adapter.notifyDataSetChanged();

			}
		});
		findViewById(R.id.btn_all).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<AppInfo> allInfos = ApplicationInfoUtil.getAllProgramInfo(v.getContext());
				infos.clear();
				for (AppInfo temp : allInfos) {
					if (temp.packageName.contains("com.magellan") || temp.packageName.contains("com.mitac")) {

						infos.add(temp);
					}
				}
				adapter.notifyDataSetChanged();

			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// try {
				// PackageManager packageManager = getPackageManager();
				// Intent intent=new Intent();
				// intent =
				// packageManager.getLaunchIntentForPackage(infos.get(position).packageName);
				// startActivity(intent);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
			}
		});

		
		

		//
		// final StatFs stat = new StatFs(new
		// File(Environment.getExternalStorageDirectory(),"Download").getAbsolutePath());
		// tv_show.setText("getAvailableBlocks=="+stat.getAvailableBlocks()+"===getBlockCount="+stat.getBlockCount()
		// +"====getAvailable Bytes ="+stat.getAvailableBytes()
		// +"=======getBlockSize="+stat.getBlockSize()+"===getFreeBlocks="+stat.getFreeBlocks()
		// +"=====getFree Bytes="+stat.getFreeBytes()+"==

//		HandlerThread thread = new HandlerThread("test thread");
//		thread.start();
		

		
		
		final ToggleButton toggleButton=(ToggleButton) findViewById(R.id.toggleButton1);
		System.err.println("oncreate==================="+toggleButton.isChecked());
		toggleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
			}
		});
		
		
		
		initActivitySkipInfo();
		
	}

	private void initActivitySkipInfo() {
		ListView lv2=(ListView) findViewById(R.id.lv2);
		BaseAdapter adapter2=new BaseAdapter() {
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if(convertView==null) {
					convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_skip, parent,false);
					try {
						TypedValue value=new TypedValue();
						getTheme().resolveAttribute(android.R.attr.selectableItemBackground, value, true);
						convertView.setBackground(getDrawable(value.resourceId));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				TextView tv_index=(TextView) convertView.findViewById(R.id.tv_index);
				TextView tv_title=(TextView) convertView.findViewById(R.id.tv_title);
				tv_index.setText("p"+(position+1));
				tv_title.setText(classTitle[position]);
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						getMyApplication().storeStudent(new Student("sal", "sh", 22, "qh"));
					startActivity(new Intent(v.getContext(),cla[position]));
						
					}
				});
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return cla.length;
			}
		};
		lv2.setAdapter(adapter2);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		 final Locale locale = Locale.getDefault();
//	      String mLastLanguage = locale.getLanguage();
//	      String mLastCountry = locale.getCountry();
//	    
//	      System.err.println("mLastLanguage=============="+mLastLanguage+"=========="+mLastCountry);
//	      
//	      String[] countries = Locale.getISOCountries();
//	      String[] languages=Locale.getISOLanguages();
//	      System.err.println("countries["+countries.length+"]============"+Arrays.toString(countries));
//	      System.err.println("getISOLanguages["+languages.length+"]============"+Arrays.toString(languages));
//	      Locale[] avalilable= Locale.getAvailableLocales();
//	      if(avalilable!=null) {
//	    	 for(Locale l:avalilable) {
//	    		 System.err.println("Locale============"+l.getCountry()+"==="+l.getLanguage());
//	    		 //打印结果，country可能是空的，
//	    	 }
//	     }
	}




	private void testConcatPath() {
		String dir = "a/b/c/d/e";
		String path = "../../maps/mapload.xml";
		int lastIndex = path.lastIndexOf("../");
		System.out.println("lastinde====" + lastIndex);
		String suffix = path.substring(lastIndex + 3, path.length());
		File file = new File(dir).getParentFile().getParentFile();
		System.err.println("=========" + new File(file, suffix));
	}

	private ListView lv;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private BaseAdapter adapter = new BaseAdapter() {
		
		private  Drawable mBackgroundDrawable=null;
		private void initSomething() {
			TypedValue themedBackground = new TypedValue();
		      if (getTheme().resolveAttribute(R.attr.listviewBackgroundTwocolour, themedBackground, true))
		      {
//		        mBackgroundDrawable = ContextCompat.getDrawable(MainActivity.this, themedBackground.resourceId);
		        mBackgroundDrawable=getDrawable(themedBackground.resourceId);
		      }
		      else
		      {
		        mBackgroundDrawable = null;
		      }
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_app_infos, parent,
						false);

			}
			if(mBackgroundDrawable==null) {
				initSomething();
			}
			AppInfo info = getItem(position);
			ImageView iView = (ImageView) convertView.findViewById(R.id.iv_logo);
			TextView tView = (TextView) convertView.findViewById(R.id.tv_info);
			iView.setImageDrawable(info.appIcon);
			tView.setText(info.appName + "\n" + info.packageName + "\n" + info.versionName + "/" + info.versionCode);
			
			{
				Drawable bg = convertView.getBackground();
			      if (bg == null || bg.getConstantState() != mBackgroundDrawable.getConstantState())
			      {
			        bg = mBackgroundDrawable.getConstantState().newDrawable();
			        // View.setBackground() is only available from API level 16 onward, we need to support lower API levels.
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			        	convertView.setBackgroundDrawable(bg);
			        } else {
			        	convertView.setBackground(bg);
			        }
			      }

			      if (!convertView.isSelected()) {
			        bg.setLevel(position%2);
			      } else {
			        bg.setLevel(2); // set as head background color when the item is selected
			      }
			}
			
			
			ImageView iv_wifi=(ImageView) convertView.findViewById(R.id.iv_wifi);
			iv_wifi.setImageLevel(position%5);
			
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public AppInfo getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}
	};

}
