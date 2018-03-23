package preference;


import com.charlie.demo0108.R;
import android.os.Bundle;
import android.preference.Preference;

public class FragmentGeneralSoundSetting extends FragmentPreferenceBase{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_sound_setting);
		
		Preference preferenceVolume=getPreferenceScreen().findPreference(getString(R.string.key_sound_volume));
		
	}

}
