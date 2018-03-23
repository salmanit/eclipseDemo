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
//		// ��ȡ�ڴ�Ȩ��
//		permissions = new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };
//		if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//			// ����Ѿ����Ȩ��
//			String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
//			Intent intent = new Intent(TestPermission.this, ActivityWeb.class);
//			intent.putExtra("dir", dir);
//			intent.putExtra("title", "�ļ�����");
//			startActivity(intent);
//
//		} else {
//			// ����ȥ��ȡȨ��
//			getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, permissions);
//		}
//
//	}
//
//	// ���ĳ��Ȩ���Ƿ��Ѿ����
//	private boolean checkPermission(String permission) {
//		// ���Ȩ�ޣ�NEED_PERMISSION���Ƿ���Ȩ PackageManager.PERMISSION_GRANTED��ʾͬ����Ȩ
//		if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
//			return true;
//		else
//			return false;
//	}
//
//	// ��ȡȨ��
//	private void getPermission(String permission, String[] permissions) {
//
//		// ����Ȩ��
//		ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE);
//
//		// �û��Ѿ��ܾ���һ�Σ��ٴε���Ȩ������Ի�����Ҫ���û�һ������
//		if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
//			Toast.makeText(this, "�뿪ͨ���Ȩ�ޣ������޷�����ʹ�ñ�Ӧ�ã�", Toast.LENGTH_SHORT).show();
//
//	}
//
//	/**
//	 * һ������Ȩ���������ص�
//	 *
//	 * @param requestCode
//	 * @param permissions
//	 * @param grantResults
//	 */
//	// ������˲���ѯ�ʣ�������Ҫʵ��ĳ�����ܣ�����Ҫ�õ�Ȩ�ޣ�������ʾ�û��������û�ȥ����
//	@Override
//	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//			@NonNull int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//		boolean hasAllGranted = true;
//		for (int i = 0; i < grantResults.length; ++i) {
//			if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//				hasAllGranted = false;
//				// ���û��Ѿ��ܾ���Ȩ������£����shouldShowRequestPermissionRationale����false��
//				// �����ƶϳ��û�ѡ���ˡ�������ʾ��ѡ��������������Ҫ�����û�������ҳ�ֶ���Ȩ
//				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//					// ����ԭ�򣬲��������û�������ҳ�ֶ���Ȩ
//					new AlertDialog.Builder(this).setMessage("\r\n" + "��ȡ���Ȩ��ʧ��:xxxxxx,�����²��ֹ����޷�����ʹ�ã���Ҫ������ҳ���ֶ���Ȩ")
//							.setPositiveButton("ȥ��Ȩ", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// �����û�������ҳ�ֶ���Ȩ
//									Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//									Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
//									intent.setData(uri);
//									startActivity(intent);
//								}
//							}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// �����û��ֶ���Ȩ��Ȩ������ʧ��
//									Toast.makeText(TestPermission.this, "Ȩ�޻�ȡʧ��", Toast.LENGTH_SHORT).show();
//								}
//							}).setOnCancelListener(new DialogInterface.OnCancelListener() {
//								@Override
//								public void onCancel(DialogInterface dialog) {
//									// �����û��ֶ���Ȩ��Ȩ������ʧ��
//								}
//							}).show();
//
//				} else {
//					// Ȩ������ʧ�ܣ���δѡ�С�������ʾ��ѡ��
//				}
//				break;
//			}
//		}
//		if (hasAllGranted) {
//
//		}
//	}
}
