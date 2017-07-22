package com.mani.cs5540.newsapp.database;

import android.database.Cursor;

/**
 * Created by maeswara on 7/22/2017.
 */
public class NewsCursor {
   //[Mani]:TODO: Wrapper class to the DB cursor to get the news fields from
   // the news table
   private Cursor cursor;

   public NewsCursor(Cursor cursor) {
      this.cursor = cursor;
   }

   public void moveToPosition(int pos) {
      cursorNullCheck();
      cursor.moveToPosition(pos);
   }

   public String getUrl() {
      cursorNullCheck();
      return cursor.getString(cursor.getColumnIndex(
            NewsDatabase.TABLE_NEWS.COLUMN_NAME_NEWS_URL));
   }

   public String getTitle() {
      cursorNullCheck();
      return cursor.getString(
            cursor.getColumnIndex(NewsDatabase.TABLE_NEWS.COLUMN_NAME_TITLE));
   }

   public String getDescription() {
      cursorNullCheck();
      return cursor.getString(cursor.getColumnIndex(
            NewsDatabase.TABLE_NEWS.COLUMN_NAME_DESCRIPTION));
   }

   public String getImageUrl() {
      cursorNullCheck();
      return cursor.getString(cursor.getColumnIndex(
            NewsDatabase.TABLE_NEWS.COLUMN_NAME_IMAGE_URL));
   }

   public String getPublishedAt() {
      cursorNullCheck();
      return cursor.getString(cursor.getColumnIndex(
            NewsDatabase.TABLE_NEWS.COLUMN_NAME_PUBLISHED_AT));
   }

   public int getCount() {
      cursorNullCheck();
      return cursor.getCount();
   }

   private void cursorNullCheck() {
      if (cursor == null) {
         throw new RuntimeException("cursor is null @ NewsCursor");
      }
   }
}
