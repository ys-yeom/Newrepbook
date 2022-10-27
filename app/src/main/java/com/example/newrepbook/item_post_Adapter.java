package com.example.newrepbook;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class item_post_Adapter extends RecyclerView.Adapter<item_post_Adapter.GalleryViewHolder> {
    private ArrayList<String> mDataset;
    private Activity activity;

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public item_post_Adapter(Activity activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public item_post_Adapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("profilePath", mDataset.get(galleryViewHolder.getAdapterPosition()));
                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textView = cardView.findViewById(R.id.titletextView);
        textView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }
}