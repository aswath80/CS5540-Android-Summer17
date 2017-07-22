package com.mani.cs5540.newsapp.database;

import android.provider.BaseColumns;

/**
 * Created by maeswara on 7/22/17.
 */
public class NewsDatabase {
   //[Mani] TODO:Defines the table structure to store the news details.
   public static class TABLE_NEWS implements BaseColumns {
      public static final String TABLE_NAME = "news";
      public static final String COLUMN_NAME_TITLE = "title";
      public static final String COLUMN_NAME_DESCRIPTION = "description";
      public static final String COLUMN_NAME_NEWS_URL = "newsurl";
      public static final String COLUMN_NAME_IMAGE_URL = "imageurl";
      public static final String COLUMN_NAME_PUBLISHED_AT = "publishedat";
   }
}
