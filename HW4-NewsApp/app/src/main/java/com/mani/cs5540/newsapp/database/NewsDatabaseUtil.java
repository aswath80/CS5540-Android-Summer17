package com.mani.cs5540.newsapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mani.cs5540.newsapp.NewsItem;

/**
 * Created by maeswara on 7/22/2017.
 */
public class NewsDatabaseUtil {
   //[Mani]TODO: Helper method to read all news records from database.
   public static NewsCursor getAllNewsCursor(SQLiteDatabase db) {
      Cursor cursor = db.query(NewsDatabase.TABLE_NEWS.TABLE_NAME, null, null,
            null, null, null,
            NewsDatabase.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT + " DESC");
      return new NewsCursor(cursor);
   }

   //[Mani]TODO: Helper method to replace all news records in database from
   // server.
   public static void updateNews(SQLiteDatabase db, NewsItem[] newsItems) {
      db.beginTransaction();
      deleteAllNews(db);
      try {
         for (NewsItem newsItem : newsItems) {
            ContentValues cv = new ContentValues();
            cv.put(NewsDatabase.TABLE_NEWS.COLUMN_NAME_TITLE,
                  newsItem.getTitle());
            cv.put(NewsDatabase.TABLE_NEWS.COLUMN_NAME_DESCRIPTION,
                  newsItem.getDescription());
            cv.put(NewsDatabase.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT,
                  newsItem.getPublishedAt());
            cv.put(NewsDatabase.TABLE_NEWS.COLUMN_NAME_IMAGE_URL,
                  newsItem.getUrlToImage());
            cv.put(NewsDatabase.TABLE_NEWS.COLUMN_NAME_NEWS_URL,
                  newsItem.getUrl());
            db.insert(NewsDatabase.TABLE_NEWS.TABLE_NAME, null, cv);
         }
         db.setTransactionSuccessful();
      } finally {
         db.endTransaction();
         db.close();
      }
   }

   private static void deleteAllNews(SQLiteDatabase db) {
      db.delete(NewsDatabase.TABLE_NEWS.TABLE_NAME, null, null);
   }
}
