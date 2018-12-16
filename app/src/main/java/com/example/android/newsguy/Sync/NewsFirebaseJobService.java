package com.example.android.newsguy.Sync;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created in com.example.android.newsguy.Sync by PETERR on 10/23/2018.
 */

public class NewsFirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchNewsTask;


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mFetchNewsTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                com.example.android.newsguy.Sync.NewsSync.syncNews(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchNewsTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchNewsTask != null) {
            mFetchNewsTask.cancel(true);
        }
        return true;
    }
}
