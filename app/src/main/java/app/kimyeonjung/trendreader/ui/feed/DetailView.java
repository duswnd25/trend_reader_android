package app.kimyeonjung.trendreader.ui.feed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;

public class DetailView extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(intent.getStringExtra(Const.INTENT.POST_TITLE));
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);

        String faviconUrl = intent.getStringExtra(Const.INTENT.FAVICON_URL);
        String postContent = intent.getStringExtra(Const.INTENT.POST_CONTENT);
        String postUrl = intent.getStringExtra(Const.INTENT.POST_URL);

        ((TextView) findViewById(R.id.detail_text)).setText(postContent);

        Glide.with(this).load(faviconUrl).asBitmap()
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
}
