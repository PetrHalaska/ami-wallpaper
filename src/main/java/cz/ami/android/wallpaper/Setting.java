package cz.ami.android.wallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Setting extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(AMIWallpaper.SHARED_PREFS_NAME);
        addPreferencesFromResource(R.xml.setting);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
