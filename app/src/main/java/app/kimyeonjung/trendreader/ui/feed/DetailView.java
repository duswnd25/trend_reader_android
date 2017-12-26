package app.kimyeonjung.trendreader.ui.feed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DetailView extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(intent.getStringExtra(Const.INTENT.BLOG_NAME));
        toolbar.setNavigationOnClickListener(v -> finish());

        initView(toolbar);
    }

    private void initView(Toolbar toolbar) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);

        String blogTitle = intent.getStringExtra(Const.INTENT.BLOG_NAME);
        String faviconUrl = intent.getStringExtra(Const.INTENT.FAVICON_URL);
        String postTitle = intent.getStringExtra(Const.INTENT.POST_TITLE);
        String postContent = intent.getStringExtra(Const.INTENT.POST_CONTENT);
        String postUrl = intent.getStringExtra(Const.INTENT.POST_URL);

        toolbar.setTitle(postTitle);
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
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
