package testlifecycle;

import com.charlie.demo0108.ActivityBase;
import com.charlie.demo0108.R;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityLife1 extends ActivityBase{
	@Override
	protected void onCreate(Bundle arg0) {
		println(getLocalClassName()+"`````_onCreate1");
		super.onCreate(arg0);
		println(getLocalClassName()+"`````_onCreate2  bundle="+arg0);
		setContentView(R.layout.activity_life1);
		if(arg0!=null) {
		}else {
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.layout_container, new FragmentLife1(),"fragmentlife1")
			.setBreadCrumbShortTitle("bread crumb short title fragment1")
			.commitAllowingStateLoss();
		}
		
		findViewById(R.id.tv1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_container, new FragmentLife1(),"fragmentlife1")
				.setBreadCrumbShortTitle("bread crumb short title fragment2")
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
	            .addToBackStack("fragment2")
				.commitAllowingStateLoss();
			}
		});
		findViewById(R.id.tv2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 int numEntries = getSupportFragmentManager().getBackStackEntryCount();
			      
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		println(getLocalClassName()+"```````onResume");
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		println(getLocalClassName()+"``````onStart");
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		println(getLocalClassName()+"```````onPause");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		println(getLocalClassName()+"````````onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		println(getLocalClassName()+"`````````onDestroy");
		super.onDestroy();
	}

	
	private void println(String msg) {
		System.err.println("======================="+msg);
	}
}
