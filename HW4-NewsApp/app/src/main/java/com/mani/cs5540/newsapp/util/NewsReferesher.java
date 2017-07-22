package com.mani.cs5540.newsapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mani.cs5540.newsapp.NewsItem;
import com.mani.cs5540.newsapp.database.NewsDatabaseHelper;
import com.mani.cs5540.newsapp.database.NewsDatabaseUtil;

import java.net.URL;

/**
 * Created by maeswara on 7/22/2017.
 */
public class NewsReferesher {
   //[Mani]TODO: Refereshes the database with new set of news articles from the
   // API
   private static final String TAG = "NewsReferesher";

   public static void updateDatabaseFromServer(Context context) {
      try {
         URL newsURL = NetworkUtils.buildUrl();

         String jsonResponse = NetworkUtils.getResponseFromHttpUrl(newsURL);
         //Log.d(TAG, "JSON Response: " + jsonResponse);
         if (jsonResponse != null && !jsonResponse.isEmpty()) {
            NewsItem[] newsItems = NewsJSONUtils.getNewsItems(jsonResponse);
            SQLiteDatabase db = new NewsDatabaseHelper(context)
                  .getWritableDatabase();
            NewsDatabaseUtil.updateNews(db, newsItems);
         }
      } catch (Exception e) {
         Log.e(TAG, "Error reading new API. Cause: " + e.getClass().getName() +
               ": " + e.getMessage(), e);
         Toast.makeText(context,
               "Error reading new API. Cause: " + e.getClass().getName() +
                     ": " + e.getMessage(), Toast.LENGTH_LONG).show();
      }
   }
}
