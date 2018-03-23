package permission;

import com.charlie.demo0108.R;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;
import web.ActivityWeb;

public class TestPermission extends Activity {

	private static final int REQUEST_EXTERNAL_STORAGE = 1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission);

	}

//	public void get(View v) {
//
//		String[] permissions;
//		// 读取内存权限
//		permissions = new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };
//		if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//			// 如果已经获得权限
//			String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
//			Intent intent = new Intent(TestPermission.this, ActivityWeb.class);
//			intent.putExtra("dir", dir);
//			intent.putExtra("title", "文件管理");
//			startActivity(intent);
//
//		} else {
//			// 否则去获取权限
//			getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, permissions);
//		}
//
//	}
//
//	// 检查某个权限是否已经获得
//	private boolean checkPermission(String permission) {
//		// 检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
//		if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
//			return true;
//		else
//			return false;
//	}
//
//	// 获取权限
//	private void getPermission(String permission, String[] permissions) {
//
//		// 申请权限
//		ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE);
//
//		// 用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
//		if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
//			Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
//
//	}
//
//	/**
//	 * 一个或多个权限请求结果回调
//	 *
//	 * @param requestCode
//	 * @param permissions
//	 * @param grantResults
//	 */
//	// 当点击了不在询问，但是想要实现某个功能，必须要用到权限，可以提示用户，引导用户去设置
//	@Override
//	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//			@NonNull int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//		boolean hasAllGranted = true;
//		for (int i = 0; i < grantResults.length; ++i) {
//			if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//				hasAllGranted = false;
//				// 在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
//				// 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
//				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//					// 解释原因，并且引导用户至设置页手动授权
//					new AlertDialog.Builder(this).setMessage("\r\n" + "获取相关权限失败:xxxxxx,将导致部分功能无法正常使用，需要到设置页面手动授权")
//							.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// 引导用户至设置页手动授权
//									Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//									Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
//									intent.setData(uri);
//									startActivity(intent);
//								}
//							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// 引导用户手动授权，权限请求失败
//									Toast.makeText(TestPermission.this, "权限获取失败", Toast.LENGTH_SHORT).show();
//								}
//							}).setOnCancelListener(new DialogInterface.OnCancelListener() {
//								@Override
//								public void onCancel(DialogInterface dialog) {
//									// 引导用户手动授权，权限请求失败
//								}
//							}).show();
//
//				} else {
//					// 权限请求失败，但未选中“不再提示”选项
//				}
//				break;
//			}
//		}
//		if (hasAllGranted) {
//
//		}
//	}
}
