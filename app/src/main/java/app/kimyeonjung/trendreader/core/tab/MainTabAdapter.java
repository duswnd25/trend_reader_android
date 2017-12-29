package app.kimyeonjung.trendreader.core.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainTabAdapter extends FragmentPagerAdapter {

    private final List<TabInfo> mFragmentInfoList = new ArrayList<>();

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(int iconResId, String title, Fragment fragment) {
        TabInfo info = new TabInfo(iconResId, title, fragment);
        mFragmentInfoList.add(info);
    }

    public TabInfo getFragmentInfo(int position) {
        return mFragmentInfoList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentInfoList.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentInfoList.get(position).getTitleText();
    }

    @Override
    public int getCount() {
        return mFragmentInfoList.size();
    }
}