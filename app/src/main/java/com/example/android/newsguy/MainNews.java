package com.example.android.newsguy;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.newsguy.Sync.NewsSyncUtils;
import com.example.android.newsguy.data.NewsPreferences;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainNews extends AppCompatActivity {

    FrameLayout frameLayout;
    private AdView adView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_top_news:
                    TopNewFrag f=new TopNewFrag();
                    setTitle("Top News");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).commit();
                    return true;
                case R.id.navigation_local:
                    LocalFrag fr=new LocalFrag();
                    setTitle("Local News");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fr).commit();
                    return true;
                case R.id.navigation_sports:
                    SportsFrag fra=new SportsFrag();
                   setTitle("Sports News");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fra).commit();
                    return true;
                case R.id.navigation_entertainment:
                    EnterFrag frag=new EnterFrag();
                    setTitle("Entertainment News");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag).commit();
                    return true;
                case R.id.navigation_tech:
                    TechFrag fragm=new TechFrag();
                    setTitle("Tech News");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragm).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news);
        frameLayout=findViewById(R.id.fragment);
        adView = findViewById(R.id.ad_view);

        MobileAds.initialize(this, getString(R.string.abmob_app_id));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("48709D5C5E343F786ADFBA371BFBF7BA")
                .build();

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

        });

        adView.loadAd(adRequest);


        long timeSinceLastSync = NewsPreferences
                .getEllapsedTimeSinceLastSync(this);

        boolean threeHoursPassedSinceLastSync = false;

        if (timeSinceLastSync >= (1 * DateUtils.HOUR_IN_MILLIS)) {
            threeHoursPassedSinceLastSync = true;
        }
        if (threeHoursPassedSinceLastSync){
             NewsSyncUtils.startImmediateSync(this);
        }

        checkInternet(frameLayout);



        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_top_news);

        TopNewFrag f=new TopNewFrag();
        setTitle("Top News");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //startActivity(new Intent(MainNews.this, SettingsActivity.class));
            NewsSyncUtils.startImmediateSync(this);
            return true;
        }
        if (id==R.id.about){
            AboutFrag fw=new AboutFrag();
            setTitle("About");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fw).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkInternet(View view){
        ConnectivityManager connectivityManager=
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo=connectivityManager.getActiveNetworkInfo();

        if (!(activeNetInfo !=null && activeNetInfo.isConnectedOrConnecting())){

            final Snackbar snackbar=Snackbar.make(view,
                    "News Guy is not connected to the internet, News will not be Synced", Snackbar.LENGTH_LONG);
            snackbar.show();

        }}
}
