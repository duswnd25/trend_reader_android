package app.kimyeonjung.trendreader.ui.bookmark;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import io.realm.Realm;
import io.realm.RealmQuery;

public class BookMarkDetailView extends AppCompatActivity {
    private FeedItem feedItem;
    private boolean isBookMarked = false;
    private MenuItem bookMarkItem;

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

        ((TextView) findViewById(R.id.detail_text)).setText(feedItem.getPostContent());

        Glide.with(this)
                .load(feedItem.getFaviconUrl())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into((ImageView) findViewById(R.id.detail_favicon));
    }

    private void changeBookMarkState() {

        try (Realm realm = Realm.getInstance(Const.DB.getBookMarkConfig())) {

            if (isBookMarked) {
                RealmQuery<FeedItem> query = realm.where(FeedItem.class);
                query.equalTo("postUrl", feedItem.getPostUrl());
                FeedItem temp = query.findFirst();
                if (temp != null) {
                    realm.beginTransaction();
                    temp.deleteFromRealm();
                    realm.commitTransaction();
                }
            } else {
                realm.beginTransaction();
                FeedItem feed = realm.createObject(FeedItem.class);
                feed.setBlogName(feedItem.getBlogName());
                feed.setBlogUrl(feedItem.getBlogUrl());
                feed.setFaviconUrl(feedItem.getFaviconUrl());
                feed.setPostContent(feedItem.getPostContent());
                feed.setPostTitle(feedItem.getPostTitle());
                feed.setPostUrl(feedItem.getPostUrl());
                feed.setUpdateAt(feedItem.getUpdateAt());
                realm.commitTransaction();
            }

            new StyleableToast
                    .Builder(this)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .iconResLeft(isBookMarked ? R.drawable.ic_bookmark_remove_fill : R.drawable.ic_bookmark_fill)
                    .text(isBookMarked ? getString(R.string.action_bookmark_remove) : getString(R.string.action_bookmark_save))
                    .show();

            isBookMarked = !isBookMarked;
            changeBookMarkIcon();
        }
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
        /*
        색상필터
        for (int index = 0; index < menu.size(); index++) {
            Drawable icon = menu.getItem(index).getIcon();
            icon.mutate();
            icon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
        */
        bookMarkItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 북마크 여부에 따른 초기 아이콘 변경
        try (Realm realm = Realm.getInstance(Const.DB.getBookMarkConfig())) {
            RealmQuery<FeedItem> query = realm.where(FeedItem.class);
            query.equalTo("postUrl", feedItem.getPostUrl());
            isBookMarked = query.count() != 0;
            changeBookMarkIcon();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void changeBookMarkIcon() {
        // 북마크 여부에 따라 변경
        bookMarkItem.setIcon(isBookMarked ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark);
    }
}
