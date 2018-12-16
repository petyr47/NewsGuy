package com.example.android.newsguy.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created in com.example.android.newsguy.data by PETERR on 10/19/2018.
 */

public class NewsProvider extends ContentProvider {

    public static final int CODE_TOP_NEWS = 100;
    public static final int CODE_TOP_NEWS_WITH_ID = 101;

    public static final int CODE_ENTERTAINMENT= 200;
    public static final int CODE_ENTERTAINMENT_WITH_ID = 201;

    public static final int CODE_SPORTS = 300;
    public static final int CODE_SPORTS_WITH_ID = 301;

    public static final int CODE_LOCAL = 400;
    public static final int CODE_LOCAL_WITH_ID = 401;

    public static final int CODE_TECH = 500;
    public static final int CODE_TECH_WITH_ID = 501;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NewsContract.NewsEntry.CONTENT_AUTHORITY;

         /* This URI is content://com.example.android.sunshine/weather/ */
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_TOP_NEWS, CODE_TOP_NEWS);
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_TOP_NEWS+ "/#", CODE_TOP_NEWS_WITH_ID);

        matcher.addURI(authority, NewsContract.NewsEntry.PATH_ENTER, CODE_ENTERTAINMENT);
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_ENTER+ "/#", CODE_ENTERTAINMENT_WITH_ID);

        matcher.addURI(authority, NewsContract.NewsEntry.PATH_SPORTS, CODE_SPORTS);
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_SPORTS+ "/#", CODE_SPORTS_WITH_ID);

        matcher.addURI(authority, NewsContract.NewsEntry.PATH_LOCAL, CODE_LOCAL);
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_LOCAL+ "/#", CODE_LOCAL_WITH_ID);

        matcher.addURI(authority, NewsContract.NewsEntry.PATH_TECH, CODE_TECH);
        matcher.addURI(authority, NewsContract.NewsEntry.PATH_TECH+ "/#", CODE_TECH_WITH_ID);

        return matcher;
    }



    private NewsDbHelper NewsdbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        NewsdbHelper = new NewsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_TOP_NEWS: {

                cursor = NewsdbHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        NewsContract.NewsEntry.TOP_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_SPORTS: {

                cursor = NewsdbHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        NewsContract.NewsEntry.SPORTS_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_ENTERTAINMENT: {

                cursor = NewsdbHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        NewsContract.NewsEntry.ENTER_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_LOCAL: {

                cursor = NewsdbHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        NewsContract.NewsEntry.LOCAL_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_TECH: {

                cursor = NewsdbHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        NewsContract.NewsEntry.TECH_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CODE_TOP_NEWS_WITH_ID: {
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = NewsdbHelper.getReadableDatabase().query(
                        NewsContract.NewsEntry.TOP_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_ENTERTAINMENT_WITH_ID: {
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = NewsdbHelper.getReadableDatabase().query(
                        NewsContract.NewsEntry.ENTER_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_SPORTS_WITH_ID: {
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = NewsdbHelper.getReadableDatabase().query(
                        NewsContract.NewsEntry.SPORTS_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_LOCAL_WITH_ID: {
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = NewsdbHelper.getReadableDatabase().query(
                        NewsContract.NewsEntry.LOCAL_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_TECH_WITH_ID: {
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = NewsdbHelper.getReadableDatabase().query(
                        NewsContract.NewsEntry.TECH_NEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsDeleted;
        SQLiteDatabase Ddb=NewsdbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE_TOP_NEWS:

                numRowsDeleted = Ddb.delete(
                        NewsContract.NewsEntry.TOP_NEWS_TABLE_NAME,
                        null,
                        null);
                break;
            case CODE_TOP_NEWS_WITH_ID:
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs =new String[] {String.valueOf(ContentUris.parseId(uri))};
                numRowsDeleted =Ddb.delete(NewsContract.NewsEntry.TOP_NEWS_TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_SPORTS:

                numRowsDeleted = Ddb.delete(
                        NewsContract.NewsEntry.SPORTS_NEWS_TABLE_NAME,
                        null,
                        null);
                break;
            case CODE_SPORTS_WITH_ID:
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs =new String[] {String.valueOf(ContentUris.parseId(uri))};
                numRowsDeleted =Ddb.delete(NewsContract.NewsEntry.SPORTS_NEWS_TABLE_NAME, selection, selectionArgs);
                break;

            case CODE_ENTERTAINMENT:

                numRowsDeleted = Ddb.delete(
                        NewsContract.NewsEntry.ENTER_NEWS_TABLE_NAME,
                        null,
                        null);
                break;
            case CODE_ENTERTAINMENT_WITH_ID:
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs =new String[] {String.valueOf(ContentUris.parseId(uri))};
                numRowsDeleted =Ddb.delete(NewsContract.NewsEntry.ENTER_NEWS_TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_LOCAL:

                numRowsDeleted = Ddb.delete(
                        NewsContract.NewsEntry.LOCAL_NEWS_TABLE_NAME,
                        null,
                        null);
                break;
            case CODE_LOCAL_WITH_ID:
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs =new String[] {String.valueOf(ContentUris.parseId(uri))};
                numRowsDeleted =Ddb.delete(NewsContract.NewsEntry.LOCAL_NEWS_TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_TECH:

                numRowsDeleted = Ddb.delete(
                        NewsContract.NewsEntry.TECH_NEWS_TABLE_NAME,
                        null,
                        null);
                break;
            case CODE_TECH_WITH_ID:
                selection = NewsContract.NewsEntry._ID+"=?";
                selectionArgs =new String[] {String.valueOf(ContentUris.parseId(uri))};
                numRowsDeleted =Ddb.delete(NewsContract.NewsEntry.TECH_NEWS_TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new RuntimeException("We are not implementing update in News Guy");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in News Guy.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        throw new RuntimeException(
                "We are not implementing insert in News Guy. Use bulkInsert instead");
    }



    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = NewsdbHelper.getWritableDatabase();
        int rowsInserted = 0;

        switch (sUriMatcher.match(uri)) {
            case CODE_TOP_NEWS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NewsContract.NewsEntry.TOP_NEWS_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            case CODE_SPORTS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NewsContract.NewsEntry.SPORTS_NEWS_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            case CODE_ENTERTAINMENT:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NewsContract.NewsEntry.ENTER_NEWS_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            case CODE_TECH:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NewsContract.NewsEntry.TECH_NEWS_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            case CODE_LOCAL:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(NewsContract.NewsEntry.LOCAL_NEWS_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }

    }
}
