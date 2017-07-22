package com.mani.cs5540.newsapp.schedulerjob;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mani.cs5540.newsapp.util.NewsReferesher;

/**
 * Created by maeswara on 7/22/2017.
 */
public class NewsLoaderService extends JobService {
   private static final String TAG = "NewsLoaderService";

   //[Mani]TODO: The scheduler job that updates the news database with latest
   // data from API
   @Override public boolean onStartJob(JobParameters params) {
      Log.d(TAG, "$$$ NewsLoaderService.onStartJob called");
      new AsyncTask<Void, Void, Void>() {
         @Override protected Void doInBackground(Void... params) {
            NewsReferesher.updateDatabaseFromServer(NewsLoaderService.this);
            return null;
         }
      }.execute();

      Toast.makeText(NewsLoaderService.this, "News updated from " + "server!",
            Toast.LENGTH_LONG).show();

      return false;
   }

   @Override public boolean onStopJob(JobParameters params) {
      Log.d(TAG, "$$$ NewsLoaderService.onStopJob called");
      return false;
   }
}
