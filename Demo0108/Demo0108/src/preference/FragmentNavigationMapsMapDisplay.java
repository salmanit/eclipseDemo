package preference;

import com.charlie.demo0108.R;

import android.os.Bundle;

public class FragmentNavigationMapsMapDisplay extends FragmentPreferenceBase{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_navigation_maps_map_display);
	}

}
