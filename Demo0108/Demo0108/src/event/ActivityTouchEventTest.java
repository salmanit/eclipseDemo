package event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.charlie.demo0108.ActivityBase;
import com.charlie.demo0108.R;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.Window;
import widget.ControlBar;
import widget.ControlBar.Alignment;

public class ActivityTouchEventTest extends ActivityBase {

	private String tag = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_event_test);

		ControlBar controlBar = (ControlBar) findViewById(R.id.controlBar1);
		controlBar.addActionScale(R.drawable.adp_navigation_etablack_icon, Alignment.RIGHT, new OnClickListener() {

			@Override
			public void onClick(View v) {
//				showToast("adp_navigation_etablack_icon");
				TouchEventView touchEventView=(TouchEventView) findViewById(R.id.touchEventView1);
				touchEventView.startAnimatiion();
			}
		});
		controlBar.addActionScale(R.drawable.adp_navigation_speedblack_icon, Alignment.RIGHT, new OnClickListener() {

			@Override
			public void onClick(View v) {

				showToast("adp_navigation_speedblack_icon");
			}
		});
		
		testSaveWordsToFile();
		
		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				button.setText(getFileInfo());
			}
		});
//		try {
//			final String[] loggingCommand = { "/system/bin/logcat", "-v", "threadtime" };
//			logProcess = Runtime.getRuntime().exec(loggingCommand);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		final BufferedReader reader = new BufferedReader(new InputStreamReader(logProcess.getInputStream()), 8 * 1024);
//		
//		handler.post(new Runnable() {
//
//			@Override
//			public void run() {
//				{
//					
//					try {
//						final String buf = reader.readLine();
//						if (buf != null) {
//							sBuffer.append(buf + "\n");
//							button.setText(sBuffer.toString());
//						}
//						handler.postDelayed(this, 1000);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
	}

	private String getFileInfo() {
		try {
			FileInputStream fis=openFileInput("rList-event.ActivityTouchEventTest");
			int count=0;
			byte[] buffer=new byte[1024*8];
			StringBuffer sBuffer=new StringBuffer();
			while((count=fis.read(buffer))>0) {
				sBuffer.append(new String(buffer,0,count));
			}
			return sBuffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	Process logProcess = null;
	StringBuffer sBuffer = new StringBuffer();
	Handler handler = new Handler();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacksAndMessages(null);
		if (logProcess != null) {
			logProcess.destroy();
		}
		super.onDestroy();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		System.err.println(tag + "=========dispatchTouchEvent======" + event.getAction());
		
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.err.println(tag + "=========onTouchEvent======" + event.getAction());
		return super.onTouchEvent(event);
	}

	private void testSaveWordsToFile() {
		try {
			System.err.println("fileList============="+Arrays.toString(fileList()));
			//[rList-com.charlie.demo0108.MainActivity, rList-event.ActivityTouchEventTest, test.txt]
			//2130837591/[16974120, 2131427468, 0, 0]
			//2130837592/[16974120, 2131427468, 0, 0]
			//2130837593/[16974120, 2131427468, 0, 0]

			FileOutputStream fileOutputStream=openFileOutput("test.txt", MODE_PRIVATE);
			fileOutputStream.write("i am here.i am here.".getBytes());
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file=getFileStreamPath("rList-com.charlie.demo0108.MainActivity");
		File file2=getFileStreamPath("rList-event.ActivityTouchEventTest");
		File file11=new File(Environment.getExternalStorageDirectory(),"MainActivity");
		File file22=new File(Environment.getExternalStorageDirectory(),"ActivityTouchEventTest");
		copyFile(file, file11);
		copyFile(file2, file22);
	}
	
	private void copyFile(File from,File to) {
		try {
			FileOutputStream fos1=new FileOutputStream(to);
			FileInputStream fis1=new FileInputStream(from);
			int count=0;
			byte[] buffer=new byte[1024*8];
			while((count=fis1.read(buffer))>0) {
				fos1.write(buffer, 0, count);
			}
			fos1.close();
			fis1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
