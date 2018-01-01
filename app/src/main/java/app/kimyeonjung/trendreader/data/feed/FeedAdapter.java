package app.kimyeonjung.trendreader.data.feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import app.kimyeonjung.trendreader.R;
import app.kimyeonjung.trendreader.core.Const;
import app.kimyeonjung.trendreader.data.FeedItem;
import app.kimyeonjung.trendreader.ui.feed.FeedDetailView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class FeedAdapter extends RealmRecyclerViewAdapter<FeedItem, FeedAdapter.ViewHolder> {
    private Context context;
    private boolean isPaletteUse;

    public FeedAdapter(Context context, boolean isPaletteUse, OrderedRealmCollection<FeedItem> data) {
        super(data, false);
        this.context = context;
        this.isPaletteUse = isPaletteUse;
        setHasStableIds(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.data = getItem(position);
        // Container
        holder.container.setOnClickListener(view -> {
            Intent intent = new Intent(context, FeedDetailView.class);
            intent.putExtra(Const.INTENT.POST_DATA, holder.data);
            context.startActivity(intent);
        });

        // Title
        holder.title.setText(holder.data.getPostTitle());
        holder.title.setSelected(true);

        // Content
        String content = holder.data.getPostContent();
        if (content.length() != 0) {
            if (holder.data.getPostContent().length() > 300) {
                content = content.substring(0, 300);
            }
            holder.description.setText(content);

        } else {
            holder.description.setVisibility(View.GONE);
        }

        // Favicon Image View
        Glide.with(context).load(holder.data.getFaviconUrl()).asBitmap()
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

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description;
        private ImageView profile;
        private LinearLayout descriptionContainer;
        private CardView container;

        private FeedItem data;

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