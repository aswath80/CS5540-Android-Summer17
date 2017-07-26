package com.mani.cs5540.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mani.cs5540.newsapp.R;
import com.mani.cs5540.newsapp.database.NewsCursor;
import com.mani.cs5540.newsapp.util.NewsClickEvent;
import com.mani.cs5540.newsapp.util.NewsClickListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maeswara on 6/24/2017.
 */
public class NewsViewAdapter
      extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
   //TODO:[ANSWER(4)]: Recycler view now loads news from database using
   // NewsViewAdapter
   private static final String TAG = NewsViewAdapter.class.getName();
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
         "yyyy-MM-dd");
   private NewsClickListener newsClickListener;
   private NewsCursor newsCursor;

   public NewsViewAdapter(NewsCursor cursor,
         NewsClickListener newsClickListener) {
      this.newsCursor = cursor;
      this.newsClickListener = newsClickListener;
   }

   public void swapCursor(NewsCursor cursor) {
      this.newsCursor = cursor;
      notifyDataSetChanged();
   }

   @Override
   public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View newsLayoutView = layoutInflater.inflate(R.layout.news_layout, parent,
            false);
      return new NewsViewHolder(newsLayoutView);
   }

   @Override public void onBindViewHolder(NewsViewHolder holder, int position) {
      newsCursor.moveToPosition(position);
      holder.url = newsCursor.getUrl();

      holder.titleTextView.setText(newsCursor.getTitle());
      holder.descriptionTextView.setText(newsCursor.getDescription());

      loadNewsImage(holder.imageView.getContext(), newsCursor.getImageUrl(),
            holder.imageView);

      try {
         Date publishedDate = dateFormat.parse(newsCursor.getPublishedAt());
         holder.publishedAtTextView.setText(publishedDate.toString());
      } catch (ParseException e) {
         Log.e(TAG, "Parsing date filed with error: " + e.getClass().getName() +
               ": " + e.getMessage(), e);
         holder.publishedAtTextView.setText(newsCursor.getPublishedAt());
      }
   }

   private void loadNewsImage(Context context, String imageUrl,
         ImageView imageView) {
      //TODO:[ANSWER(6)]: Using Picasso API to load image from image URL in
      // JSON response.
      Picasso.with(context).load(imageUrl).resize(200, 250).into(imageView);
   }

   @Override public int getItemCount() {
      return newsCursor.getCount();
   }

   class NewsViewHolder extends RecyclerView.ViewHolder
         implements View.OnClickListener {

      private ImageView imageView;
      private TextView titleTextView;
      private TextView descriptionTextView;
      private TextView publishedAtTextView;
      private String url;

      public NewsViewHolder(View itemView) {
         super(itemView);
         this.imageView = (ImageView) itemView.findViewById(R.id.urlImage);
         this.titleTextView = (TextView) itemView.findViewById(R.id.newsTitle);
         this.descriptionTextView = (TextView) itemView.findViewById(
               R.id.newsDescription);
         this.publishedAtTextView = (TextView) itemView.findViewById(
               R.id.newsPublishedAt);
         this.titleTextView.setOnClickListener(this);
         this.descriptionTextView.setOnClickListener(this);
         this.publishedAtTextView.setOnClickListener(this);
      }

      @Override public void onClick(View v) {
         if (url != null) {
            NewsClickEvent event = new NewsClickEvent(v, url);
            newsClickListener.onNewsClick(event);
         }
      }
   }
}
