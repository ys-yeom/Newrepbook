package com.example.newrepbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class listMainDTO {
    private Context mContext;
    private ArrayList<String> mItmes;

    public void ListItemsAdapter(Context mContext, ArrayList<String> mItmes) {
        this.mContext = mContext;
        this.mItmes = mItmes;
    }

    public int getCount() {
        return mItmes.size();
    }

    public Object getItem(int position) {
        return mItmes.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
        }

        TextView txt_number = (TextView) convertView.findViewById(R.id.txt_number);
        TextView txt_item = (TextView) convertView.findViewById(R.id.txt_item);

        txt_number.setText("" + position);
        txt_item.setText(mItmes.get(position));

        return convertView;
    }
}