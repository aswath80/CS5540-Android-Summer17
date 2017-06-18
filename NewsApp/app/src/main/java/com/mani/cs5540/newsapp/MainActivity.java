package com.mani.cs5540.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mani.cs5540.newsapp.util.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = (EditText) findViewById(R.id.searchText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.searchMenuItem) {
            String source = searchText.getText().toString();
            if(source != null && !source.isEmpty()) {
                Log.d(TAG, "Calling NewsAsyncTask with source " + source);

                LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsLayout);
                newsLayout.removeAllViews();

                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                new NewsAsyncTask(this).execute(source);
            } else {
                Toast.makeText(getApplicationContext(), "Enter a search text!", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onContextItemSelected(item);
    }

    public void displayNews(NewsResponse newsResponse) {

        if(newsResponse != null && newsResponse.getArticles() != null) {
            // Source: www.stackoverflow.com
            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (NewsArticle newsArticle : newsResponse.getArticles()) {
                LinearLayout newsLayout = (LinearLayout) findViewById(R.id.newsLayout);
                View newsGridLayout = inflater.inflate(R.layout.news, newsLayout, false);

                ImageView imageView = (ImageView) newsGridLayout.findViewById(R.id.urlImage);
                imageView.setImageBitmap(newsArticle.getImageBitMap());

                TextView authorView = (TextView) newsGridLayout.findViewById(R.id.author);
                authorView.setText("Author: " + newsArticle.getAuthor());
                TextView titleView = (TextView) newsGridLayout.findViewById(R.id.title);
                titleView.setText("Title: " + newsArticle.getTitle());
                TextView descriptionView = (TextView) newsGridLayout.findViewById(R.id.description);
                descriptionView.setText("Description: " + newsArticle.getDescription());
                TextView urlView = (TextView) newsGridLayout.findViewById(R.id.url);
                urlView.setText("URL: " + newsArticle.getUrl());
                TextView urlToImageView = (TextView) newsGridLayout.findViewById(R.id.urlToImage);
                urlToImageView.setText("URLToImage: " + newsArticle.getUrlToImage());
                TextView publishedAtView = (TextView) newsGridLayout.findViewById(R.id.publishedAt);
                publishedAtView.setText("PublishedAt: " + newsArticle.getPublishedAt());

                //if(newsGridLayout.getParent() != null) {
                //    ((ViewGroup)newsGridLayout.getParent()).removeView(newsGridLayout);
                //}

                newsLayout.addView(newsGridLayout);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No search results found!", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }
}
