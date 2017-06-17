package com.mani.cs5540.githubapp.util;

import android.net.Uri;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by maeswara on 6/12/2017.
 */
public class NetworkUtil {
    private static final String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    private static final String GITHUB_QUERY_PARAM = "q";
    private static final String GITHUB_SORT_PARAM = "sort";

    public static String getSearchResponseFromGithub(String searchKey) {
        HttpURLConnection urlConnection = null;
        try {
            Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                    .appendQueryParameter(GITHUB_QUERY_PARAM, searchKey).build();
            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return "No data returned by Github for search key " + searchKey;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error","Error",e);
            return "Exception occured: " + e.getClass().getName() + ": " + e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
