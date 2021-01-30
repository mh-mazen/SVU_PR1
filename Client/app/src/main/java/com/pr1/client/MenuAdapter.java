package com.pr1.client;

import android.content.Context;
import android.service.quicksettings.Tile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private RowItem[]  Title;

    public MenuAdapter(Context context, RowItem[] text1) {
        mContext = context;
        Title = text1;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.length;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row;
        row = inflater.inflate(R.layout.row, parent, false);
        TextView title;
        TextView desc;
        ImageView i1;
        i1 = (ImageView) row.findViewById(R.id.imgIcon);
        title = (TextView) row.findViewById(R.id.txtTitle);
        desc = (TextView) row.findViewById(R.id.txtDesc);
        title.setText(Title[position].title);
        desc.setText(Title[position].description);
        i1.setImageResource(R.drawable.ic_powerpoint);

        return (row);
    }
}