package com.charlie.demo0108;

import java.io.FileInputStream;
import java.util.Arrays;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class ActivityBase extends FragmentActivity{

	
	
	private Toast toast;
	
	public void showToast(String string) {
		if(toast!=null) {
			toast.cancel();
			toast=null;
		}
		toast=Toast.makeText(this, ""+string, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	@Override
	protected void onResume() {
//		System.err.println(getLocalClassName()+"====onResume==="+Arrays.toString(fileList())+"\n====="+getFileInfo());
		super.onResume();
	}
	@Override
	protected void onPause() {
//		System.err.println(getLocalClassName()+"===onPause===="+Arrays.toString(fileList())+"\n====="+getFileInfo());
		super.onPause();
	}
	private String getFileInfo() {
		try {
			FileInputStream fis=openFileInput("rList-"+getComponentName().getClassName());
			int count=0;
			byte[] buffer=new byte[1024*8];
			StringBuffer sBuffer=new StringBuffer();
			while((count=fis.read(buffer))>0) {
				sBuffer.append(new String(buffer,0,count));
			}
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public MyApplication getMyApplication() {
		return (MyApplication) getApplication();
	}
}
