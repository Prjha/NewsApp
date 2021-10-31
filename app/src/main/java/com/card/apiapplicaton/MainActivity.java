package com.card.apiapplicaton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Data> arrayList;
    private String myResponse;
    private EditText search_item;
    private listAdapter adapter;
    private TextView total_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.ListView);
        search_item = findViewById(R.id.search_item);
        total_post = findViewById(R.id.total_post);

        arrayList = new ArrayList<>();

        // fetch all data from api in json format
        fetchAllNews();

        // search news
        searchNews();

    }

    private void fetchAllNews() {

        OkHttpClient client = new OkHttpClient();
        String url = "https://newsapi.org/v2/everything?q=tesla&from=2021-09-30&sortBy=publishedAt&language=en&apiKey=9f88ea2b86f7436ab3fe6aae959d1b45";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(myResponse);
                                JSONArray array = jsonObject.getJSONArray("articles");
                                for (int i = 0; i<array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    String title = object.getString("title");
                                    String des = object.getString("description");
                                    String image = object.getString("urlToImage");
                                    String time = object.getString("publishedAt");

                                    // this function is used for convert zoned time into hours and minute i.e, 2 hours ago or 30 min ago.
                                    String output_time = TimeConvert(time);

                                    JSONObject source_obj = object.getJSONObject("source");

                                    String source_name = source_obj.getString("name");

                                    // add all data of news i.e, title, description etc in arraylist
                                    Data data = new Data();
                                    data.setDescription(des);
                                    data.setTitle(title);
                                    data.setUrl(image);
                                    data.setPublishedAt(output_time);
                                    data.setSourceOfNews(source_name);
                                    arrayList.add(data);

                                    // initialize adapter
                                    adapter = new listAdapter(MainActivity.this, arrayList);

                                    // set adapter on listview
                                    listView.setAdapter(adapter);

                                }

                                //set total post
                                total_post.setText(arrayList.size()+"");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        });
    }

    private void searchNews() {
        search_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.adapter.getFilter().filter(charSequence);

                // set total post count when filter
                MainActivity.this.adapter.getFilter().filter(charSequence, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        total_post.setText(i+"");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private String TimeConvert(String time) {
        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH");
            SimpleDateFormat o_m = new SimpleDateFormat("mm");
            Date parsedDate = inputFormat.parse(time);
            Date m = inputFormat.parse(time);
            int min = Integer.parseInt(o_m.format(m));
            SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
            int current_time = Integer.parseInt(sdf.format(new Date()));
            int past_time = Integer.parseInt(outputFormat.format(parsedDate));
            int time_diff = current_time-past_time;
            if (time_diff<1){
                SimpleDateFormat sdf1 = new SimpleDateFormat("mm");
                Date minutes = inputFormat.parse(time);
                int c_minute = Integer.parseInt(sdf1.format(minutes));
                int o_minutes = c_minute-min;
                formattedDate = o_minutes+" minutes ago";
            }
            else {
                formattedDate = time_diff+" hours ago";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;

    }
}