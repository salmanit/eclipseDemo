package preference;

import java.util.List;
import com.charlie.demo0108.R;
import com.charlie.demo0108.simple.ActivityBrightness;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

public class ActivityPreferenceSetting extends ActivityPreferenceBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.setting_headers, target);
		Header header=new Header();
		header.title="动态添加的";
		header.summary="看需求要不要summary决定";
		header.iconRes=R.drawable.adp_settingsactivity_information_icon;
		header.fragment="preference.FragmentGeneral";
		header.fragmentArguments=new Bundle();
//		header.intent=new Intent(this,ActivityBrightness.class);
		target.add(header);
		super.onBuildHeaders(target);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		System.err.println(
				getClass().getSimpleName() + " onPreferenceTreeClick=================" + preference.getTitle());
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public void onHeaderClick(Header header, int position) {
		System.err.println(getClass().getSimpleName() + " onHeaderClick=================" + header.title);
		super.onHeaderClick(header, position);
	}
}
