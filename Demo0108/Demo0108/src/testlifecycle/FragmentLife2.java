package testlifecycle;

import com.charlie.demo0108.R;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentLife2 extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		println(getLocalClassName()+"```````onCreate");
//		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.err.println(getClass().getName()+"========================onCreateView");
		return inflater.inflate(R.layout.fragmentlife2, container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		println(getLocalClassName()+"````````onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
	@Override
	public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
		System.err.println(getClass().getName()+"========================onInflate=="+attrs);
		super.onInflate(activity, attrs, savedInstanceState);
		if(attrs!=null) {
			TypedArray a=activity.obtainStyledAttributes(attrs, R.styleable.FragmentArguments);
			title= a.getText(R.styleable.FragmentArguments_android_label);
			color=a.getColor(R.styleable.FragmentArguments_android_color, color);
			int orientation=a.getInt(R.styleable.FragmentArguments_android_orientation, -1);
			System.err.println("TypedArray=================="+color+"========="+title+"===="+orientation);
			a.recycle();
		}
	}
	CharSequence title="default";
	int color=Color.BLUE;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.err.println(getClass().getName()+"==onActivityCreated=================="+color+"========="+title);
		TextView tv=(TextView) getView().findViewById(R.id.tv_fragment2);
		tv.setText(title);
		tv.setTextColor(color);
	}
	
	@Override
	public void onResume() {
		println(getLocalClassName()+"`````onResume");
		super.onResume();
	}
	
	@Override
	public void onStart() {
		println(getLocalClassName()+"`````````onStart");
		super.onStart();
	}
	
	private String getLocalClassName() {
		return getClass().getSimpleName();
	}


	@Override
	public void onPause() {
		println(getLocalClassName()+"`````onPause");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		println(getLocalClassName()+"````````onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		println(getLocalClassName()+"`````````onDestroy");
		super.onDestroy();
	}

	
	private void println(String msg) {
		System.err.println("======================="+msg);
	}
}
