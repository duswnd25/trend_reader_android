package app.kimyeonjung.trendreader.ui.setting;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.ui.setting.AppCompatPreferenceActivity;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_setting);

        initClickListener();
    }

    private void initClickListener() {
        // 텍스트크기
        findPreference(getString(R.string.pref_feed_col_num)).setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.preference.Preference preference) {
                return false;
            }
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
