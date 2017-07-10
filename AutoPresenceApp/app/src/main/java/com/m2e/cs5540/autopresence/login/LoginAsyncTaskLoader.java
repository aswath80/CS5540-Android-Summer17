package com.m2e.cs5540.autopresence.login;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.database.DatabaseUtil;

/**
 * Created by maeswara on 7/8/2017.
 */
public class LoginAsyncTaskLoader extends AsyncTaskLoader<String> {
   private static final String TAG = LoginAsyncTaskLoader.class.getName();
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
   private EditText usernameEditText;

   public LoginAsyncTaskLoader(Context context, EditText usernameEditText) {
      super(context);
      this.usernameEditText = usernameEditText;
      onContentChanged();
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader created");
   }


   @Override protected void onStartLoading() {
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader onStartLoading");
      //Log.d(TAG, "$$$$ LoginAsyncTaskLoader takeContentChanged() = " +
      //      takeContentChanged());
      if(takeContentChanged()) {
         forceLoad();
      }
   }

   @Override public String loadInBackground() {
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader loadInBackground");
      return databaseUtil.getUserPassword(usernameEditText.getText().toString());
   }


   @Override public void deliverResult(String data) {
      super.deliverResult(data);
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader deliverResult " + data);
   }
}
