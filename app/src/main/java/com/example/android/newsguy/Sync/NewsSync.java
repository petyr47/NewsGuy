package com.example.android.newsguy.Sync;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.newsguy.Utils.JsonUtils;
import com.example.android.newsguy.Utils.NetworkUtils;
import com.example.android.newsguy.Utils.NotificationUtils;
import com.example.android.newsguy.data.NewsContract;
import com.example.android.newsguy.data.NewsPreferences;

import java.net.URL;

/**
 * Created in com.example.android.newsguy.Sync by PETERR on 10/20/2018.
 */

public class NewsSync {

    synchronized public static void syncNews(Context context) {

        try {
            /*
             * The getUrl method will return the URL that we need to get the forecast JSON for the
             * weather. It will decide whether to create a URL based off of the latitude and
             * longitude or off of a simple location as a String.
             */
            URL topNewsRequestUrl = NetworkUtils.buildLocalUrl("us");
            URL techNewsRequestUrl=NetworkUtils.buildCategoryUrl("technology", "us");
            URL sportsNewsRequestUrl=NetworkUtils.buildCategoryUrl("sports", "gb");
            URL enterNewsRequestUrl=NetworkUtils.buildCategoryUrl("entertainment", "us");
            URL localNewsRequestUrl=NetworkUtils.buildLocalUrl("ng");

            /* Use the URL to retrieve the JSON */
            String topNewsResponse = NetworkUtils.getResponseFromHttpUrl(topNewsRequestUrl);
            String enterNewsResponse = NetworkUtils.getResponseFromHttpUrl(enterNewsRequestUrl);
            String techNewsResponse = NetworkUtils.getResponseFromHttpUrl(techNewsRequestUrl);
            String sportsNewsResponse = NetworkUtils.getResponseFromHttpUrl(sportsNewsRequestUrl);
            String localNewsResponse = NetworkUtils.getResponseFromHttpUrl(localNewsRequestUrl);


            /* Parse the JSON into a list of weather values */
            ContentValues[] topNewsValues = JsonUtils.getNewsFromJson(topNewsResponse);
            ContentValues[] techNewsValues = JsonUtils.getNewsFromJson(techNewsResponse);
            ContentValues[] sportsNewsValues = JsonUtils.getNewsFromJson(sportsNewsResponse);
            ContentValues[] enterNewsValues = JsonUtils.getNewsFromJson(enterNewsResponse);
            ContentValues[] localNewsValues = JsonUtils.getNewsFromJson(localNewsResponse);

            /*
             * In cases where our JSON contained an error code, getWeatherContentValuesFromJson
             * would have returned null. We need to check for those cases here to prevent any
             * NullPointerExceptions being thrown. We also have no reason to insert fresh data if
             * there isn't any to insert.
             */
            if (topNewsValues != null && topNewsValues.length != 0 && techNewsValues !=null
                    &&sportsNewsValues !=null && enterNewsValues !=null && localNewsValues !=null) {
                /* Get a handle on the ContentResolver to delete and insert data */
                ContentResolver newsContentResolver = context.getContentResolver();

                /* Delete old weather data because we don't need to keep multiple days' data */
                newsContentResolver.delete(
                        NewsContract.NewsEntry.TOP_CONTENT_URI,
                        null,
                        null);
                newsContentResolver.delete(
                        NewsContract.NewsEntry.ENTER_CONTENT_URI,
                        null,null);
                newsContentResolver.delete(
                        NewsContract.NewsEntry.SPORTS_CONTENT_URI,
                        null,null);
                newsContentResolver.delete(
                        NewsContract.NewsEntry.TECH_CONTENT_URI,
                        null,null);
                newsContentResolver.delete(
                        NewsContract.NewsEntry.LOCAL_CONTENT_URI,
                        null,null);

                /* Insert our new weather data into Sunshine's ContentProvider */
                newsContentResolver.bulkInsert(
                        NewsContract.NewsEntry.TOP_CONTENT_URI, topNewsValues);

                newsContentResolver.bulkInsert(
                        NewsContract.NewsEntry.TECH_CONTENT_URI, techNewsValues);

                newsContentResolver.bulkInsert(
                        NewsContract.NewsEntry.SPORTS_CONTENT_URI, sportsNewsValues);

                newsContentResolver.bulkInsert(
                        NewsContract.NewsEntry.ENTER_CONTENT_URI, enterNewsValues);

                newsContentResolver.bulkInsert(
                        NewsContract.NewsEntry.LOCAL_CONTENT_URI, localNewsValues);

                NewsPreferences.saveLastSyncTime(context, System.currentTimeMillis());

                NotificationUtils.notifyUser(context);



            }

        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }
    }
}
