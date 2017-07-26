package com.mani.cs5540.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mani.cs5540.newsapp.adapter.NewsViewAdapter;
import com.mani.cs5540.newsapp.database.NewsCursor;
import com.mani.cs5540.newsapp.database.NewsDatabaseHelper;
import com.mani.cs5540.newsapp.database.NewsDatabaseUtil;
import com.mani.cs5540.newsapp.loader.NewsAsyncLoader;
import com.mani.cs5540.newsapp.schedulerjob.NewsLoaderJobScheduler;
import com.mani.cs5540.newsapp.util.NewsClickEvent;
import com.mani.cs5540.newsapp.util.NewsClickListener;

public class MainActivity extends AppCompatActivity
      implements NewsClickListener, LoaderManager.LoaderCallbacks<Void> {
   private static final String TAG = "MainActivity";
   private static boolean onLoad = true;
   private RecyclerView newsRecyclerView;
   private NewsViewAdapter newsViewAdapter;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      SQLiteDatabase db = new NewsDatabaseHelper(this).getReadableDatabase();
      NewsCursor newsCursor = NewsDatabaseUtil.getAllNewsCursor(db);

      newsViewAdapter = new NewsViewAdapter(newsCursor, this);

      newsRecyclerView = (RecyclerView) findViewById(R.id.newsItemRecyclerView);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
      newsRecyclerView.setLayoutManager(linearLayoutManager);
      newsRecyclerView.setHasFixedSize(false);
      newsRecyclerView.setAdapter(newsViewAdapter);
      getLoaderManager().initLoader(0, null, this);

      checkIfFirstRunAndDoFirstLoad();

      //TODO:[ANSWER(7)]: Display what's in the database during app onCreate()
      displayNews();
   }

   private void checkIfFirstRunAndDoFirstLoad() {
      //TODO:[ANSWER(6)]: Perform first load when the app is installed first
      // time
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
            this);
      boolean firstRun = prefs.getBoolean("firstRun", true);
      if (firstRun) {
         reLoad();
         SharedPreferences.Editor editor = prefs.edit();
         editor.putBoolean("firstRun", false);
         editor.commit();
      }
   }

   private void reLoad() {
      getLoaderManager().restartLoader(0, null, this).forceLoad();
   }

   @Override protected void onStart() {
      super.onStart();
      //[Mani]TODO: Start the background news load job when app starts
      NewsLoaderJobScheduler.scheduleNewsLoad(this);
   }

   @Override public Loader<Void> onCreateLoader(int id, Bundle args) {
      return new NewsAsyncLoader(this);
   }

   @Override public void onLoadFinished(Loader<Void> loader, Void data) {
      Log.d(TAG, "$$$ onLoadFinished");
      displayNews();
   }

   private void displayNews() {
      SQLiteDatabase db = new NewsDatabaseHelper(this).getReadableDatabase();
      NewsCursor newsCursor = NewsDatabaseUtil.getAllNewsCursor(db);
      newsViewAdapter.swapCursor(newsCursor);
      newsRecyclerView.setAdapter(newsViewAdapter);
      newsViewAdapter.notifyDataSetChanged();
   }

   @Override public void onLoaderReset(Loader<Void> loader) {

   }

   @Override public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.options_menu, menu);
      return true;
   }

   @Override public boolean onOptionsItemSelected(MenuItem item) {
      Log.d(TAG, "$$$ MenuItem " + item.getTitle() + "pressed!");
      if (item.getItemId() == R.id.refreshMenuItem) {
         Log.d(TAG, "$$$ Refreshing news!");
         //[Mani]TODO: May restart loader here but since background job is
         // updating database every minute, we would just load from database.
         reLoad();
      }
      return true;
   }

   @Override public void onNewsClick(NewsClickEvent event) {
      Uri uri = Uri.parse(event.getUrl());
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      if (intent.resolveActivity(getPackageManager()) != null) {
         startActivity(intent);
      }
   }

   @Override protected void onStop() {
      super.onStop();
      //[Mani]TODO: Stop background scheduled job when app is stopped.
      NewsLoaderJobScheduler.stopScheduledNewsLoad(this);
   }
}
