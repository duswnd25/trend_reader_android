package app.kimyeonjung.trendreader.data.feed.fetch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.LinkedList;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.feed.FeedItem;
import app.kimyeonjung.trendreader.ui.feed.DetailView;
import me.grantland.widget.AutofitTextView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private LinkedList<FeedItem> feedList = new LinkedList<>();
    private Context context;
    private boolean isPaletteUse = true;


    public FeedAdapter(Context context, Boolean isPaletteUse, LinkedList<FeedItem> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.isPaletteUse = isPaletteUse;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        FeedItem temp = feedList.get(position);
        holder.title.setText(temp.getPostTitle());

        holder.container.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailView.class);
            intent.putExtra(Const.INTENT.BLOG_NAME, temp.getBlogName());
            intent.putExtra(Const.INTENT.FAVICON_URL, temp.getFaviconUrl());
            intent.putExtra(Const.INTENT.POST_TITLE, temp.getPostTitle());
            intent.putExtra(Const.INTENT.POST_CONTENT, temp.getPostContent());
            intent.putExtra(Const.INTENT.POST_URL, temp.getPostUrl());
            context.startActivity(intent);
        });


        String content = temp.getPostContent();
        if (content.length() != 0) {
            if (temp.getPostContent().length() > 300) {
                content = content.substring(0, 300);
            }
            holder.description.setText(content);

        } else {
            holder.description.setVisibility(View.GONE);
        }

        Glide.with(context).load(temp.getFaviconUrl()).asBitmap()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(holder.profile) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);
                        if (isPaletteUse) {
                            Palette palette = Palette.from(bitmap).generate();
                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                            if (vibrantSwatch != null) {
                                holder.descriptionContainer.setBackgroundColor(vibrantSwatch.getRgb());
                                holder.description.setTextColor(vibrantSwatch.getBodyTextColor());
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description;
        private ImageView profile;
        private LinearLayout descriptionContainer;
        private CardView container;

        ViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.feed_container);
            profile = view.findViewById(R.id.feed_profile_image);
            title = view.findViewById(R.id.feed_title);
            description = view.findViewById(R.id.feed_description);
            descriptionContainer = view.findViewById(R.id.feed_description_container);
        }
    }
}
