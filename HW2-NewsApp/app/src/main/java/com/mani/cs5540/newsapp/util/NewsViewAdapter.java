package com.mani.cs5540.newsapp.util;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mani.cs5540.newsapp.NewsItem;
import com.mani.cs5540.newsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maeswara on 6/24/2017.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
    private static final String TAG = NewsViewAdapter.class.getName();
    private NewsItem[] newsItems;
    private NewsClickListener newsClickListener;

    public NewsViewAdapter(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView publishedAtTextView;
        private String url;

        public NewsViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.urlImage);
            this.titleTextView = (TextView) itemView.findViewById(R.id.newsTitle);
            this.descriptionTextView = (TextView) itemView.findViewById(R.id.newsDescription);
            this.publishedAtTextView = (TextView) itemView.findViewById(R.id.newsPublishedAt);
            this.titleTextView.setOnClickListener(this);
            this.descriptionTextView.setOnClickListener(this);
            this.publishedAtTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(url != null) {
                NewsClickEvent event = new NewsClickEvent(v, url);
                newsClickListener.onNewsClick(event);
            }
        }
    }

    public void setNewsItems(NewsItem[] newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View newsLayoutView = layoutInflater.inflate(R.layout.news_layout, parent, false);
        return new NewsViewHolder(newsLayoutView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.url = newsItems[position].getUrl();
        holder.titleTextView.setText(newsItems[position].getTitle());
        holder.descriptionTextView.setText(newsItems[position].getDescription());
        holder.imageView.setImageBitmap(newsItems[position].getImageBitMap());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date publishedDate = dateFormat.parse(newsItems[position].getPublishedAt());
            holder.publishedAtTextView.setText(publishedDate.toString());
        } catch (ParseException e) {
            Log.e(TAG, "Parsing date filed with error: " + e.getClass().getName() + ": " + e.getMessage(), e);
            holder.publishedAtTextView.setText(newsItems[position].getPublishedAt());
        }
    }

    @Override
    public int getItemCount() {
        return newsItems != null ? newsItems.length : 0;
    }
}
