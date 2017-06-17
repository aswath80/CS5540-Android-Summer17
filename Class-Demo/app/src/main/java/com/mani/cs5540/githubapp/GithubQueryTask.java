package com.mani.cs5540.githubapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mani.cs5540.githubapp.util.NetworkUtil;

/**
 * Created by maeswara on 6/12/2017.
 */

public class GithubQueryTask extends AsyncTask<String, Void, String> {
    ProgressBar progressBar;
    TextView textView;

    GithubQueryTask(ProgressBar progressBar, TextView textView) {
        this.progressBar = progressBar;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        return NetworkUtil.getSearchResponseFromGithub(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText(s);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
