package com.mani.cs5540.newsapp.schedulerjob;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by maeswara on 7/22/2017.
 */

public class NewsLoaderJobScheduler {
   private static final String TAG = "NewsLoaderJobScheduler";
   private static final String NEWS_JOB_TAG = "ManiNewsLoaderSchedulerJob";

   public static void scheduleNewsLoad(Context context) {
      //TODO:[ANSWER(5)]: Firebase dispatcher to schedule background job for
      // new update
      Driver driver = new GooglePlayDriver(context);
      FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

      Job constraintRefreshJob = dispatcher.newJobBuilder().setService(
            NewsLoaderService.class).setTag(NEWS_JOB_TAG).setConstraints(
            Constraint.ON_ANY_NETWORK).setLifetime(Lifetime.FOREVER)
            .setRecurring(true).setTrigger(Trigger.executionWindow(0, 60))
            .setReplaceCurrent(true).build();

      dispatcher.schedule(constraintRefreshJob);
      Log.d(TAG, "$$$ NewsLoaderService scheduled to run in the background");
   }

   public static void stopScheduledNewsLoad(Context context) {
      Driver driver = new GooglePlayDriver(context);
      FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
      dispatcher.cancel(NEWS_JOB_TAG);
      Log.d(TAG, "$$$ NewsLoaderService background job stopped");
   }
}
