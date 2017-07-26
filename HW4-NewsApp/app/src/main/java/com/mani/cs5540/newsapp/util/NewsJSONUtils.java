package com.mani.cs5540.newsapp.util;

import android.util.Log;

import com.mani.cs5540.newsapp.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maeswara on 7/22/2017.
 */
public class NewsJSONUtils {
   private static final String TAG = "NewsJSONUtils";

   //[Mani] TODO: Converts JSON response from news API to POJOs
   public static NewsItem[] getNewsItems(String jsonResponse) {
      try {
         JSONObject jsonObject = new JSONObject(jsonResponse);
         JSONArray newsItemJSONArray = jsonObject.getJSONArray("articles");
         NewsItem[] newsItems = null;
         if (newsItemJSONArray != null) {
            newsItems = new NewsItem[newsItemJSONArray.length()];
            for (int i = 0; i < newsItemJSONArray.length(); i++) {
               JSONObject newsItemJSONObject = newsItemJSONArray.getJSONObject(
                     i);
               newsItems[i] = new NewsItem();
               newsItems[i].setAuthor(newsItemJSONObject.getString("author"));
               newsItems[i].setTitle(newsItemJSONObject.getString("title"));
               newsItems[i].setDescription(
                     newsItemJSONObject.getString("description"));
               newsItems[i].setUrl(newsItemJSONObject.getString("url"));
               //[Mani] TODO: Retrieves image URL from json response.
               newsItems[i].setUrlToImage(
                     newsItemJSONObject.getString("urlToImage"));
               newsItems[i].setPublishedAt(
                     newsItemJSONObject.getString("publishedAt"));
            }
         }
         return newsItems;
      } catch (JSONException e) {
         Log.e(TAG, "Error convert news JSON to NewsItem objects", e);
         throw new RuntimeException("Error getting news JSON from news API", e);
      }
   }
}
