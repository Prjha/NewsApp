package com.card.apiapplicaton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {

    Context context;
    ArrayList<Data> arrayList;

    public listAdapter(Context context, ArrayList<Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout, viewGroup, false);
        }
        TextView title,description;

        title = view.findViewById(R.id.titleTv);
        description = view.findViewById(R.id.desriptionTv);

        title.setText(arrayList.get(i).getTitle());
        description.setText(arrayList.get(i).getDescription());

        return view;
    }
}
