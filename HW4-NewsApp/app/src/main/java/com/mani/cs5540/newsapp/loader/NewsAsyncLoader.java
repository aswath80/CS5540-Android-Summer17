package com.mani.cs5540.newsapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.mani.cs5540.newsapp.util.NewsReferesher;

/**
 * Created by maeswara on 6/17/2017.
 */
public class NewsAsyncLoader extends AsyncTaskLoader<Void> {
   //TODO:[ANSWER(2)]: AsyncTaskLoader implementation to load news
   //[Mani] TODO: NewsAsyncLoader that does the initial load of news
   // from server into Database
   private static final String TAG = "NewsAsyncLoader";
   private Context context;

   public NewsAsyncLoader(Context context) {
      super(context);
      this.context = context;
   }

   @Override protected void onStartLoading() {
      super.onStartLoading();
      forceLoad();
      Log.d(TAG, "$$$ NewsAsyncLoader onStartLoading");
   }

   @Override public Void loadInBackground() {
      Log.d(TAG, "$$$ NewsAsyncLoader loadInBackground");
      NewsReferesher.updateDatabaseFromServer(context);
      return null;
   }

   @Override public void deliverResult(Void data) {
      super.deliverResult(data);
      Log.d(TAG, "$$$ NewsAsyncLoader deliverResult " + data);
   }
}
