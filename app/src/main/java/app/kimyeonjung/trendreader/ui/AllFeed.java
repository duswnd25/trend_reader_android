package app.kimyeonjung.trendreader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.info.FeedAdapter;
import app.kimyeonjung.trendreader.data.info.FeedItem;
import app.kimyeonjung.trendreader.data.info.FeedManager;

public class AllFeed extends Fragment {

    public AllFeed() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinkedList<FeedItem> feedList = new LinkedList<>();
        final FeedAdapter feedAdapter = new FeedAdapter(getContext(), feedList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        RecyclerView feedView = view.findViewById(R.id.fragment_recycler_view);
        feedView.setHasFixedSize(false);
        feedView.setLayoutManager(mLayoutManager);
        feedView.setAdapter(feedAdapter);

        new FeedManager().fetchFeed(Const.API_URL.ALL, result -> {
            feedList.addAll(result);
            feedAdapter.notifyDataSetChanged();
        });
    }
}