package testlifecycle;

import java.util.ArrayList;

import com.charlie.demo0108.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import bean.FlowStepData;
import bean.ShowTextParent;
import widget.FlowStepShow;
import widget.FlowStepShow.StateChangeListener;

public class FragmentLife1 extends Fragment{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		println(getLocalClassName()+"```````onCreate");
//		setRetainInstance(true);
		super.onCreate(savedInstanceState);
		test1();	
		
		
	}
	
	private void test1() {
		
		System.err.println("getConfiguration==========="+getResources().getConfiguration().toString());
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		println(getLocalClassName()+"````````onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		println(getLocalClassName()+"`````````onCreateView");
		View view=inflater.inflate(R.layout.fragment_life1, container,false);
		if(view.getParent()!=null) {
			
		}
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		println(getLocalClassName()+"``````onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		
		FlowStepShow flowStepShow=(FlowStepShow) getView().findViewById(R.id.flowStepShow1);
		FlowStepShow flowStepShow2=(FlowStepShow) getView().findViewById(R.id.flowStepShow2);
		{
		ArrayList<ShowTextParent> lists=new ArrayList<ShowTextParent>();
		lists.add(new FlowStepData("下订单"));
		lists.add(new FlowStepData("验证客户订单信息"));
		lists.add(new FlowStepData("支付定金"));
		lists.add(new FlowStepData("订单完成"));
		lists.add(new FlowStepData("已经入住"));
		lists.add(new FlowStepData("已经退房"));
		flowStepShow.setData(lists,3);
		flowStepShow.setListener(new StateChangeListener() {
			
			@Override
			public void positionChanged(int position) {
				System.err.println("current==========="+position);
				
			}
		});
		}
		
		{
		ArrayList<ShowTextParent> lists=new ArrayList<ShowTextParent>();
		lists.add(new FlowStepData("5分钟"));
		lists.add(new FlowStepData("10分钟"));
		lists.add(new FlowStepData("20分钟"));
		lists.add(new FlowStepData("1小时"));
		flowStepShow2.setData(lists,1);
		}
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
