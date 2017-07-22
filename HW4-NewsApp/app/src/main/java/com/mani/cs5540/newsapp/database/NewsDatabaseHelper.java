package com.mani.cs5540.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by maeswara on 07/22/17
 */
public class NewsDatabaseHelper extends SQLiteOpenHelper {
   //TODO:[ANSWER(3)]: Implementation of SQLiteOpenHelper to maintain news
   // database
   //[Mani]TODO: Database utility to create the new table, read news table
   // and perform updates on the news table.

   private static final int DATABASE_VERSION = 1;
   private static final String DATABASE_NAME = "mani_news.db";
   private static final String TAG = "NewsDatabaseReader";

   public NewsDatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override public void onCreate(SQLiteDatabase db) {
      String queryString =
            "CREATE TABLE " + NewsDatabase.TABLE_NEWS.TABLE_NAME + " (" +
                  NewsDatabase.TABLE_NEWS._ID +
                  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  NewsDatabase.TABLE_NEWS.COLUMN_NAME_TITLE +
                  " TEXT NOT NULL, " +
                  NewsDatabase.TABLE_NEWS.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                  NewsDatabase.TABLE_NEWS.COLUMN_NAME_NEWS_URL + " TEXT, " +
                  NewsDatabase.TABLE_NEWS.COLUMN_NAME_IMAGE_URL + " TEXT, " +
                  NewsDatabase.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT + " TEXT); ";

      Log.d(TAG, "Create table SQL: " + queryString);
      db.execSQL(queryString);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }
}
