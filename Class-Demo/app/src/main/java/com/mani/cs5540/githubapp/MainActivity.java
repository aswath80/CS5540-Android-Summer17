package com.mani.cs5540.githubapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText searchEditText;
    TextView textView;
    GithubQueryTask githubQueryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        searchEditText = (EditText)findViewById(R.id.searchQuery);
        textView = (TextView) findViewById(R.id.displayJSON);
        githubQueryTask = new GithubQueryTask(progressBar, textView);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        searchEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNo = item.getItemId();
        if(itemNo == R.id.search) {
            githubQueryTask.execute(searchEditText.getText().toString());
        }
        return true;
    }
}
