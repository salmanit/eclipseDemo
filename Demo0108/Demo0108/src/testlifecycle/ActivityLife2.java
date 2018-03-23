package testlifecycle;

import org.json.JSONException;
import org.json.JSONObject;

import com.charlie.demo0108.R;
import android.annotation.SuppressLint;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.service.voice.VoiceInteractionSessionService;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class ActivityLife2 extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		println(getLocalClassName() + "`````````onCreate1");
		super.onCreate(arg0);
		setContentView(R.layout.activity_life2);
		println(getLocalClassName() + "`````````onCreate2");
		
		
		
		
	}

	private BroadcastReceiver mMountEventReceiver = null;

	private void registerMountEventReceiver() {
		if (mMountEventReceiver == null) {
			mMountEventReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
						String path = intent.getData().getPath();
						System.err.println("mounted===============" + path);
					}
				}
			};
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
			filter.addDataScheme("file");
			registerReceiver(mMountEventReceiver, filter);
		}
	}

	private void unregisterMountEventReceiver() {
		if (mMountEventReceiver != null) {
			unregisterReceiver(mMountEventReceiver);
			mMountEventReceiver = null;
		}
	}
	WakeLock wakeLock;
	private void createWakeLock() {
		if(wakeLock==null) {
			PowerManager powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock=powerManager.newWakeLock(powerManager.PARTIAL_WAKE_LOCK, getLocalClassName());
			wakeLock.setReferenceCounted(false);
		}
		wakeLock.acquire();
	}
	private void realseWakeLock() {
		if(wakeLock!=null) {
			if(wakeLock.isHeld()) {
				wakeLock.release();
			}
		}
	}
	@Override
	protected void onResume() {
		println(getLocalClassName() + "```````onResume");
		super.onResume();
		registerMountEventReceiver();
		createWakeLock();
	}

	@Override
	protected void onStart() {
		println(getLocalClassName() + "``````onStart");
		super.onStart();
	}

	@Override
	protected void onPause() {
		println(getLocalClassName() + "```````onPause");
		unregisterMountEventReceiver();
		super.onPause();
		realseWakeLock();
	}

	@Override
	protected void onStop() {
		println(getLocalClassName() + "````````onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		println(getLocalClassName() + "`````````onDestroy");
		super.onDestroy();
	}

	private void println(String msg) {
		System.err.println("=======================" + msg);
	}

	@SuppressLint("NewApi")
	@Override
	public void onProvideAssistContent(AssistContent assistContent) {
		super.onProvideAssistContent(assistContent);
		System.err.println("onProvideAssistContent============================="+assistContent.getStructuredData()+" =="+assistContent.getWebUri());
		String structuredJson= "{}";
		try {
			structuredJson = new JSONObject().put("@type", "MusicRecording")
					.put("@id", "https://example.com/music/recording").put("name", "Album Title").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assistContent.setStructuredData(structuredJson);
	}
	
	@Override
	public void onUserInteraction() {
		System.err.println(getLocalClassName()+"===============onUserInteraction");
		//���Խ����������Ļ����������home�������˼������ܼ����������˵�Դ�������Դ�����
		//ǰ�ᶼ������Ļ�����������£���������Ժ�����Դ����������Ļ����ʱ����Ļ��������״̬����ô�ϱ�˵���¼���û��
		super.onUserInteraction();
	}
	
	@Override
	protected void onUserLeaveHint() {
		System.err.println(getLocalClassName()+"===============onUserLeaveHint");
		//����о�����Ӧ�ý����̨�Żᴥ����Ҳ���ǰ�home���ص������棬���߰����ܼ���ʾ�����Ӧ��
		super.onUserLeaveHint();
	}
}
