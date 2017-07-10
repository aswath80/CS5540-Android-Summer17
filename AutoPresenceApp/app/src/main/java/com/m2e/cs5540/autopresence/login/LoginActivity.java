package com.m2e.cs5540.autopresence.login;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.BaseActivity;

/**
 * Created by maeswara on 7/8/2017.
 */
public class LoginActivity extends BaseActivity
      implements View.OnClickListener, LoaderManager.LoaderCallbacks<String> {
   private static final String TAG = LoginActivity.class.getName();
   private EditText usernameEditText;
   private Button loginButton;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login_layout);
      this.usernameEditText = (EditText)findViewById(R.id.usernameText);
      addButtonClickListener();
   }

   private void addButtonClickListener() {
      loginButton = (Button) findViewById(R.id.loginButton);
      loginButton.setOnClickListener(this);
   }

   @Override public void onClick(View v) {
      EditText userNameText = (EditText) findViewById(R.id.usernameText);
      String username = userNameText.getText().toString();

      EditText passwordText = (EditText) findViewById(R.id.passwordText);
      String password = userNameText.getText().toString();

      Log.d(TAG, "$$$$$$ Login process start... ");

      if (username == null || username.isEmpty() || password == null ||
            password.isEmpty()) {
         Toast.makeText(this, "Enter a valid " + "username and password!",
               Toast.LENGTH_SHORT).show();
      } else {
         Log.d(TAG, "$$$$$$ LoadManager.initLoader called");
         getLoaderManager().initLoader(0, null, this);
         showProgressDialog();
      }
   }

   @Override public Loader<String> onCreateLoader(int id, Bundle args) {
      Log.d(TAG, "$$$$$$ LoginActivity.onCreateLoader called");
      return new LoginAsyncTaskLoader(this, usernameEditText);
   }

   @Override
   public void onLoadFinished(Loader<String> loader, String password) {
      hideProgressDialog();
      Log.d(TAG, "$$$$$$ LoginActivity.onLoadFinished called");
      Log.d(TAG, "$$$$$$ Password from database " + password);
      Toast.makeText(this, "Password from DB is " + password,
            Toast.LENGTH_LONG).show();
   }

   @Override public void onLoaderReset(Loader<String> loader) {

   }
}
