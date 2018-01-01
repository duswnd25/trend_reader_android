package app.kimyeonjung.trendreader.ui.feed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.LinkedList;
import java.util.List;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.data.bookmark.BookMarkAdapter;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FeedBookMark extends Fragment {

    private List<FeedItem> bookMarkList = new LinkedList<>();
    private BookMarkAdapter feedAdapter;
    private ShimmerRecyclerView feedView;

    public FeedBookMark() {
        setHasOptionsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int staggerColSize = prefs.getInt(getString(R.string.pref_feed_col_num), 1);

        // StaggerGridLayout
        StaggeredGridLayoutManager feedLayoutManager = new StaggeredGridLayoutManager(staggerColSize, StaggeredGridLayoutManager.VERTICAL);
        feedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        feedAdapter = new BookMarkAdapter(getContext(), bookMarkList);

        // RecyclerView
        feedView = view.findViewById(R.id.fragment_recycler2_view);
        feedView.setNestedScrollingEnabled(true);
        feedView.setHasFixedSize(true);
        feedView.setLayoutManager(feedLayoutManager);
        feedView.setAdapter(feedAdapter);
        feedView.showShimmerAdapter();
        try (Realm realm = Realm.getInstance(Const.DB.getBookMarkConfig())) {
            RealmQuery<FeedItem> query = realm.where(FeedItem.class);
            RealmResults<FeedItem> temp = query.findAll();
            bookMarkList = realm.copyFromRealm(temp);
            feedView.hideShimmerAdapter();
            feedAdapter.notifyDataSetChanged();
        }
        initData();
    }

    private void initData() {

    }
}