package preference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.charlie.demo0108.R;

import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public  class ActivityPreferenceBase extends PreferenceActivity {

	@Override
	public void onContentChanged() {
		

	    // Replace list view with TwoColourListView
	    View oldListView = findViewById(android.R.id.list);
	    oldListView.setBackgroundColor(Color.parseColor("#55ff0000"));
	    ViewGroup parent = (ViewGroup) oldListView.getParent();
	    System.err.println(getLocalClassName()+"======================onContentChanged getChildCount="+parent.getChildCount());
	    System.err.println("is LinearLayout? "+(parent instanceof LinearLayout));
	    ViewGroup grandParent=(ViewGroup) parent.getParent();
	    System.err.println("is LinearLayout? "+(grandParent instanceof LinearLayout)+" count="+grandParent.getChildCount());
	    if(grandParent instanceof LinearLayout) {
	    	LinearLayout linearLayout=(LinearLayout) grandParent;
	    	LinearLayout layout=(LinearLayout) linearLayout.getChildAt(1);
	    	System.err.println("right layout count==="+layout.getChildCount());
	    	layout.setBackgroundColor(Color.GRAY);
	    	layout.getChildAt(0).setBackgroundColor(Color.parseColor("#5500ff00"));
	    	layout.getChildAt(1).setBackgroundColor(Color.parseColor("#550000ff"));
	    }
	    super.onContentChanged();
	    
	    View crumbs = findViewById(android.R.id.title);
        // For screens with a different kind of title, don't create breadcrumbs.
        try {
            FragmentBreadCrumbs mFragmentBreadCrumbs = (FragmentBreadCrumbs)crumbs;
            setNotAccessibleProperty(mFragmentBreadCrumbs, "mTextColor", Color.WHITE);
        } catch (ClassCastException e) {
            return;
        }
	}
    public static void setNotAccessibleProperty(Object obj, String propertyName, Object value)  
    {  
        try {
			Class<?> clazz = obj.getClass();  
			Field field = clazz.getDeclaredField(propertyName);  
			//赋值前将该成员变量的访问权限打开  
			field.setAccessible(true);  
			field.set(obj, value);  
			//赋值后将该成员变量的访问权限关闭  
			field.setAccessible(false);
		}  catch (Exception e) {
			e.printStackTrace();
		}  
    }  
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.err.println(getLocalClassName()+"======================onCreate");
		super.onCreate(savedInstanceState);
	}

	
	@Override
	protected boolean isValidFragment(String fragmentName) {
		return true;
	}
	
	
	private ArrayList<Header> lists = new ArrayList<Header>();

	public class HeaderAdapter extends ArrayAdapter<Header> {

		public HeaderAdapter(Context context, List<Header> objects) {
			super(context, 0, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null) {
				convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.preference_header, parent,false);
			}
			TextView tv_title = (TextView) convertView.findViewById(android.R.id.title);
			TextView tv_summary = (TextView) convertView.findViewById(android.R.id.summary);
			ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			Header header = getItem(position);
			tv_title.setText(header.title);
			tv_summary.setVisibility(View.GONE);
			iv_icon.setImageResource(header.iconRes);
			return convertView;
		}
	}

}
