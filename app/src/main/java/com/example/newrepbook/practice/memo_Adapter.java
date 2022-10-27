package com.example.newrepbook.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newrepbook.R;

import java.util.ArrayList;

public class memo_Adapter extends RecyclerView.Adapter<memo_Adapter.CustomViewHolder> {
    private ArrayList<memo_list> arrayList;
    private Context context;
    TextView itemView;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    OnItemClickListener mListener;

    public void OnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public memo_Adapter(ArrayList<memo_list> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practicelistviewactivity, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) { // 아이템 매칭
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.tv_profile);
        holder.tv_text1.setText(arrayList.get(position).getName());
        holder.tv_text2.setText(arrayList.get(position).getComment());
        holder.tv_text3.setText(arrayList.get(position).getStaffkey());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView tv_profile;
        TextView tv_text1;
        TextView tv_text2;
        TextView tv_text3;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_profile = itemView.findViewById(R.id.tv_profile);
            this.tv_text1 = itemView.findViewById(R.id.tv_text1);
            this.tv_text2 = itemView.findViewById(R.id.tv_text2);
            this.tv_text3 = itemView.findViewById(R.id.tv_text3);


        }
    }
}