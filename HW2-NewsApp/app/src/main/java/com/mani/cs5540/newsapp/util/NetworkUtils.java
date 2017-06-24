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
    private static final String TAG = NetworkUtils.class.getName();
    private static final String NEWS_BASE_URL = "https://newsapi.org/v1/articles";
    private static final String SOURCE_PARAM = "source";
    private static final String SORT_BY_PARAM = "sortBy";
    private static final String SORT_BY_LATEST_VALUE = "latest";
    private static final String API_KEY_PARAM = "apiKey";
    //TODO: Insert API key here!
    private static final String API_KEY_VALUE = "8623c400f99048b99afc68e61f8366e4";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param source The new source that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String source) {
        Uri newsUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, source)
                .appendQueryParameter(SORT_BY_PARAM, SORT_BY_LATEST_VALUE)
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE).build();
        try {
            return new URL(newsUri.toString());
        } catch(MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Exception creating news_layout URL: " + newsUri.toString(), e);
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
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
