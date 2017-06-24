package com.mani.cs5540.newsapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.mani.cs5540.newsapp.util.NewsClickEvent;
import com.mani.cs5540.newsapp.util.NewsClickListener;
import com.mani.cs5540.newsapp.util.NewsViewAdapter;

public class MainActivity extends AppCompatActivity implements NewsClickListener {
    private static boolean onLoad = true;
    private static final String TAG = MainActivity.class.getName();
    private RecyclerView newsRecyclerView;
    private NewsViewAdapter newsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsViewAdapter = new NewsViewAdapter(this);

        newsRecyclerView = (RecyclerView) findViewById(R.id.newsItemRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        newsRecyclerView.setHasFixedSize(false);
        newsRecyclerView.setAdapter(newsViewAdapter);

        handleIntent(getIntent());

        if(onLoad == true) {
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            new NewsAsyncTask(this).execute("the-next-web");
            onLoad = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (query != null && !query.isEmpty()) {
                Log.d(TAG, "Calling NewsAsyncTask with query " + query);

                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                new NewsAsyncTask(this).execute(query);
            } else {
                Toast.makeText(getApplicationContext(), "Enter a search text!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchMenuItem).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void onNewsClick(NewsClickEvent event) {
        Uri uri = Uri.parse(event.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void displayNews(NewsItem[] newsItems) {
        if (newsItems != null && newsItems.length > 0) {
            newsViewAdapter.setNewsItems(newsItems);
        } else {
            Toast.makeText(getApplicationContext(), "No search results found!", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }
}
