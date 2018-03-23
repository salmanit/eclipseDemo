package com.charlie.demo0108;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.charlie.demo0108.util.UtilsNormal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

public class ActivityFunctionTest extends ActivityBase{

		private TextView tv_show;
	private static final String TAG = ActivityFunctionTest.class.getSimpleName();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_function_test);
		tv_show=(TextView) findViewById(R.id.tv_show);
		getAppInfo();
		tv_show.setText("sdk="+android.os.Build.VERSION.SDK_INT +" isWiFiConnected="+isWiFiConnected(this)+ "  isInternetConnected="+isInternetConnected(this)
		+"  UtilsNormal internet Connected="+UtilsNormal.isInternetConnected(this));
		
	}
	
	
	public static boolean isWiFiConnected(final Context context)
	  {
	    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if(manager == null)
	    {
	      Log.i(TAG, "ConnectivityManager is null");
	      return false;
	    }
	    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
	    return activeNetwork != null 
	        && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI 
	        && activeNetwork.isConnectedOrConnecting();
	  }
	  
	  public static boolean isInternetConnected(final Context context)
	  {
	    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if(manager == null)
	   {
	      Log.e(TAG, "ConnectivityManager is null");
	      return false;
	    }
	    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
	    if(activeNetwork!=null) {
	    	System.err.println("active NetworkInfo=="+activeNetwork.toString());
	    }
	    return activeNetwork != null 
	        && activeNetwork.isConnectedOrConnecting();
	  }

	public void clickSound(View view) {
		AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_SAME,
	        AudioManager.FLAG_PLAY_SOUND); 
	}
	private static final String LOG_TAG = "AutoPowerOffManager";
	private WakeLock mWakeLock;
	public void wakeLock(View view) {
	    final PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
	    mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, LOG_TAG);
	    CheckBox cBox=(CheckBox) findViewById(R.id.cb_lock_reference_count);
	    //this line is not necessary, but let's make things a little simpler to reason about
	    mWakeLock.setReferenceCounted(cBox.isChecked());
	    mWakeLock.acquire(60000);
	}
	
	public void wakeUnlock(View view) {
		if(mWakeLock!=null) {
			System.err.println("isHeld============"+mWakeLock.isHeld());
			mWakeLock.release();
		}
	}
	
	private  String changeToChar(String s,String yh) {
		byte[] old=s.getBytes();
		byte[]  add=yh.getBytes();
		
		for(int i=0;i<old.length;i++) {
			for(int j=0;j<add.length;j++) {
				old[i]=(byte) (old[i]^add[j]);
			}
		}
		String result=new String(old);
		System.err.println("============="+result+"========");
		return result;
	}
	public  void changeSystemSetting(View view) {
		timeout=Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,10000);
		brightness=Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,10);
		System.err.println("get default  timeout==="+timeout+" brightness=="+brightness);
		android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, 50);
//		android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,  Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
		Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 36000000);
		//2147483647
	}
	int timeout=-1;
	int brightness=40;
	
	public void resetToPre(View view) {
		System.err.println("reset default timeout==="+timeout+" brightness=="+brightness);
		 if(timeout>=0) {
			 Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeout);
			 Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
		 }
	}
	public void changeCurrentBrightnessByWindow(View view) {
		WindowManager.LayoutParams params=getWindow().getAttributes();
		params.screenBrightness=0.8f;
		getWindow().setAttributes(params);
	}
	public void resetCurrentBrightnessByWindow(View view) {
		WindowManager.LayoutParams params=getWindow().getAttributes();
		params.screenBrightness=WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
		getWindow().setAttributes(params);
	}
	public void getAppInfo() {
		// 获取当前程序路径
				getApplicationContext().getFilesDir().getAbsolutePath();
				// 获取该程序的安装包路径
				String path = getApplicationContext().getPackageResourcePath();
				// 获取程序默认数据库路径
				getApplicationContext().getDatabasePath("test.db").getAbsolutePath();
				final TextView tv_show = (TextView) findViewById(R.id.tv_path_show);
				tv_show.setText(
						"getExternalFilesDir="+getExternalFilesDir("").getAbsolutePath() 
						+ "\n getFilesDir==" + getFilesDir().getAbsolutePath() 
						+ "\n getPackageResourcePath==" + getPackageResourcePath()
						+"\n getPackageCodePath="+getPackageCodePath()
						+"\n getDatabasePath="+getDatabasePath("test.db"));
	}
	
	
	File file=new File(Environment.getExternalStorageDirectory(),"MyTemp");
	public void fileObsever(View view) {
		file.mkdirs();
		final TextView tv_show=(TextView) findViewById(R.id.tv_file_change_info);
		FileObserver fileObserver=new FileObserver(file.getAbsolutePath()) {
			
			@Override
			public void onEvent(int event, String path) {
				System.err.println("onEvent===="+event+" path="+path);
				tv_show.setText(tv_show.getText().toString()+"\n"+ "event="+event+" path="+path);
				
			}
		};
		fileObserver.startWatching();
		
		try {
			File file2=File.createTempFile("pre", ".txt", file);
			file2.renameTo(new File(Environment.getExternalStorageDirectory(),"changeName.txt"));
//			boolean  copyResult=new File(getPackageResourcePath()).renameTo(new File(file,"demo0108.apk"));
//			上边的rename会失败，因为那系统存储的apk他还要用，我们没有权限改名字或者移动的。
//			copyFile(new File(getPackageResourcePath()), new File(file,"demo0108.apk"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void copyFile(File from,File to) {
		try {
			FileInputStream fileInputStream=new FileInputStream(from);
			FileOutputStream fileOutputStream=new FileOutputStream(to);
			byte[] buffer=new byte[1024*8];
			int count=0;
			while((count=fileInputStream.read(buffer))>0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void clearFile() {
		file.deleteOnExit();
	}
	
	
	private CountDownLatch countDownLatch;
	public void startCountDown(View view) {
		if(countDownLatch==null) {
			countDownLatch=new CountDownLatch(3);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
//						System.err.println("==============countdown start");
//						countDownLatch.await();
						System.err.println("==============countdown start1,after 15seconds end");
						countDownLatch.await(15,TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.err.println("==============countdown end");
					countDownLatch=null;
				}
			}).start();
		}
	}
	
	public void stopCountDown(View view) {
		if(countDownLatch!=null) {
			countDownLatch.countDown();
		}
	}
	
	
	
	public void testWifi(View view) {
		TextView tv_wifi_info=(TextView) findViewById(R.id.tv_wifi_info);
		StringBuffer sBuffer=new StringBuffer();
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// WifiConfiguration config = new WifiConfiguration();
			
		List<WifiConfiguration> configs = wm.getConfiguredNetworks();
		sBuffer.append("getConfiguredNetworks********\n");
		if (null != configs) {
			for (WifiConfiguration config : configs) {
				if (config != null) {
					System.err.println("wifi==========" + config);
					sBuffer.append("networkId="+config.networkId+"ssid="+config.SSID+" bssid=="+config.BSSID+
							 " •priority="+config.priority+"\n");
				}
			}
		}
		if(!wm.isWifiEnabled()) {
			wm.setWifiEnabled(true);
		}
		WifiInfo wifiInfo=wm.getConnectionInfo();
		if(wifiInfo!=null) {
			System.err.println("connectionInfo======"+wifiInfo.toString());
			sBuffer.append("connectionInfo======="+wifiInfo.toString()+"\n");
		}
		boolean result=wm.startScan();
		sBuffer.append("scan result==="+result+"\n");
		List<ScanResult>  lists=wm.getScanResults();
		if(lists!=null) {
			for(ScanResult scanResult:lists) {
				System.err.println("scan result===="+scanResult.toString());
			sBuffer.append(scanResult.toString()+"\n");
			}
		}
	
		tv_wifi_info.setText(sBuffer.toString());
		//
	}
	
	
	
	NotificationManager manager;
	private NotificationCompat.Builder builder;
	private int progress = 22;
	public void showNotification(View view) {
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (builder == null) {
			builder = new NotificationCompat.Builder(this)
					.setTicker("ticker")
					.setContentTitle("content title")
					.setContentInfo("content info")
//					.setContentText("content text")
//					.setSubText("sub text")
					.setAutoCancel(true)
					.setContentIntent(PendingIntent.getActivity(this, 999, new Intent(this, Activity4Balls.class), 0))
					.setSmallIcon(R.drawable.ic_launcher).setProgress(100, progress, false);
		}
		progress += 10;
		builder.setProgress(100, progress, false);
		manager.notify(2, builder.build());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(manager!=null)
			manager.cancelAll();
		clearFile();
		// System.exit(0);
	}
}
