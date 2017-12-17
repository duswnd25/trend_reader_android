package app.kimyeonjung.trendreader.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;

public class DetailView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent intent = getIntent();
        String blogTitle = intent.getStringExtra(Const.INTENT.BLOG_NAME);
        String faviconUrl = intent.getStringExtra(Const.INTENT.FAVICON_URL);
        String postTitle = intent.getStringExtra(Const.INTENT.POST_TITLE);
        String postContent = intent.getStringExtra(Const.INTENT.POST_CONTENT);
        String postUrl = intent.getStringExtra(Const.INTENT.POST_URL);

    }
}
