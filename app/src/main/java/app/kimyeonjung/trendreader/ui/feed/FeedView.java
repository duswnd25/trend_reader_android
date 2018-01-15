package app.kimyeonjung.trendreader.ui.feed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.yalantis.phoenix.PullToRefreshView;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.data.feed.FeedAdapter;
import app.kimyeonjung.trendreader.data.feed.FeedManager;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class FeedView extends Fragment {

    private ShimmerRecyclerView feedView;
    private PullToRefreshView refreshView;
    private Realm realm;

    public FeedView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Prefs
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);
        int staggerColSize = prefs.getInt(getString(R.string.pref_feed_col_num), 1);

        // 리스트뷰
        feedView = view.findViewById(R.id.fragment_recycler_view);
        refreshView = view.findViewById(R.id.fragment_recycler_refresh);
        FloatingActionButton upToTopButton = view.findViewById(R.id.fragment_recycler_up_to_top);

        // DB
        realm = Realm.getInstance(Const.DB.getFeedDBConfig());
        RealmQuery<FeedItem> query = realm.where(FeedItem.class).sort("updateAt", Sort.DESCENDING);
        RealmResults<FeedItem> feedList = query.findAll();

        // UpToButton
        upToTopButton.setOnClickListener(view1 -> feedView.smoothScrollToPosition(0));

        // Feed View
        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(staggerColSize, StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        FeedAdapter feedAdapter = new FeedAdapter(getContext(), isPaletteUse, feedList);
        feedView.setGridChildCount(staggerColSize+2);
        feedView.setLayoutManager(feedLayoutManager);
        feedView.setAdapter(feedAdapter);
        feedView.setNestedScrollingEnabled(true);
        feedView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    upToTopButton.show();
                } else {
                    upToTopButton.hide();
                }
            }
        });

        // Refresh View
        refreshView.setOnRefreshListener(this::feedFetch);

        feedFetch();
    }

    private void feedFetch() {
        feedView.showShimmerAdapter();
        refreshView.setRefreshing(true);
        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            for (FeedItem item : result) {
                RealmQuery<FeedItem> query = realm.where(FeedItem.class);
                if (query.equalTo("postUrl", item.getPostUrl()).count() == 0) {
                    realm.beginTransaction();
                    realm.copyToRealm(item);
                    realm.commitTransaction();
                }
            }
            feedView.hideShimmerAdapter();
            refreshView.setRefreshing(false);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }
}