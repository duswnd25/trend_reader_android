package app.kimyeonjung.trendreader.data.bookmark;

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
import app.kimyeonjung.trendreader.data.feed.FeedItem;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    private LinkedList<FeedItem> feedList = new LinkedList<>();
    private Context context;
    private boolean isPaletteUse = true;


    public BookMarkAdapter(Context context, Boolean isPaletteUse, LinkedList<FeedItem> feedList) {
        this.context = context;
        this.feedList = feedList;
        this.isPaletteUse = isPaletteUse;
    }

    @Override
    public BookMarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(BookMarkAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
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
        private TextView title;
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
