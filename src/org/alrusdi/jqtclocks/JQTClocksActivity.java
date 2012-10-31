package org.alrusdi.jqtclocks;

import org.alrusdi.jqtclocks.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class JQTClocksActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WebView myWebView = (WebView) this.findViewById(R.id.main_view);
        
        //enabling javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
        //enabling localstorage
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true); 
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath(); 
        webSettings.setDatabasePath(databasePath);


        
        //hiding unusual whitespace from the right side 
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        //loading a main view
        myWebView.loadUrl("file:///android_asset/index.html");
    }
}