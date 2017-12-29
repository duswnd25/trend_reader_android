package app.kimyeonjung.trendreader.ui.setting;


import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.otto.BusProvider;
import app.kimyeonjung.trendreader.core.otto.PreferenceEvent;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(v -> finish());

        initClickListener();
    }

    private void initClickListener() {
        // 텍스트크기
        findPreference(getString(R.string.pref_feed_col_num)).setOnPreferenceChangeListener((preference, o) -> {
            BusProvider.getInstance().post(new PreferenceEvent());
            return false;
        });
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
