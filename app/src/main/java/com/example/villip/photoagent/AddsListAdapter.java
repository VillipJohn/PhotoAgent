package com.example.villip.photoagent;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddsListAdapter extends ArrayAdapter<Ad> {
    private final Context context;
    private final ArrayList<Ad> values;

    public AddsListAdapter(Context context, ArrayList<Ad> values) {
        super(context, R.layout.item_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.hw_item_list, parent, false);

        TextView place = (TextView) rowView.findViewById(R.id.place);
        TextView dateTime = (TextView) rowView.findViewById(R.id.date_time);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        String placeStr = values.get(position).getPlace();
        String dateStr = values.get(position).getDate();
        String timeStr = values.get(position).getTime();
        Bitmap image = values.get(position).getImage();

        place.setText(placeStr);
        dateTime.setText(dateStr + " " + timeStr);
        icon.setImageBitmap(image);
        return rowView;
    }
}
