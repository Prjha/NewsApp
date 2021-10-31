package com.card.apiapplicaton;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class listAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Data> arrayList, temparray;
    customFilter cf;

    public listAdapter(Context context, ArrayList<Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.temparray = arrayList;
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
        TextView title,description,time,source_news;
        ImageView iV;

        title = view.findViewById(R.id.titleTv);
        description = view.findViewById(R.id.desriptionTv);
        iV = view.findViewById(R.id.iV);
        time = view.findViewById(R.id.time);
        source_news = view.findViewById(R.id.source_news);

        // set all data i.e, title, image, source_news_name etc.
        title.setText(arrayList.get(i).getTitle());
        description.setText(arrayList.get(i).getDescription());

        // when image not found or no image set default image.
        if(arrayList.get(i).getUrl().equals("null")){
            iV.setImageResource(R.drawable.ic_baseline_image_24);
            iV.setColorFilter(Color.argb(100, 25, 121, 225));
        }
        else {
            Picasso.get().load(arrayList.get(i).getUrl()).into(iV);
        }
        time.setText(arrayList.get(i).getPublishedAt());
        source_news.setText(arrayList.get(i).getSourceOfNews());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (cf==null){
            cf = new customFilter();
        }
        return cf;
    }

    class customFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                charSequence = charSequence.toString().toUpperCase();

            ArrayList<Data> list = new ArrayList<>();
            for (int i = 0; i < temparray.size(); i++) {

                if (temparray.get(i).getTitle().toUpperCase().contains(charSequence)) {
                    Data data = new Data(temparray.get(i).getTitle(), temparray.get(i).getDescription(), temparray.get(i).getUrl(), temparray.get(i).getPublishedAt(), temparray.get(i).getSourceOfNews());
                    list.add(data);
                }
            }
            filterResults.count = list.size();
            filterResults.values = list;

        }
            else {
                filterResults.count = temparray.size();
                filterResults.values = temparray;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            arrayList = (ArrayList<Data>)filterResults.values;
            notifyDataSetChanged();
        }
    }
}
