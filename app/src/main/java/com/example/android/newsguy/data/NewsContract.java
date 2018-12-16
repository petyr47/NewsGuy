package com.example.android.newsguy.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created in com.example.android.newsguy.data by PETERR on 10/15/2018.
 */

public final class NewsContract {

    public static class NewsEntry implements BaseColumns {

        public final static String TOP_NEWS_TABLE_NAME = "topnews";
        public final static String LOCAL_NEWS_TABLE_NAME ="localnews";
        public final static String TECH_NEWS_TABLE_NAME = "technews";
        public final static String SPORTS_NEWS_TABLE_NAME = "sportsnews";
        public final static String ENTER_NEWS_TABLE_NAME="enternews";


        public final static String COLUMN_TITLE="title";
        public final static String COLUMN_DESCRIPTION="description";
        public final static String COLUMN_IMAGE_URL="imageurl";
        public final static String COLUMN_DATE="date";
        public final static String COLUMN_URL="url";


        public final static String PATH_TOP_NEWS=TOP_NEWS_TABLE_NAME;
        public final static String PATH_ENTER=ENTER_NEWS_TABLE_NAME;
        public final static String PATH_LOCAL=LOCAL_NEWS_TABLE_NAME;
        public final static String PATH_SPORTS=SPORTS_NEWS_TABLE_NAME;
        public final static String PATH_TECH=TECH_NEWS_TABLE_NAME;



        public static final String CONTENT_AUTHORITY = "com.example.android.newsguy";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final Uri TOP_CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_NEWS).build();
        public static final Uri TECH_CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TECH).build();
        public static final Uri SPORTS_CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPORTS).build();
        public static final Uri LOCAL_CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCAL).build();
        public static final Uri ENTER_CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTER).build();


  }
}
