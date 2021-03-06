package app.kimyeonjung.trendreader.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.fcm.FCMRegister;
import app.kimyeonjung.trendreader.core.tab.MainTabAdapter;
import app.kimyeonjung.trendreader.ui.feed.BookMarkView;
import app.kimyeonjung.trendreader.ui.feed.FeedView;
import app.kimyeonjung.trendreader.ui.setting.SettingsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAnalytics.getInstance(this);
        new FCMRegister().refreshSubscribe(this);
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
        final TabLayout tabLayout = findViewById(R.id.main_tab);
        final ViewPager viewPager = findViewById(R.id.main_viewpager);

        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager());
        adapter.addFragment(R.drawable.ic_search, "", new FeedView());
        adapter.addFragment(R.drawable.ic_bookmark_list, "", new BookMarkView());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.getTabAt(i).setIcon(adapter.getFragmentInfo(i).getIconResId());
            switch (i) {
                case 0:
                    tabLayout.getTabAt(i).getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    break;
                default:
                    tabLayout.getTabAt(i).getIcon().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);
            }
        }

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.getIcon().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
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
            case R.id.nav_about_dev:
                startActivity(new Intent(this, AboutDev.class));
                break;
            case R.id.nav_about_app:
                startActivity(new Intent(this, AboutApp.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
