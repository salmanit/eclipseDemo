package preference;

import com.charlie.demo0108.R;
import com.charlie.demo0108.simple.ActivityBrightness;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

public class FragmentGeneral extends FragmentPreferenceBase{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_general);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		System.err.println(getClass().getSimpleName()+" onPreferenceTreeClick================="+preference.getTitle());
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
}
