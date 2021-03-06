package com.pr1.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<RowItem> Title;
    private int Icon;

    public MenuAdapter(Context context, List<RowItem> text1, int icon) {
        mContext = context;
        Title = text1;
        Icon = icon;
    }

    public void setItems(List<RowItem> items) {
        this.Title = items;
    }

    public boolean isEmpty() {
        if (this.Title != null)
            return this.Title.isEmpty();
        return true;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return Title.get(arg0);
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
        title.setText(Title.get(position).title);
        desc.setText(Title.get(position).description);
        i1.setImageResource(Icon);

        return (row);
    }
}