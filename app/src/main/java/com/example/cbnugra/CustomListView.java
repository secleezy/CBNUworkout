package com.example.cbnugra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView  extends BaseAdapter {
    LayoutInflater layoutInflater = null;
    private ArrayList<ListData> listViewData = null;
    private int count = 0;

    public CustomListView(ArrayList<ListData> listData)
    {
        listViewData = listData;
        count = listViewData.size();
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            final Context context = viewGroup.getContext();
            if (layoutInflater == null)
            {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            view = layoutInflater.inflate(R.layout.custom_listview, viewGroup, false);
        }

        TextView title = view.findViewById(R.id.title);
        TextView body_1 = view.findViewById(R.id.body_1);
        TextView body_2 = view.findViewById(R.id.body_2);

        title.setText(listViewData.get(i).title);
        body_1.setText(listViewData.get(i).body_1);
        body_2.setText(listViewData.get(i).body_2);
        return view;

    }
}

