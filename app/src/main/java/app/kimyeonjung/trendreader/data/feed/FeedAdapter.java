package app.kimyeonjung.trendreader.data.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
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

        if (temp.getPostContent().length() != 0) {
            holder.description.setText(temp.getPostContent());
        } else {
            holder.description.setVisibility(View.GONE);
        }

        Glide.with(context).load(temp.getFaviconUrl()).asBitmap()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(holder.profile) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);
                        if (isPaletteUse) {
                            Palette palette = Palette.from(bitmap).generate();
                            Palette.Swatch vibrantSwatch = palette.getLightVibrantSwatch();
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
        private AutofitTextView title;
        private TextView description;
        private ImageView profile;
        private LinearLayout descriptionContainer;

        ViewHolder(View view) {
            super(view);
            profile = view.findViewById(R.id.feed_profile_image);
            title = view.findViewById(R.id.feed_title);
            description = view.findViewById(R.id.feed_description);
            descriptionContainer = view.findViewById(R.id.feed_description_container);
        }
    }
}
