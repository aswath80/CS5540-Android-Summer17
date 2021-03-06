package com.mani.cs5540.newsapp.util;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by maeswara on 6/17/2017.
 */
public class NetworkUtils {
   //[Mani]TODO: News API and key configured here in NetworkUtils
   private static final String TAG = "NetworkUtils";
   private static final String NEWS_BASE_URL =
         "https://newsapi.org/v1/articles";
   private static final String SOURCE_PARAM = "source";
   private static final String SORT_BY_PARAM = "sortBy";
   private static final String SORT_BY_LATEST_VALUE = "latest";
   private static final String API_KEY_PARAM = "apiKey";
   //TODO: Insert API key here! The configured one is Mani's
   private static final String API_KEY_VALUE =
         "8623c400f99048b99afc68e61f8366e4";

   /**
    * Builds the URL used to talk to the news api using given api-key
    *
    * @return The URL to use to query the news server.
    */
   public static URL buildUrl() {
      //[Mani]TODO: Build news API URL
      Uri newsUri = Uri.parse(NEWS_BASE_URL).buildUpon().appendQueryParameter(
            SOURCE_PARAM, "the-next-web").appendQueryParameter(SORT_BY_PARAM,
            SORT_BY_LATEST_VALUE).appendQueryParameter(API_KEY_PARAM,
            API_KEY_VALUE).build();
      try {
         Log.d(TAG, "$$$ News URI: " + newsUri.toString());
         return new URL(newsUri.toString());
      } catch (MalformedURLException e) {
         e.printStackTrace();
         Log.e(TAG, "Exception creating news_layout URL: " + newsUri.toString(),
               e);
      }
      return null;
   }

   /**
    * This method returns the entire result from the HTTP response.
    *
    * @param url The URL to fetch the HTTP response from.
    * @return The contents of the HTTP response.
    * @throws IOException Related to network and stream reading
    */
   public static String getResponseFromHttpUrl(URL url) {
      //[Mani]TODO: Read JSON response from news API
      HttpURLConnection urlConnection = null;
      try {
         urlConnection = (HttpURLConnection) url.openConnection();
         InputStream in = urlConnection.getInputStream();

         Scanner scanner = new Scanner(in);
         scanner.useDelimiter("\\A");

         boolean hasInput = scanner.hasNext();
         if (hasInput) {
            String jsonResponse = scanner.next();
            Log.d(TAG, "$$$ JSON Response from news API:\n" + jsonResponse);
            return jsonResponse;
         } else {
            return null;
         }
      } catch (IOException e) {
         Log.e(TAG, "Error getting news JSON from news API", e);
         throw new RuntimeException("Error getting news JSON from news API", e);
      } finally {
         if (urlConnection != null) {
            urlConnection.disconnect();
         }
      }
   }
}
