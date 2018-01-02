package app.kimyeonjung.trendreader.ui.feed;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Date;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.core.otto.BookMarkEvent;
import app.kimyeonjung.trendreader.core.otto.BusProvider;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class FeedDetailView extends AppCompatActivity {
    private FeedItem feedItem;
    private MenuItem bookMarkItem;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        realm = Realm.getInstance(Const.DB.getFeedDBConfig());
        feedItem = getIntent().getParcelableExtra(Const.INTENT.POST_DATA);

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
        RealmQuery<FeedItem> query = realm.where(FeedItem.class).equalTo("postUrl", feedItem.getPostUrl());
        FeedItem temp = query.findFirst();
        realm.beginTransaction();
        temp.setBookMarked(!temp.isBookMarked());
        if (temp.isBookMarked()) {
            temp.setBookMarkAt(new Date());
        }
        realm.commitTransaction();

        changeBookMarkIcon();

        new StyleableToast
                .Builder(this)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .iconResLeft(!feedItem.isBookMarked() ? R.drawable.ic_bookmark_remove_fill : R.drawable.ic_bookmark_fill)
                .text(!feedItem.isBookMarked() ? getString(R.string.action_bookmark_remove) : getString(R.string.action_bookmark_save))
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
        changeBookMarkIcon();
        return super.onPrepareOptionsMenu(menu);
    }

    private void changeBookMarkIcon() {
        // 북마크 여부에 따라 변경
        bookMarkItem.setIcon(feedItem.isBookMarked() ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark);
    }

    @Override
    protected void onStop() {
        realm.close();
        super.onStop();
    }
}
