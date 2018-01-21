package app.kimyeonjung.trendreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.data.about.LibraryAdapter;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Toolbar toolbar = findViewById(R.id.about_app_toolbar);
        toolbar.setTitle(getString(R.string.title_about_dev));
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView libraryListView = findViewById(R.id.about_app_recycler);
        libraryListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        libraryListView.setLayoutManager(mLayoutManager);

        LinkedList<String> itemList = new LinkedList<>();
        itemList.add("ocm.android.support:appcompat-v7");
        itemList.add("com.android.support:design");
        itemList.add("com.android.support.constraint:constraint-layout");
        itemList.add("com.android.support:support-v4");
        itemList.add("com.android.support:cardview-v7");
        itemList.add("com.android.support:palette-v7");
        itemList.add("com.android.support:recyclerview-v7");
        itemList.add("com.android.support:multidex");
        itemList.add("com.android.support:support-vector-drawable");
        itemList.add("com.squareup:otto");
        itemList.add("com.squareup.okhttp:okhttp");
        itemList.add("com.github.bumptech.glide:glide");
        itemList.add("com.github.jrvansuita:MaterialAbout");
        itemList.add("com.github.sharish:ShimmerRecyclerView");
        itemList.add("com.muddzdev:styleabletoast");
        itemList.add("com.squareup:otto");
        itemList.add("com.yalantis:phoenix");
        itemList.add("io.realm:android-adapters");
        itemList.add("com.crashlytics.sdk.android:crashlytics");
        itemList.add("com.google.firebase:firebase-core:");
        itemList.add("com.google.firebase:firebase-messaging");

        LibraryAdapter galleryAdapter;
        galleryAdapter = new LibraryAdapter(itemList);
        libraryListView.setAdapter(galleryAdapter);
    }
}
