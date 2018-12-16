package com.example.android.newsguy.Utils;

import android.content.ContentValues;
import android.util.Log;

import com.example.android.newsguy.data.NewsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created in com.example.android.newsguy.Utils by PETERR on 10/16/2018.
 */

public final class JsonUtils {

    public static ContentValues[] getNewsFromJson(String JsonResult) throws JSONException{

        JSONObject jsonObject=new JSONObject(JsonResult);

            /* Is there an error? */
        if (jsonObject.has("status")) {
            String status= jsonObject.getString("status");

            switch (status){
                case "ok":
                    break;
                case "error":
                    Log.e("JSON ERROR", jsonObject.getString("message"));
                    return null;
            }

        }

        JSONArray articles= jsonObject.getJSONArray("articles");

        ContentValues[] NewsContentValues = new ContentValues[articles.length()];


        for (int i=0; i<articles.length(); i++){

            String title;
            String description;
            String url;
            String date;
            String imageUrl;


            JSONObject article=articles.getJSONObject(i);

            title=article.getString("title");
            description=article.getString("description");
            url=article.getString("url");
            imageUrl=article.getString("urlToImage");
            date=article.getString("publishedAt");


            ContentValues newsValues = new ContentValues();

            newsValues.put(NewsContract.NewsEntry.COLUMN_TITLE, title);
            newsValues.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION, description);
            newsValues.put(NewsContract.NewsEntry.COLUMN_URL, url);
            newsValues.put(NewsContract.NewsEntry.COLUMN_IMAGE_URL, imageUrl);
            newsValues.put(NewsContract.NewsEntry.COLUMN_DATE, date);

            NewsContentValues[i] = newsValues;

            }

            return NewsContentValues;

    }

//   // private static String makeDate(String Undate){
//        String[] dates=Undate.split("T", 2);
//
//        return dates[0]+","+dates[1].substring(0, -1);
//    }
}
