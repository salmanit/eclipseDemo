package com.charlie.demo0108.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class UtilsNormal {
	private static final String TAG = UtilsNormal.class.getSimpleName();

	public static boolean isInternetConnected(final Context context) {
		isInternetConnectedOld(context);
		if (android.os.Build.VERSION.SDK_INT < 21) {
			return isInternetConnectedOld(context);
		} else {
			return isInternetConnectedNew(context);
		}
	}

	private static boolean isInternetConnectedOld(final Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			Log.e(TAG, "ConnectivityManager is null");
			return false;
		} else {
			try {
				final NetworkInfo[] all_nets = manager.getAllNetworkInfo();
				if (all_nets == null) {
					Log.e(TAG, "ConnectivityManager.getAllNetworkInfo return null");
					return false;
				}
				for (NetworkInfo info : all_nets) {
					if(info!=null) {
						System.err.println("old NetworkInfo======="+info.toString());
					}
				}
			} catch (Exception e) {// error would happen if the connection type is not available
				e.printStackTrace();
			}
			// other types?
			return false;
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private static boolean isInternetConnectedNew(final Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			Log.e(TAG, "ConnectivityManager is null");
			return false;
		} else {
			try {
				final Network[] all_nets = manager.getAllNetworks();
				if (all_nets == null) {
					Log.e(TAG, "ConnectivityManager.getAllNetworks return null");
					return false;
				}
				for (Network net : all_nets) {
					final NetworkInfo info = manager.getNetworkInfo(net);
					if(info!=null) {
						System.err.println("NetworkInfo======="+info.toString());
					}
				}
				for (Network net : all_nets) {
					final NetworkInfo info = manager.getNetworkInfo(net);
					if (info != null && info.isConnected()) {
						return true;
					}
				}
			} catch (Exception e) {// error would happen if the connection type is not available
				e.printStackTrace();
			}
			// other types?
			return false;
		}
	}
}
