package app.kimyeonjung.trendreader.data.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import de.hdodenhof.circleimageview.CircleImageView;
import me.grantland.widget.AutofitTextView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private LinkedList<FeedItem> feedList = new LinkedList<>();
    private Context context;

    public FeedAdapter(Context context, LinkedList<FeedItem> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        FeedItem temp = feedList.get(position);
        holder.title.setText(temp.getPostTitle());
        holder.description.setText(temp.getPostContent());
        Glide.with(context).load(temp.getFaviconUrl()).centerCrop().placeholder(R.mipmap.ic_launcher_round).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private AutofitTextView title, description;
        private CircleImageView profile;

        ViewHolder(View view) {
            super(view);
            profile = view.findViewById(R.id.feed_profile_image);
            title = view.findViewById(R.id.feed_title);
            description = view.findViewById(R.id.feed_description);
        }
    }
}
