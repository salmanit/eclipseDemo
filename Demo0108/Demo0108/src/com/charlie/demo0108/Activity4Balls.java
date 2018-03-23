package com.charlie.demo0108;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.net.ssl.HttpsURLConnection;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import widget.LaunchAnimaView;

public class Activity4Balls  extends ActivityBase{
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.test_activity);
//		TestMain4 test = new TestMain4();
//	      test.setDaemon(true);    //调试时可以设置为false，那么这个程序是个死循环，没有退出条件。设置为true，即可主线程结束，test线程也结束。
//	       test.start();
//	       System.out.println("isDaemon = " + test.isDaemon());
	       	fileObserver.startWatching();
	       	findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					v.setBackgroundResource(17303430);
				LaunchAnimaView launchAnimaView=(LaunchAnimaView) findViewById(R.id.lav);
					launchAnimaView.startTransX();
				}
			});
//	       	downFile();
//	       	test();
//	       	systemSet();
	}

	
	private void  downFile() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					URL url=new URL("http://192.168.0.132/OTA/1/0943/3_98/version.ini");
					String destination=Environment.getExternalStorageDirectory().getAbsolutePath()+"version.ini";
					downloadFile(url, destination);
					String destination1=Environment.getExternalStorageDirectory().getAbsolutePath()+"version1.ini";
					System.err.println("==========="+downloadSmallFile(url, 104, destination1));
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	public static void downloadFile(URL url, String destination) {
	    final File destinationFile = new File(destination);
	    if (destinationFile.exists())
	    {
	      destinationFile.delete();
	    }
	    BufferedOutputStream bos = null;
	    InputStream is = null;
	    try {
	        byte[] buff = new byte[8192];
	        is = url.openStream();
	        File file = new File(destination);
	        file.getParentFile().mkdirs();
	        bos = new BufferedOutputStream(new FileOutputStream(file));
	        int count = 0;
	        while ( (count = is.read(buff)) != -1) {
	            bos.write(buff, 0, count);
	        }
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    finally {
	        if (is != null) {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        if (bos != null) {
	            try {
	                bos.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	            
	    }
	}
	  public static long downloadSmallFile(final URL url, final long fileLength,
	      final String destination) throws IOException
	  {
	    long savedLength = 0;
	    final File destinationFile = new File(destination);
	    if (destinationFile.exists())
	    {
	      destinationFile.delete();
	    }

	    HttpURLConnection connection = null;
	    BufferedInputStream in = null;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;

	    try
	    {
	      if (url.getProtocol().equals("http"))
	      {
	        connection = (HttpURLConnection) url.openConnection();
	      }
	      else
	      {
	        connection = (HttpsURLConnection) url.openConnection();
	      }
	      connection.setRequestProperty("Range", "bytes=" + savedLength + "-");
	      connection.setReadTimeout(66666);
	      connection.setConnectTimeout(66666);
	      connection.setDoInput(true);
	      if (!url.getProtocol().equals("http"))
	      {
	        connection.setDoOutput(true);
	      }

	      Log.d("", "Downloading: " + destinationFile.getName() + " ["
	          + fileLength + "]");
	      fos = new FileOutputStream(destination);

	      final int BYTE_BUFFER_SIZE = 1024;
	      bos = new BufferedOutputStream(fos, BYTE_BUFFER_SIZE);
	      in = new BufferedInputStream(connection.getInputStream(),
	          BYTE_BUFFER_SIZE);
	      {
	        final byte[] data = new byte[BYTE_BUFFER_SIZE];
	        int x = 0;
	        while (x != -1)
	        {
	          x = in.read(data, 0, BYTE_BUFFER_SIZE);
	          if (x > 0)
	          {
	            bos.write(data, 0, x);
	            savedLength += x;
	          }
	        }
	      }
	    } finally
	    {
	      if (connection != null)
	      {
	        connection.disconnect();
	      }
	      if (in != null)
	      {
	        try
	        {
	          in.close();
	        } catch (final IOException ioe)
	        {
	        }
	      }
	      if (bos != null)
	      {
	        try
	        {
	          bos.close();
	        } catch (final IOException ioe)
	        {
	        }
	      }
	      if (fos != null)
	      {
	        try
	        {
	          fos.close();
	        } catch (final IOException ioe)
	        {
	        }
	      }
	    }
	    return savedLength;
	  }
	
	private void test() {
		ArrayList<String> lists=new ArrayList<String>();
		lists.add("aa");
		lists.add("bbb");
		lists.add("d");
		Collections.sort(lists,new LengthCompator());
		Collections.reverse(lists);
		for(int i=0;i<lists.size();i++) {
			System.err.println("=========="+lists.get(i));
		}
	}
	public class LengthCompator implements Comparator<String>{

		@Override
		public int compare(String lhs, String rhs) {
			
			return lhs.length()-rhs.length();
		}

		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fileObserver.stopWatching();
	}
	FileObserver fileObserver=new FileObserver(new File(Environment.getExternalStorageDirectory(),"Download").getAbsolutePath()) {
		
		@Override
		public void onEvent(int event, String path) {
			System.err.println(path+"==onEvent=========="+event);
			
		}
	};
//	01-11 18:01:16.296: W/System.err(14574): 0third-229550397txt==onEvent==========256
//			01-11 18:01:16.296: W/System.err(14574): 0third-229550397txt==onEvent==========32
//			01-11 18:01:16.296: W/System.err(14574): 0third-229550397txt==onEvent==========8
//	01-11 18:01:36.306: W/System.err(14574): 20third198556449txt==onEvent==========256
//			01-11 18:01:36.306: W/System.err(14574): 20third198556449txt==onEvent==========32
//			01-11 18:01:36.306: W/System.err(14574): 20third198556449txt==onEvent==========8
//	01-11 18:12:43.966: W/System.err(14957): null==onEvent==========32768



	class TestMain4 extends Thread {
		   public void run() {            //永真循环线程
		       for(int i=0;;i++){
		           try {
		               Thread.sleep(1000);
		           } catch (InterruptedException ex) {   }
		           System.out.println(i+"+++++++++");
		           if(i%30==0) {
		        	   try {
						File.createTempFile(i+"third", ".txt",new File(Environment.getExternalStorageDirectory(),"Download"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		           }
		       }
		   }}
	

	
	@SuppressLint("NewApi")
	BroadcastReceiver betteryReceiver=new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
			int health=intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
			boolean present=intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
			int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
			int icon_small=intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
			int plugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
			
			int voltage=intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
			int temperature=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
			String technology=intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
			
			BatteryManager batteryManager=(BatteryManager) getSystemService(BATTERY_SERVICE);
			if(batteryManager!=null) {
				long capacity=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
				long charge_counter=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
				long current_average=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
				long current_now=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
				long energy_counter=batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
				
				System.err.println(" capacity=="+capacity +" charge_counter=="+charge_counter
						+" current_average=="+current_average+" current_now=="+current_now+" energy_counter=="+energy_counter);
				// capacity==100 charge_counter==-9223372036854775808 current_average==0 current_now==0 energy_counter==-9223372036854775808
				Button button=(Button) findViewById(R.id.btn_start);
				button.setText(
						intent.getAction()+"\n"+
						" capacity=="+capacity +" charge_counter=="+charge_counter
						+" current_average=="+current_average+" current_now=="+current_now+" energy_counter=="+energy_counter
						+"\n"+"status=="+status+" health=="+health
						+" present=="+present+" level=="+level
						+" scale=="+scale+" icon_small=="+icon_small
						+" plugged=="+plugged+" voltage=="+voltage
						+" temperature=="+temperature+" technology=="+technology);
			}
			System.err.println("status=="+status+" health=="+health
					+" present=="+present+" level=="+level
					+" scale=="+scale+" icon_small=="+icon_small
					+" plugged=="+plugged+" voltage=="+voltage
					+" temperature=="+temperature+" technology=="+technology);
			//status==3 health==2 present==true level==100 scale==100 icon_small==17303430 plugged==2 voltage==4311 temperature==271 technology==Li-ion
		}};
		
		@Override
		protected void onStart() {
			IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//			filter.addAction(BatteryManager.ACTION_CHARGING);
//			filter.addAction(BatteryManager.ACTION_DISCHARGING);
			registerReceiver(betteryReceiver, filter);
			super.onStart();
		}
		
		@Override
		protected void onStop() {
			unregisterReceiver(betteryReceiver);
			super.onStop();
		}
}
