package app.kimyeonjung.trendreader.core;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

public class TrendReader extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
