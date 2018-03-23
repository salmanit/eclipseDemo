package web;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.webkit.WebView;
import android.widget.Toast;

public class CustomWebView extends WebView{

	private ActionMode mActionMode;
	private ArrayList<String> mActionList=new ArrayList<String>();

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActionList.add("item11");
		mActionList.add("item12");
		mActionList.add("item13");
	}

	@Override
	public ActionMode startActionMode(Callback callback) {
		ActionMode actionMode=super.startActionMode(callback);
		System.err.println("=============="+actionMode+"++++++===");
		return resolveActionMode(actionMode);
	}
	
//	@Override
//	public ActionMode startActionMode(Callback callback, int type) {
//		ActionMode actionMode=super.startActionMode(callback, type);
//		System.err.println("=============="+actionMode+"++++++==="+type);
//		return super.startActionMode(callback, type);
//	}
	
	
	private ActionMode resolveActionMode(ActionMode actionMode) {
	    if (actionMode != null) {
	        final Menu menu = actionMode.getMenu();
	        mActionMode = actionMode;
	        menu.clear();
	        for (int i = 0; i < mActionList.size(); i++) {
	            menu.add(mActionList.get(i));
	        }
	        for (int i = 0; i < menu.size(); i++) {
	            MenuItem menuItem = menu.getItem(i);
	            menuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
//						 getSelectedData((String) item.getTitle());
//		                    releaseAction();
		                    showToast(item.getTitle().toString());
		                    return true;
					}
				});
	        }
	    }
	    mActionMode = actionMode;
	    return actionMode;
	}

private void showToast(String msg) {
	Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
}
	
}
