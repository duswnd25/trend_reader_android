package app.kimyeonjung.trendreader.data.feed.book_mark;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.data.feed.FeedItem;

public class BookMarkManager {
    private OnFinishCallback callback;

    public void fetchFeed(String requestUrl, OnFinishCallback callback) {

        this.callback = callback;
    }

    public interface OnFinishCallback {

        void done(LinkedList<FeedItem> result);
    }

    public BookMarkManager() {
    }
}
