package com.mani.cs5540.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.mani.cs5540.newsapp.util.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maeswara on 6/17/2017.
 */
public class NewsAsyncTask extends AsyncTask<String, Void, NewsResponse> {
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
    protected NewsResponse doInBackground(String... params) {
        if (params != null && params.length > 0) {
            URL newsURL = NetworkUtils.buildUrl(params[0]);
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(newsURL);
                Log.d(TAG, "JSON Response: " + jsonResponse);
                if(jsonResponse != null && !jsonResponse.isEmpty()) {
                    JsonReader reader = new JsonReader(new StringReader(jsonResponse));
                    if(reader.hasNext()) {
                        return readNewsResponse(reader);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Exception reading news at URL: " + newsURL.toString()
                        + ". Cause: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
        return null;
    }

    public NewsResponse readNewsResponse(JsonReader reader) throws IOException {
        NewsResponse newsResponse = new NewsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            Log.d(TAG, "Creating new NewsResponse");
            String name = reader.nextName();
            if (name.equals(NewsResponse.STATUS_NAME)) {
                newsResponse.setStatus(reader.nextString());
            } else if (name.equals(NewsResponse.SOURCE_NAME)) {
                newsResponse.setSource(reader.nextString());
            } else if (name.equals(NewsResponse.SORT_BY_NAME)) {
                newsResponse.setSortBy(reader.nextString());
            } else if (name.equals(NewsResponse.ARTICLES_NAME)) {
                List<NewsArticle> list = new ArrayList<>();
                reader.beginArray();
                while (reader.hasNext()) {
                    list.add(readNewsArticle(reader));
                }
                reader.endArray();
                newsResponse.setArticles(list.toArray(new NewsArticle[list.size()]));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return newsResponse;
    }

    public NewsArticle readNewsArticle(JsonReader reader) throws IOException {
        NewsArticle newsArticle = new NewsArticle();
        reader.beginObject();
        while (reader.hasNext()) {
            Log.d(TAG, "Creating new NewsArticle");
            String name = reader.nextName();
            if (name.equals(NewsArticle.AUTHOR_NAME)) {
                newsArticle.setAuthor(reader.nextString());
            } else if (name.equals(NewsArticle.DESCRIPTION_NAME)) {
                newsArticle.setDescription(reader.nextString());
            } else if (name.equals(NewsArticle.PUBLISHED_AT_NAME)) {
                newsArticle.setPublishedAt(reader.nextString());
            } else if (name.equals(NewsArticle.TITLE_NAME)) {
                newsArticle.setTitle(reader.nextString());
            } else if (name.equals(NewsArticle.URL_NAME)) {
                newsArticle.setUrl(reader.nextString());
            } else if (name.equals(NewsArticle.URL_TO_IMAGE_NAME)) {
                newsArticle.setUrlToImage(reader.nextString());
                newsArticle.setImageBitMap(getImageBitMap(newsArticle.getUrlToImage()));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return newsArticle;
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
    protected void onPostExecute(NewsResponse newsResponse) {
        mainActivity.displayNews(newsResponse);
    }
}
