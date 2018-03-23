package com.charlie.demo0108;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import bean.Student;
import test.EnginServiceParams;
import test.MainEnginServiceParams;

@SuppressLint("NewApi")
public class MyApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		EnginServiceParams.registerInstance(new MainEnginServiceParams());
		registerActivityLifecycleCallbacks(lifecycleCallbacks);
		
		registerComponentCallbacks(componentCallbacks);
		if(Build.VERSION.SDK_INT>=18) {
			registerOnProvideAssistDataListener(provideAssistDataListener);
		}
		
	}
	
	public static StringBuffer sBuffer=new StringBuffer();
	
	private void printLOG(String msg) {
		System.err.println("============="+msg);
		sBuffer.append(msg);
	}
	
	@Override
	protected void finalize() throws Throwable {
		printLOG("finalize=============");
		super.finalize();
	}
	
	public void back() {
		unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
		unregisterComponentCallbacks(componentCallbacks);
		if(Build.VERSION.SDK_INT>=18) {
		unregisterOnProvideAssistDataListener(provideAssistDataListener);
		}
	}
	
	private ComponentCallbacks componentCallbacks=new ComponentCallbacks() {
		
		@Override
		public void onLowMemory() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			printLOG("onConfigurationChanged============"+newConfig.orientation);
			
		}
	};
	private OnProvideAssistDataListener  provideAssistDataListener=new OnProvideAssistDataListener() {
		
		@Override
		public void onProvideAssistData(Activity activity, Bundle data) {
			printLOG("========================="+activity.getComponentName());
			
		}
	};
	private ActivityLifecycleCallbacks lifecycleCallbacks=new ActivityLifecycleCallbacks() {
		
		@Override
		public void onActivityStopped(Activity activity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onActivityStarted(Activity activity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onActivityResumed(Activity activity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onActivityPaused(Activity activity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onActivityDestroyed(Activity activity) {
			// TODO Auto-generated method stub
			printLOG("onActivityDestroyed==="+activity.getLocalClassName()+"\n");
		}
		
		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			printLOG("onActivityCreated==="+activity.getLocalClassName()+"\n");
			
		}
	};
	
	ThreadLocal<Student> local=new ThreadLocal<Student>();
	
	public void storeStudent(Student student) {
		local.set(student);
	}
	public Student retrieveStudent() {
		Student student=local.get();
//		local.remove();
		return student;
	}
}
