package com.mani.cs5540.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.mani.cs5540.newsapp.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by maeswara on 6/17/2017.
 */
public class NewsAsyncTask extends AsyncTask<String, Void, NewsItem[]> {
    private static final String TAG = NewsAsyncTask.class.getName();
    private MainActivity mainActivity;

    public NewsAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected NewsItem[] doInBackground(String... params) {
        if (params != null && params.length > 0) {
            URL newsURL = NetworkUtils.buildUrl(params[0]);
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(newsURL);
                Log.d(TAG, "JSON Response: " + jsonResponse);
                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    return getNewsItems(jsonResponse);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Exception reading news_layout at URL: " + newsURL.toString()
                        + ". Cause: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
        return null;
    }

    public NewsItem[] getNewsItems(String jsonResponse) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray newsItemJSONArray = jsonObject.getJSONArray("articles");
        NewsItem[] newsItems = null;
        if (newsItemJSONArray != null) {
            newsItems = new NewsItem[newsItemJSONArray.length()];
            for (int i = 0; i < newsItemJSONArray.length(); i++) {
                JSONObject newsItemJSONObject = newsItemJSONArray.getJSONObject(i);
                newsItems[i] = new NewsItem();
                newsItems[i].setAuthor(newsItemJSONObject.getString("author"));
                newsItems[i].setTitle(newsItemJSONObject.getString("title"));
                newsItems[i].setDescription(newsItemJSONObject.getString("description"));
                newsItems[i].setUrl(newsItemJSONObject.getString("url"));
                newsItems[i].setUrlToImage(newsItemJSONObject.getString("urlToImage"));
                newsItems[i].setPublishedAt(newsItemJSONObject.getString("publishedAt"));
                newsItems[i].setImageBitMap(getImageBitMap(newsItemJSONObject.getString("urlToImage")));
            }
        }
        return newsItems;
    }

    public Bitmap getImageBitMap(String urlString) throws IOException {
        URL url = new URL(Uri.parse(urlString).toString());
        // Source: www.stackoverflow.com
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //BitmapFactory.decodeStream(url.openConnection().getInputStream(),
        //        null, options);
        //options.inJustDecodeBounds = false;
        //options.inSampleSize = (options.outWidth * options.outHeight) / 10;
        //return BitmapFactory.decodeStream((InputStream) url.getContent(),
        //        null, options);
        return BitmapFactory.decodeStream((InputStream) url.getContent());
    }

    @Override
    protected void onPostExecute(NewsItem[] newsItems) {
        mainActivity.displayNews(newsItems);
    }
}
