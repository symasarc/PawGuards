package com.example.pawguards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CustomListItem> dataList;

    public CustomListAdapter(Context context, ArrayList<CustomListItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_item, null);
        }

        ImageView itemImageView = view.findViewById(R.id.itemImageView);
        TextView itemTextView = view.findViewById(R.id.itemTitleTextView);

        CustomListItem currentItem = dataList.get(position);

        // Set image and text for each item
        itemImageView.setImageResource(currentItem.getImageResource());
        itemTextView.setText(currentItem.getText());

        return view;
    }
}

