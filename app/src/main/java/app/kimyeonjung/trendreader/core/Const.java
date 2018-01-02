package app.kimyeonjung.trendreader.core;

import io.realm.RealmConfiguration;

public class Const {
    public class API_URL {
        public static final String ALL = "https://trendreader.herokuapp.com/api/data/read/blog/all";
    }

    public static class DB {
        public static RealmConfiguration getFeedDBConfig() {
            return new RealmConfiguration.Builder()
                    .name("feed.realm")
                    .schemaVersion(1)
                    .build();
        }
    }

    public class INTENT {
        public static final String FEED_POST = "feed_item";
    }
}
