package app.kimyeonjung.trendreader.ui.setting;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.core.fcm.FCMRegister;
import app.kimyeonjung.trendreader.data.FeedItem;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private Realm realm;

    private enum REMOVE_TYPE {ALL, FEED, BOOKMARK}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(v -> finish());

        realm = Realm.getInstance(Const.DB.getFeedDBConfig());

        initClickListener();
    }

    private void initClickListener() {
        findPreference(getString(R.string.pref_delete_feed_db)).setOnPreferenceClickListener(preference -> {
            removeData(REMOVE_TYPE.FEED);
            return true;
        });

        findPreference(getString(R.string.pref_delete_bookmark_db)).setOnPreferenceClickListener(preference -> {
            removeData(REMOVE_TYPE.BOOKMARK);
            return true;
        });

        findPreference(getString(R.string.pref_delete_db)).setOnPreferenceClickListener(preference -> {
            removeData(REMOVE_TYPE.ALL);
            return true;
        });

        findPreference(getString(R.string.pref_request_blog_add)).setOnPreferenceClickListener(preference -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
            builder.setShowTitle(true);
            customTabsIntent.launchUrl(this, Uri.parse(Const.API_URL.ADD_BLOG_REQUEST));
            return true;
        });

        findPreference(getString(R.string.pref_request_feature_add)).setOnPreferenceClickListener(preference -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
            builder.setShowTitle(true);
            customTabsIntent.launchUrl(this, Uri.parse(Const.API_URL.FEATURE_REQUEST));
            return true;
        });
    }

    private void removeData(REMOVE_TYPE type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.pref_remove_confirm));
        builder.setMessage(type.name());
        builder.setPositiveButton(android.R.string.yes,
                (dialog, which) -> {
                    RealmQuery<FeedItem> query = realm.where(FeedItem.class);
                    switch (type) {
                        case FEED:
                            query.equalTo("isBookMarked", false);
                            break;
                        case BOOKMARK:
                            query.equalTo("isBookMarked", true);
                            break;
                        default:
                    }
                    realm.beginTransaction();
                    if (type == REMOVE_TYPE.BOOKMARK) {
                        RealmResults<FeedItem> bookMarkList = query.findAll();
                        for (FeedItem item : bookMarkList) {
                            item.setBookMarked(false);
                        }
                    } else {
                        query.findAll().deleteAllFromRealm();
                    }
                    realm.commitTransaction();
                });
        builder.setNegativeButton(android.R.string.no,
                (dialog, which) -> dialog.dismiss());
        builder.show();
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

    @Override
    protected void onPause() {
        new FCMRegister().refreshSubscribe(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        realm.close();
        super.onStop();
    }
}
