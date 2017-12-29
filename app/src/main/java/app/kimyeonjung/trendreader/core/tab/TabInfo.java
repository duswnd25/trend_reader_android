package app.kimyeonjung.trendreader.core.tab;

import android.support.v4.app.Fragment;

public class TabInfo {
    private int iconResId;
    private String text;
    private Fragment fragment;

    public TabInfo(int iconResId, String text, Fragment fragment) {
        this.iconResId = iconResId;
        this.text = text;
        this.fragment = fragment;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitleText() {
        return text;
    }

    public Fragment getFragment() {
        return fragment;
    }
}