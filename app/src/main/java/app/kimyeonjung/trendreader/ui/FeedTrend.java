package app.kimyeonjung.trendreader.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.info.FeedAdapter;
import app.kimyeonjung.trendreader.data.info.FeedItem;
import app.kimyeonjung.trendreader.data.info.FeedManager;

public class FeedTrend extends Fragment {

    public FeedTrend() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final LinkedList<FeedItem> feedList = new LinkedList<>();
        final FeedAdapter feedAdapter = new FeedAdapter(getContext(), prefs.getBoolean(getString(R.string.pref_feed_palette_use), false), feedList);

        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(
                prefs.getInt(getString(R.string.pref_feed_col_num), 1),
                StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        RecyclerView feedView = view.findViewById(R.id.fragment_recycler_view);
        feedView.setNestedScrollingEnabled(true);
        feedView.setHasFixedSize(true);
        feedView.setLayoutManager(feedLayoutManager);
        feedView.setAdapter(feedAdapter);
        feedView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            feedList.addAll(result);
            feedAdapter.notifyDataSetChanged();
        });
    }
}