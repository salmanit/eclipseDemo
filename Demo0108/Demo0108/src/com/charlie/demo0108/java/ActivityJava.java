package com.charlie.demo0108.java;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.charlie.demo0108.ActivityBase;
import com.charlie.demo0108.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.provider.Telephony.Threads;
//import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import bean.Student;
import util.log.MyLog;

public class ActivityJava extends ActivityBase {

	StringBuffer sb = new StringBuffer();
	TextView tv_countdown_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_java);
		tv_countdown_show = (TextView) findViewById(R.id.tv_countdown_latch_result);
		findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				countDownTimer = startCountDownTimer(tv_countdown_show);
			}
		});
		
		findViewById(R.id.btn_looper).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyLog.setTarget((TextView) findViewById(R.id.tv_looper_log));
				waitNotify();
				logThreadName();
			}
		});
		System.err.println("student="+getMyApplication().retrieveStudent().toString());
		getMyApplication().storeStudent(new Student("sal111", "sh111", 22, "qh111"));
		Vector<String> vector=new Vector<String>();
		Stack<String> stack=new Stack<String>();
		Hashtable<String, Integer> hash=new Hashtable<String, Integer>();
		Collections.synchronizedCollection(vector);
		String[] arr= {};
		arr[0]="1";
		arr[1]="2";
	}
	Lock lock=new ReentrantLock();
	private void logThreadName() {
		lock.lock();
		int count = Thread.activeCount();
		Thread[] threads=new Thread[count];
		Thread.enumerate(threads);
		for(Thread thread:threads) {
			MyLog.i("==="+thread.toString());
		}
		lock.unlock();
	}
	CountDownTimer countDownTimer;

	@Override
	public void onBackPressed() {
		MyLog.reset();
		cancelCountDown();
		super.onBackPressed();
	}

	private void cancelCountDown() {
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}

	private CountDownTimer startCountDownTimer(final TextView tView) {
		final String defaultTxt = tView.getText().toString();
		cancelCountDown();
		countDownTimer = new CountDownTimer(5000 + 10, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				System.err.println("onTick====" + millisUntilFinished);
				tView.setText(defaultTxt + "(" + (millisUntilFinished) / 1000 + ")");
			}

			@Override
			public void onFinish() {
				showToast("end");
				System.err.println("onFinish====");
				tView.setText(defaultTxt);
			}
		};
		countDownTimer.start();
		return countDownTimer;
	}

	Handler handler = new Handler();

	private void tempTest() {
		 Message.obtain(handler, new Runnable() {

			@Override
			public void run() {
				MyLog.i("Message=======1========" + Looper.myLooper()+"==="+Thread.currentThread().toString());
			}
		}).sendToTarget();
	}

	private void waitNotify() {
		tempTest();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						MyLog.i("myLooper=======0========" + Looper.myLooper());
						 Looper looper=Looper.myLooper();
						if(looper!=null) {
							looper.quit();
						}
					}
				},3333);

				Looper.loop();
				MyLog.i("myLooper======1========" + Looper.myLooper());
				handlerThreadTest();
			}
		}).start();
		
	}
	
	private void handlerThreadTest() {
		HandlerThread thread=new HandlerThread("myThread");
		thread.start();
		new Handler(thread.getLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				MyLog.i("handlerThreadTest=======1========" + Looper.myLooper());
			}
		});
	}
}
