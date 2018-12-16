package com.example.android.newsguy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.newsguy.data.NewsContract.NewsEntry;

/**
 * Created in com.example.android.newsguy.data by PETERR on 10/16/2018.
 */



public class NewsDbHelper extends SQLiteOpenHelper {
    public  final static  String DATABASE_NAME="news.db";
    public final static int DATABASE_VERSION=1;

    final String CREATE_TOP_NEWS_TABLE="CREATE TABLE " + NewsEntry.TOP_NEWS_TABLE_NAME + " (" +

            NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            NewsEntry.COLUMN_TITLE       + " TEXT, "                 +

            NewsEntry.COLUMN_DESCRIPTION + " TEXT, "                  +

            NewsEntry.COLUMN_IMAGE_URL   + " TEXT, "                    +
            NewsEntry.COLUMN_DATE   + " TEXT, "                    +

            NewsEntry.COLUMN_URL   + " TEXT"                    +
           " );";


    final String CREATE_ENTER_NEWS_TABLE="CREATE TABLE " + NewsEntry.ENTER_NEWS_TABLE_NAME + " (" +

            NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            NewsEntry.COLUMN_TITLE       + " TEXT, "                 +

            NewsEntry.COLUMN_DESCRIPTION + " TEXT, "                  +

            NewsEntry.COLUMN_IMAGE_URL   + " TEXT, "                    +
            NewsEntry.COLUMN_DATE   + " TEXT, "                    +

            NewsEntry.COLUMN_URL   + " TEXT"                    +
            " );";
    final String CREATE_LOCAL_NEWS_TABLE="CREATE TABLE " + NewsEntry.LOCAL_NEWS_TABLE_NAME + " (" +

            NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            NewsEntry.COLUMN_TITLE       + " TEXT, "                 +

            NewsEntry.COLUMN_DESCRIPTION + " TEXT, "                  +

            NewsEntry.COLUMN_IMAGE_URL   + " TEXT, "                    +
            NewsEntry.COLUMN_DATE   + " TEXT, "                    +

            NewsEntry.COLUMN_URL   + " TEXT"                    +
            " );";
    final String CREATE_SPORTS_NEWS_TABLE="CREATE TABLE " + NewsEntry.SPORTS_NEWS_TABLE_NAME + " (" +

            NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            NewsEntry.COLUMN_TITLE       + " TEXT, "                 +

            NewsEntry.COLUMN_DESCRIPTION + " TEXT, "                  +

            NewsEntry.COLUMN_IMAGE_URL   + " TEXT, "                    +
            NewsEntry.COLUMN_DATE   + " TEXT, "                    +

            NewsEntry.COLUMN_URL   + " TEXT"                    +
            " );";
    final String CREATE_TECH_NEWS_TABLE="CREATE TABLE " + NewsEntry.TECH_NEWS_TABLE_NAME + " (" +

            NewsEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            NewsEntry.COLUMN_TITLE       + " TEXT, "                 +

            NewsEntry.COLUMN_DESCRIPTION + " TEXT, "                  +

            NewsEntry.COLUMN_IMAGE_URL   + " TEXT, "                    +
            NewsEntry.COLUMN_DATE   + " TEXT, "                    +

            NewsEntry.COLUMN_URL   + " TEXT"                    +
            " );";




    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TOP_NEWS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ENTER_NEWS_TABLE);
        sqLiteDatabase.execSQL(CREATE_LOCAL_NEWS_TABLE);
        sqLiteDatabase.execSQL(CREATE_SPORTS_NEWS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TECH_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsEntry.TOP_NEWS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsEntry.ENTER_NEWS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsEntry.TECH_NEWS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsEntry.SPORTS_NEWS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsEntry.LOCAL_NEWS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
