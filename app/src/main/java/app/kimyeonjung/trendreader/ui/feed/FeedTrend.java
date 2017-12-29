package app.kimyeonjung.trendreader.ui.feed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.data.feed.FeedAdapter;
import app.kimyeonjung.trendreader.data.feed.FeedManager;

public class FeedTrend extends Fragment {

    private Context context;
    private LinkedList<FeedItem> feedList = new LinkedList<>();
    private FeedAdapter feedAdapter;

    public FeedTrend() {

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), false);
        int staggerColSize = prefs.getInt(getString(R.string.pref_feed_col_num), 2);

        feedAdapter = new FeedAdapter(getContext(), isPaletteUse, feedList);

        initView(view, staggerColSize);
        initData(view);
    }

    private void initView(View view, int staggerColSize) {

        // StaggerGridLayout
        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(staggerColSize, StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        // RecyclerView
        ShimmerRecyclerView feedView = view.findViewById(R.id.fragment_recycler_view);
        feedView.showShimmerAdapter();
        feedView.setNestedScrollingEnabled(true);
        feedView.setHasFixedSize(true);
        feedView.setLayoutManager(feedLayoutManager);
        feedView.setAdapter(feedAdapter);

        // Scroll Change Listener
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
    }

    private void initData(View view) {

        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            feedList.addAll(result);
            feedAdapter.notifyDataSetChanged();
        });
    }
}