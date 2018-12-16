package com.example.android.newsguy.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.example.android.newsguy.R;
import com.example.android.newsguy.WebViewActivity;
import com.example.android.newsguy.data.NewsContract;

/**
 * Created in com.example.android.newsguy.Utils by PETERR on 10/26/2018.
 */

public class NotificationUtils {
    private static final String[] NEWS_NOTIFICATION_PROJECTION = {
            NewsContract.NewsEntry.COLUMN_TITLE,
            NewsContract.NewsEntry.COLUMN_URL
    };

    private static final int INDEX_TITLE = 0;
    private static final int INDEX_URL = 1;

    private static final int NEWS_NOTIFICATION_ID = 4194;
    private static String url;


    public static void notifyUser(Context context){

        Uri newsNotifyUri= NewsContract.NewsEntry.TOP_CONTENT_URI.buildUpon()
                .appendPath("7")
                .build();

        Cursor notifyNewsCursor = context.getContentResolver().query(
                newsNotifyUri,
                NEWS_NOTIFICATION_PROJECTION,
                null,
                null,
                null);

         /*
         * If todayWeatherCursor is empty, moveToFirst will return false. If our cursor is not
         * empty, we want to show the notification.
         */
        if (notifyNewsCursor != null && notifyNewsCursor.moveToFirst()) {

            String title = notifyNewsCursor.getString(INDEX_TITLE);
            url = notifyNewsCursor.getString(INDEX_URL);

            Resources resources = context.getResources();

            Bitmap largeIcon = BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_notifications_black_24dp);

            String notificationTitle = context.getString(R.string.app_name);


                /* getSmallArtResourceIdForWeatherCondition returns the proper art to show given an ID */
            int smallArtResourceId = R.drawable.ic_notifications_black_24dp;

                /*
                 * NotificationCompat Builder is a very convenient way to build backward-compatible
                 * notifications. In order to use it, we provide a context and specify a color for the
                 * notification, a couple of different icons, the title for the notification, and
                 * finally the text of the notification, which in our case in a summary of today's
                 * forecast.
                 */
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSmallIcon(smallArtResourceId)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(notificationTitle)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentText(title)
                    .addAction(dismissAction(context))
                    .addAction(shareAction(context))
                    .setAutoCancel(true);

                /*
                 * This Intent will be triggered when the user clicks the notification. In our case,
                 * we want to open Sunshine to the DetailActivity to display the newly updated weather.
                 */
            Intent detailIntentForToday = new Intent(context, WebViewActivity.class);
            detailIntentForToday.putExtra("link", url);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(detailIntentForToday);
            PendingIntent resultPendingIntent = taskStackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            notificationBuilder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

                /* WEATHER_NOTIFICATION_ID allows you to update or cancel the notification later on */
            if (notificationManager != null) {
                notificationManager.notify(NEWS_NOTIFICATION_ID, notificationBuilder.build());
            }

                /*
                 * Since we just showed a notification, save the current time. That way, we can check
                 * next time the weather is refreshed if we should show another notification.
                 */
            //SunshinePreferences.saveLastNotificationTime(context, System.currentTimeMillis());
        }

        /* Always close your cursor when you're done with it to avoid wasting resources. */
        if (notifyNewsCursor != null) {
            notifyNewsCursor.close();
        }


    }


    private static NotificationCompat.Action dismissAction(Context context){
        Intent intent = new Intent(context, WebViewActivity.class);



        PendingIntent pendingIntent =PendingIntent.getService(context,
                989,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_cancel_black_24dp, "DISMISS", pendingIntent);
    }
    private static NotificationCompat.Action shareAction(Context context){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setAction("share-news");
        intent.putExtra("link", url);

        PendingIntent pendingIntent =PendingIntent.getService(context,
                989,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_share_black_24dp, "Share", pendingIntent);
    }



}

