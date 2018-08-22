package com.rizaldi.qasirtest;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;                  //to fetch data from server to recyclerview
    private List<MyData> data_list;                 //data to receive
    private String bannerLink;
    private ImageView bannerImage;
    private CustomAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bannerImage = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data_list = new ArrayList<>();

        loadDataFromServer(0);

        adapter = new CustomAdapter(this, data_list, listener);                //to bind data from server
        recyclerView.setAdapter(adapter);
    }

    private void loadDataFromServer(int id) {
        //when the content needs updating
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                HttpHandler sh = new HttpHandler();
                String url = "https://api.myjson.com/bins/gsq5w";
                String jsonStr = sh.makeServiceCall(url);
                //OkHttpClient client = new OkHttpClient();
                //Request request = new Request.Builder().url("").build(); //url PHP
                Log.e(TAG, "Response from URL: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        //Response response = client.newCall(request).execute();
                        //JSONObject object = new JSONObject(response.body().string());

                        JSONObject object = new JSONObject(jsonStr);
                        //getting json array node
                        JSONObject dataObj = object.getJSONObject("data");
                        JSONArray array = dataObj.getJSONArray("products");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);

                            JSONObject product_img = product.getJSONObject("images");

                            MyData data = new MyData(product.getInt("product_id"),
                                    product.getInt("price"),
                                    product.getInt("stock"),
                                    product.getString("product_name"),
                                    product.getString("description"),
                                    product_img.getString("thumbnail"),
                                    product_img.getString("large"));

                            data_list.add(data);
                        }

                        JSONObject bannerImg = dataObj.getJSONObject("banner");
                        bannerLink = bannerImg.getString("image");

                    } catch (final JSONException e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "JSON parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //tell adapter there's a change in database
                adapter.notifyDataSetChanged();
                //show banner image
                Glide.with(MainActivity.this).load(bannerLink).into(bannerImage);
            }
        };

        task.execute(id);
    }
}
