package app.kimyeonjung.trendreader.data.bookmark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.ui.feed.FeedDetailView;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    private List<FeedItem> feedList = new LinkedList<>();
    private Context context;


    public BookMarkAdapter(Context context, List<FeedItem> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @Override
    public BookMarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(BookMarkAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        FeedItem temp = feedList.get(position);
        holder.descriptionContainer.setVisibility(View.GONE);

        holder.title.setText(temp.getPostTitle());
        Glide.with(context).load(temp.getFaviconUrl()).centerCrop().into(holder.profile);

        holder.container.setOnClickListener(view -> {
            Intent intent = new Intent(context, FeedDetailView.class);
            intent.putExtra(Const.INTENT.POST_DATA, temp);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView profile;
        private LinearLayout descriptionContainer;
        private CardView container;

        ViewHolder(View view) {
            super(view);
            profile = view.findViewById(R.id.feed_profile_image);
            title = view.findViewById(R.id.feed_title);
            descriptionContainer = view.findViewById(R.id.feed_description_container);
            container = view.findViewById(R.id.feed_container);
        }
    }
}
