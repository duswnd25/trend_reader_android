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

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.data.feed.FeedAdapter;
import app.kimyeonjung.trendreader.data.feed.FeedManager;

public class FeedSearch extends Fragment {

    private static LinkedList<FeedItem> feedList = new LinkedList<>(), feedListFiltered = new LinkedList<>();
    private FeedAdapter feedAdapter;
    private ShimmerRecyclerView feedView;
    private SharedPreferences prefs;

    public FeedSearch() {
        setHasOptionsMenu(true);
    }

   @Override
   public void onResume(){
        super.onResume();
        initData();
   }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isPaletteUse = prefs.getBoolean(getString(R.string.pref_feed_palette_use), true);
        int staggerColSize = prefs.getInt(getString(R.string.pref_feed_col_num), 1);
        // 데이터 어댑터
        feedAdapter = new FeedAdapter(getContext(), isPaletteUse, feedListFiltered);
        // 리스트뷰
        feedView = view.findViewById(R.id.fragment_recycler_view);
        // 스크롤 버튼
        FloatingActionButton upToTopButton = view.findViewById(R.id.fragment_recycler_up_to_top);
        upToTopButton.setOnClickListener(view1 -> feedView.smoothScrollToPosition(0));
        // 레이아웃 매니저
        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(staggerColSize, StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        // 리스트뷰 속성
        feedView.setGridChildCount(staggerColSize);
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

        initData();
    }

    private void initData() {
        feedView.showShimmerAdapter();

        feedListFiltered.clear();
        feedList.clear();

        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            feedList.addAll(result);
            feedListFiltered.addAll(result);

            feedView.setGridChildCount(prefs.getInt(getString(R.string.pref_feed_col_num), 1));
            feedView.hideShimmerAdapter();
            Log.d("테스트", String.valueOf(feedListFiltered.size()));
            feedAdapter.notifyDataSetChanged();
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