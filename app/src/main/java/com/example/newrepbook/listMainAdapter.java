package com.example.newrepbook;

import android.app.Activity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.api.Distribution;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class listMainAdapter extends RecyclerView.Adapter<listMainAdapter.GalleryViewHolder> {
    private ArrayList<Addpostinfo> mDataset;
    private Activity activity;

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        GalleryViewHolder(Activity activity, CardView v, Addpostinfo position) {
            super(v);
            cardView = v;

            LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = position.getContents();

            if(contentsLayout.getChildCount() == 0){
                for (int i = 0; i < contentsList.size() ; i++){
                    String contents = contentsList.get(i);
                    if(Patterns.WEB_URL.matcher(contents).matches()){
                        ImageView imageView = new ImageView(activity);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 리스트 글의 썸네일이 꽉차게 보임
                        contentsLayout.addView(imageView);
                        Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                    }else {
                        TextView textView = new TextView(activity);
                        textView.setLayoutParams(layoutParams);
                        textView.setText(contents);
                        contentsLayout.addView(textView);
                    }
                }
            }

        }
    }

    public listMainAdapter(Activity activity, ArrayList<Addpostinfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public listMainAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(activity, cardView, mDataset.get(viewType));
        cardView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titletextView = cardView.findViewById(R.id.titletextView);
        titletextView.setText(mDataset.get(position).getTitle());

        TextView createdAtTextView = cardView.findViewById(R.id.createdAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList = mDataset.get(position).getContents();

//        if(contentsLayout.getChildCount() == 0){
//            for (int i = 0; i < contentsList.size() ; i++){
//                String contents = contentsList.get(i);
//                if(Patterns.WEB_URL.matcher(contents).matches()){
//                    ImageView imageView = new ImageView(activity);
//                    imageView.setLayoutParams(layoutParams);
//                    imageView.setAdjustViewBounds(true);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 리스트 글의 썸네일이 꽉차게 보임
//                    contentsLayout.addView(imageView);
//                    Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
//                }else {
//                    TextView textView = new TextView(activity);
//                    textView.setLayoutParams(layoutParams);
//                    textView.setText(contents);
//                    contentsLayout.addView(textView);
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }
}