package com.example.android.newsguy;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;


public class WebViewActivity extends AppCompatActivity {

    String url;
    WebView webView;
    String savedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view   );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView=new WebView(this);
        setContentView(webView);
        webView.getSettings().setJavaScriptEnabled(true);

        url=(getIntent().getStringExtra("link"));

        if (getIntent().getAction() !=null){
        String action= getIntent().getAction();
        executeTask(action);}

        if (url !=null){
            webView.loadUrl(url);
        }
    }
    public void executeTask(String action) {
        if (action.equals("share-news")){
            share();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Intent intb =new Intent(WebViewActivity.this, SettingsActivity.class);
                startActivity(intb);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.other:
               others();
                return true;
            case R.id.share:
                share();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void share(){
        String message;
        if ( savedUrl !=null){
            message=savedUrl;
        }
        else{
            message=url;
        }
        String mimeType="text/plain", title="share", subject=message;
        Intent inten = ShareCompat.IntentBuilder.from(WebViewActivity.this)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(subject)
                .getIntent();

        if (inten.resolveActivity(getPackageManager()) != null){
            startActivity(inten);}

    }

    public void others(){
        String uurl;
        if ( savedUrl !=null){
            uurl=savedUrl;
        }
        else{
            uurl=url;
        }
        Uri uri =Uri.parse(uurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }


}
