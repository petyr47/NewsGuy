package com.example.android.newsguy.Sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created in com.example.android.newsguy.Sync by PETERR on 10/23/2018.
 */

public class NewsSyncIntentService extends IntentService {
    public NewsSyncIntentService() {
        super("NewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NewsSync.syncNews(this);

    }
}
