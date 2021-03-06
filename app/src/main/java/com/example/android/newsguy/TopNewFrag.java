package com.example.android.newsguy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newsguy.Sync.NewsSyncUtils;
import com.example.android.newsguy.data.NewsContract;
import com.example.android.newsguy.data.NewsPreferences;

import java.net.URL;


/**
 * Created in com.example.android.newsguy by PETERR on 10/14/2018.
 */

public class TopNewFrag extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, NewsAdapter.NewsAdapterOnClickHandler {
    RecyclerView newsList;
    private NewsAdapter newsAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private static final int TOP_NEWS_LOADER_ID = 3456;
    public static final String[] NEWS_PROJECTION = {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_TITLE,
            NewsContract.NewsEntry.COLUMN_DESCRIPTION,
            NewsContract.NewsEntry.COLUMN_URL,
            NewsContract.NewsEntry.COLUMN_IMAGE_URL,
            NewsContract.NewsEntry.COLUMN_DATE
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_list,container, false);
        TextView test= v.findViewById(R.id.tv_test);
        newsList=v.findViewById(R.id.list_view);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create an adapter for that cursor to display the data
        newsAdapter = new NewsAdapter(getContext(), TopNewFrag.this);

        // Link the adapter to the RecyclerView
        newsList.setAdapter(newsAdapter);



        getActivity().getSupportLoaderManager().initLoader(TOP_NEWS_LOADER_ID, null, this);


        NewsSyncUtils.initialize(getActivity());


        long timeSinceLastSync = NewsPreferences
                .getEllapsedTimeSinceLastSync(getActivity());

        boolean threeHoursPassedSinceLastSync = false;

        if (timeSinceLastSync >= (3 * DateUtils.HOUR_IN_MILLIS)) {
            threeHoursPassedSinceLastSync = true;
        }
        if (threeHoursPassedSinceLastSync){
            NewsSyncUtils.initialize(getActivity());;
        }



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id=(int) viewHolder.itemView.getTag();
                String _id=Integer.toString(id);

                Uri uri= NewsContract.NewsEntry.TOP_CONTENT_URI;
                uri=uri.buildUpon().appendPath(_id).build();

                getActivity().getContentResolver().delete(uri, null, null);

                getActivity().getSupportLoaderManager().restartLoader(TOP_NEWS_LOADER_ID, null, TopNewFrag.this);

            }
        }).attachToRecyclerView(newsList);


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                /* URI for all rows of weather data in our weather table */
                Uri newsQueryUri = NewsContract.NewsEntry.TOP_CONTENT_URI;

                return new CursorLoader(getActivity(),
                       newsQueryUri,
                        NEWS_PROJECTION,
                        null,
                        null,
                        null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        newsAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        newsList.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        newsAdapter.swapCursor(null);
    }

    @Override
    public void onClick(String url) {
        Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
        Uri uurl=Uri.parse(url);
        webViewIntent.putExtra("link",uurl.toString());
        startActivity(webViewIntent);
         getActivity().overridePendingTransition(0,0);
    }
}
