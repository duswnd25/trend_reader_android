package app.kimyeonjung.trendreader.ui.bookmark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.Date;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import io.realm.Realm;

public class BookMarkDetailView extends AppCompatActivity {
    private FeedItem feedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        feedItem = (FeedItem) getIntent().getSerializableExtra(Const.INTENT.POST_DATA);

        initView();
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(feedItem.getPostTitle());
        setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);

        ((TextView) findViewById(R.id.detail_text)).setText(feedItem.getPostContent());

        Glide.with(this).load(feedItem.getFaviconUrl()).asBitmap()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(findViewById(R.id.detail_favicon)) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);

                        if (isPaletteUse) {
                            Palette palette = Palette.from(bitmap).generate();
                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                            if (vibrantSwatch != null) {

                            }
                        }
                    }
                });


    }

    private void addBookMark() {

        try (Realm realm = Realm.getInstance(Const.DB.getBookMarkConfig())) {

            realm.beginTransaction();

            FeedItem feed = realm.createObject(FeedItem.class);
            feed = realm.copyToRealm(feedItem);
            realm.commitTransaction();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_bookmark:
                addBookMark();
                break;
            case R.id.action_link:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(feedItem.getPostUrl())));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
}
