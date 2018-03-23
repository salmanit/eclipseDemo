package com.charlie.demo0108;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ApplicationInfoUtil {
	public static final int DEFAULT = 0; // 默认 所有应用
	public static final int SYSTEM_APP = DEFAULT + 1; // 系统应用
	public static final int NONSYSTEM_APP = DEFAULT + 2; // 非系统应用

	public static String getProgramNameByPackageName(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		String name = null;
		try {
			name = pm.getApplicationLabel(
					pm.getApplicationInfo(packageName,
							PackageManager.GET_META_DATA)).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return name;
	}

	public static ArrayList<AppInfo> getAllProgramInfo(
			Context context) {
		return getAllProgramInfo( context, DEFAULT);
	}

	public static ArrayList<AppInfo> getAllProgramInfo(Context context, int type) {
		ArrayList<AppInfo> applist = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);

		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(
					context.getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(context
					.getPackageManager());
			switch (type) {
			case NONSYSTEM_APP:
				if (!isSystemAPP(packageInfo)) {
					applist.add(tmpInfo);
				}
				break;
			case SYSTEM_APP:
				if (isSystemAPP(packageInfo)) {
					applist.add(tmpInfo);
				}
				break;
			default:
				applist.add(tmpInfo);
				break;
			}

		}
		return applist;
	}

	public static ArrayList<AppInfo> getAllSystemProgramInfo(Context context) {
		return getAllProgramInfo( context, SYSTEM_APP);
	}

	public static ArrayList<AppInfo> getAllNonsystemProgramInfo(Context context) {
		return getAllProgramInfo( context, NONSYSTEM_APP);
	}

	public static Boolean isSystemAPP(PackageInfo packageInfo) {
		if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { // 非系统应用
			return false;
		} else { // 系统应用
			return true;
		}
	}
	
	/**get packageInfo By packageName*/
	public static PackageInfo getPackageInfo(Context context, String packageName) throws Exception {  
	    // 获取packagemanager的实例  
	    PackageManager packageManager = context.getPackageManager();  
	    PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);  
	    return packInfo;
	}
	/**@param archiveFilePath sdcard apk location*/
	public static PackageInfo getPackageInfoFromApk(Context context, String archiveFilePath) {  
	    PackageManager pm = context.getPackageManager();  
	    PackageInfo packInfo = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);  
	    return packInfo;
	}
}
