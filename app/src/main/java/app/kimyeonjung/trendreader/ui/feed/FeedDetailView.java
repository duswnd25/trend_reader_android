package app.kimyeonjung.trendreader.ui.feed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import io.realm.Realm;
import io.realm.RealmQuery;

public class FeedDetailView extends AppCompatActivity {
    private FeedItem feedItem;
    private MenuItem bookMarkItem;
    private Realm realm;
    private List<MenuItem> menuIcons = new LinkedList<>();
    private int nowColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        realm = Realm.getInstance(Const.DB.getFeedDBConfig());
        feedItem = getIntent().getParcelableExtra(Const.INTENT.FEED_POST);
        nowColor = getResources().getColor(R.color.colorPrimary);

        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(feedItem.getPostTitle());
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.detail_text)).setText(feedItem.getPostContent());

        // Favicon Image View
        Glide.with(this)
                .load(feedItem.getFaviconUrl())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(findViewById(R.id.detail_favicon)) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);
                        setOffsetListener(bitmap);
                    }
                });
    }

    private void setOffsetListener(Bitmap bitmap) {
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch vibrantSwatch = palette.getLightVibrantSwatch();
        ((AppBarLayout) findViewById(R.id.detail_appbar)).addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset < -200) {
                nowColor = getResources().getColor(R.color.colorPrimary);
            } else {
                nowColor = vibrantSwatch == null ? getResources().getColor(android.R.color.white) : vibrantSwatch.getRgb();
            }
            for (MenuItem item : menuIcons) {
                item.getIcon().mutate();
                item.getIcon().setColorFilter(nowColor, PorterDuff.Mode.SRC_IN);
            }
        });
    }

    private void changeBookMarkState() {
        RealmQuery<FeedItem> query = realm.where(FeedItem.class).equalTo("postUrl", feedItem.getPostUrl());
        FeedItem temp = query.findFirst();

        realm.beginTransaction();
        temp.setBookMarked(!temp.isBookMarked());
        temp.setBookMarkAt(new Date());
        realm.commitTransaction();

        feedItem.setBookMarked(temp.isBookMarked());

        changeBookMarkIcon();

        new StyleableToast
                .Builder(this)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .iconResLeft(feedItem.isBookMarked() ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark_remove_fill)
                .text(feedItem.isBookMarked() ? getString(R.string.action_bookmark_save) : getString(R.string.action_bookmark_remove))
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_bookmark:
                changeBookMarkState();
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

        for (int index = 0; index < menu.size(); index++) {
            menuIcons.add(menu.getItem(index));
        }
        bookMarkItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        changeBookMarkIcon();
        return super.onPrepareOptionsMenu(menu);
    }

    private void changeBookMarkIcon() {
        // 북마크 여부에 따라 변경
        bookMarkItem.setIcon(feedItem.isBookMarked() ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark);
        bookMarkItem.getIcon().setColorFilter(nowColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onStop() {
        realm.close();
        super.onStop();
    }
}
