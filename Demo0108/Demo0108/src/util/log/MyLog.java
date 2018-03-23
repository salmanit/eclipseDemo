package util.log;

import android.os.Handler;
import android.widget.TextView;

public class MyLog {
	private static TextView textView;
	private static StringBuffer sBuffer=new StringBuffer();
	
	public static void setTarget(TextView tView) {
		textView=tView;
		sBuffer=new StringBuffer();
	}
	public static void i(String msg) {
		if(sBuffer!=null)
			sBuffer.append(msg+"\n");
		if(textView!=null) {
			new Handler(textView.getContext().getMainLooper()).post(new Runnable() {
				
				@Override
				public void run() {
					textView.setText(sBuffer.toString());
				}
			});
			
		}
	}
	
	public static void reset() {
		textView=null;
		sBuffer=null;
	}
}
