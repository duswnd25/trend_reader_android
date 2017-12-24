package app.kimyeonjung.trendreader.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.ui.feed.FeedBookMark;
import app.kimyeonjung.trendreader.ui.feed.FeedSearch;
import app.kimyeonjung.trendreader.ui.feed.FeedTrend;
import app.kimyeonjung.trendreader.ui.setting.SettingsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        initTabView();
    }

    private void initTabView() {

        FragmentPagerItemAdapter tabAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.title_feed_all, FeedSearch.class)
                .add(R.string.title_feed_trend, FeedTrend.class)
                .add(R.string.title_feed_book_mark, FeedBookMark.class)
                .create());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabAdapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewpager_tab);

        final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());
        final Resources res = viewPagerTab.getContext().getResources();

        viewPagerTab.setCustomTabView((container, position, adapter) -> {
            ImageView icon = (ImageView) inflater.inflate(R.layout.smart_tab_icon, container,
                    false);
            switch (position) {
                case 0:
                    icon.setImageDrawable(res.getDrawable(R.drawable.ic_feed));
                    break;
                case 1:
                    icon.setImageDrawable(res.getDrawable(R.drawable.ic_search));
                    break;
                case 2:
                    icon.setImageDrawable(res.getDrawable(R.drawable.ic_database));
                    break;
                default:
                    throw new IllegalStateException("Invalid position: " + position);
            }
            return icon;
        });

        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutDev.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
