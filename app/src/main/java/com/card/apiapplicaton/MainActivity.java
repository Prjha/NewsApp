package com.card.apiapplicaton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Data> arrayList;
    private String myResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.ListView);

        arrayList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://newsapi.org/v2/everything?q=tesla&from=2021-09-29&sortBy=publishedAt&apiKey=9f88ea2b86f7436ab3fe6aae959d1b45";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

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

                                    Data data = new Data();
                                    data.setDescription(des);
                                    data.setTitle(title);
                                    arrayList.add(data);

                                    listAdapter adapter = new listAdapter(MainActivity.this, arrayList);
                                    listView.setAdapter(adapter);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        });

    }
}