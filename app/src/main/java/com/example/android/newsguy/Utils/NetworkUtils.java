package com.example.android.newsguy.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created in com.example.android.newsguy.Utils by PETERR on 10/16/2018.
 */

public final class NetworkUtils {
    private static final String BASE_URL_STRING="https://newsapi.org/v2/top-headlines";

    private static final String api_key="564ebf9dfec4409fbdaf1327e47c1932";

    private static final String API_PARAM="apiKey";

    private static final String COUNTRY_PARAM="country";

    private static final String category_PARAM="category";

    private static final String LANGUAGE_PARAM="language";

    private static final String language="en";



    public static URL buildCategoryUrl(String category, String country){
        Uri newsQueryUri=Uri.parse(BASE_URL_STRING).buildUpon()
                .appendQueryParameter(category_PARAM, category)
                .appendQueryParameter(COUNTRY_PARAM, country)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(API_PARAM, api_key)
                .build();
        try {
            URL NewsQueryUrl = new URL(newsQueryUri.toString());
            Log.v("Network Query Product", "URL: " + NewsQueryUrl);
            return NewsQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }



    public static URL buildLocalUrl(String country){
        Uri newsQueryUri=Uri.parse(BASE_URL_STRING).buildUpon()
                .appendQueryParameter(COUNTRY_PARAM, country)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(API_PARAM, api_key )
                .build();

        try {
            URL NewsQueryUrl = new URL(newsQueryUri.toString());
            Log.v("Network Query Product", "URL: " + NewsQueryUrl);
            return NewsQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

}
