package app.kimyeonjung.trendreader.data.about;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;


public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private LinkedList<String> itemList = new LinkedList<>();

    public LibraryAdapter(LinkedList<String> itemList) {
        this.itemList = itemList;
    }

    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LibraryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textView.setText(itemList.get(position));
        holder.textView.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(android.R.id.text1);
        }
    }
}
