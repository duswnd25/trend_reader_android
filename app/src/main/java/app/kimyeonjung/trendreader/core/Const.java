package app.kimyeonjung.trendreader.core;

import io.realm.RealmConfiguration;

public class Const {
    public class API_URL {
        public static final String ALL = "https://trendreader.herokuapp.com/api/data/read/blog/all";
        public static final String ADD_BLOG_REQUEST = "https://goo.gl/forms/SWYqxD3ttP8BN0rD3";
        public static final String FEATURE_REQUEST = "https://goo.gl/forms/2bn0JRgrvSMuO8rw2";
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
