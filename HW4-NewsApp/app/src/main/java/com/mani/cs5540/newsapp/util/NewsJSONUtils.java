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
         JSONArray newsItemJSONArray = jsonObject.getJSONArray("results");
         NewsItem[] newsItems = null;
         if (newsItemJSONArray != null) {
            newsItems = new NewsItem[newsItemJSONArray.length()];
            for (int i = 0; i < newsItemJSONArray.length(); i++) {
               JSONObject newsItemJSONObject = newsItemJSONArray.getJSONObject(
                     i);
               newsItems[i] = new NewsItem();
               newsItems[i].setAuthor(newsItemJSONObject.getString("source"));
               newsItems[i].setTitle(newsItemJSONObject.getString("title"));
               newsItems[i].setDescription(
                     newsItemJSONObject.getString("abstract"));
               newsItems[i].setUrl(newsItemJSONObject.getString("url"));
               newsItems[i].setUrlToImage(getImageUrl(newsItemJSONObject));
               newsItems[i].setPublishedAt(
                     newsItemJSONObject.getString("published_date"));
            }
         }
         return newsItems;
      } catch (JSONException e) {
         Log.e(TAG, "Error convert news JSON to NewsItem objects", e);
         throw new RuntimeException("Error getting news JSON from news API", e);
      }
   }

   //[Mani] TODO: Retrieves image URL from media attribute if one is available.
   private static String getImageUrl(JSONObject newsItemJSONObject)
         throws JSONException {
      JSONArray mediaArray = newsItemJSONObject.optJSONArray("media");
      if (mediaArray != null && mediaArray.length() > 0) {
         JSONArray mediaMetaArray = mediaArray.getJSONObject(0).optJSONArray(
               "media-metadata");
         if (mediaMetaArray != null && mediaMetaArray.length() > 0) {
            if (mediaMetaArray.length() > 1) {
               return mediaMetaArray.getJSONObject(1).getString("url");
            } else {
               return mediaMetaArray.getJSONObject(0).getString("url");
            }
         }
      }
      return null;
   }
}
