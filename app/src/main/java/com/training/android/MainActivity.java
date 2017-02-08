package com.training.android;

import android.app.ProgressDialog;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.training.android.Data.NewsFeed;
import com.training.android.Handler.HttpHandler;
import com.training.android.newsfeed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://newsapi.org/v1/articles?source=ign&apiKey=6e697785363b43218fcdf7d0c76f8245";
    ArrayList<NewsFeed> data;
    ListView mlvList;
    ProgressDialog pd;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new GetList().execute();
    }

    private class GetList extends AsyncTask<String, Void, List<NewsFeed>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        //doInBackground method
        @Override
        protected List<NewsFeed> doInBackground(String... params) {

            //Initialization of JSON variables
            data = new ArrayList<>();
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);

            //Fetches data from JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray feeds = jsonObj.getJSONArray("articles");

                    for (int i = 0; i < feeds.length(); i++) {
                        //Creating JSON object
                        JSONObject li = feeds.getJSONObject(i);

                        //Initializing Data object
//                        String author = li.getString("author");
//                        String title = li.getString("title");
//                        String description = li.getString("description");
//                        String url = li.getString("url");
//                        String urlToImage = li.getString("urlToImage");
//                        String publishedAt = li.getString("publishedAt");

                        NewsFeed current = new NewsFeed(li.getString("author")
                                , li.getString("description")
                                , li.getString("publishedAt")
                                , li.getString("title")
                                , li.getString("url")
                                , li.getString("urlToImage")
                        );

                        //Adds all Complete data objects into data ArrayList
//                        current.author = author;
//                        current.title = title;
//                        current.description = description;
//                        current.url = url;
//                        current.urlToImage = urlToImage;
//                        current.publishedAt = publishedAt;

                        data.add(current);
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //OnPostExecute method
        @Override
        protected void onPostExecute(List<NewsFeed> result) {
            super.onPostExecute(result);
            if (pd.isShowing())
                pd.dismiss();

            if (data.isEmpty()) {
                Toast.makeText(MainActivity.this, "No Work Order available", Toast.LENGTH_SHORT).show();
            } else {

                mlvList = (ListView) findViewById(R.id.lvList);
                adapter = new Adapter(MainActivity.this, data);
                mlvList.setAdapter(adapter);
            }


        }
    }
}
