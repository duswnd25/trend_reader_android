package app.kimyeonjung.trendreader.ui.feed;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.feed.fetch.FeedAdapter;
import app.kimyeonjung.trendreader.data.feed.FeedItem;
import app.kimyeonjung.trendreader.data.feed.fetch.FeedManager;

public class FeedSearch extends Fragment {

    private Context context;
    private LinkedList<FeedItem> feedList = new LinkedList<>(), feedListFiltered = new LinkedList<>();
    private FeedAdapter feedAdapter;

    public FeedSearch() {

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.feed_search, menu);
        initSearchView(menu.findItem(R.id.menu_search));
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
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);
        int staggerColSize = prefs.getInt(getString(R.string.pref_feed_col_num), 2);

        feedAdapter = new FeedAdapter(getContext(), isPaletteUse, feedListFiltered);

        initView(view, staggerColSize);
    }

    private void initView(View view, int staggerColSize) {

        // StaggerGridLayout
        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(staggerColSize, StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        // RecyclerView
        ShimmerRecyclerView feedView;
        feedView = view.findViewById(R.id.fragment_recycler_view);
        feedView.setNestedScrollingEnabled(true);
        feedView.setLayoutManager(feedLayoutManager);
        feedView.showShimmerAdapter();

        feedListFiltered.clear();
        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            feedView.hideShimmerAdapter();
            feedList.addAll(result);
            feedListFiltered.addAll(result);
            feedView.setAdapter(feedAdapter);
            feedAdapter.notifyDataSetChanged();
        });
    }

    private void initSearchView(MenuItem searchItem) {

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setIconified(true);
        }

        if (searchView == null) {
            return;
        }

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((AppCompatActivity) context).getComponentName()));
        }

        EditText queryEdit = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        queryEdit.setHintTextColor(Color.WHITE);
        queryEdit.setTextColor(Color.WHITE);
        queryEdit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                finalSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                feedListFiltered.clear();
                for (FeedItem item : feedList) {
                    if (item.getAll().toLowerCase().contains(newText.toLowerCase())) {
                        feedListFiltered.add(item);
                    }
                }
                feedAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}