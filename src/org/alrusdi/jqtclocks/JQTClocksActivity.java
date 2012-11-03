package org.alrusdi.jqtclocks;

import java.io.*;
import java.util.List;

import android.content.res.Resources;
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
        String lang = getLang();
        myWebView.loadDataWithBaseURL("", buildTemplate(lang), "text/html", "UTF-8", null);
    }

    private String getLang()
    {
        return "ru";
    }

    private String buildTemplate(String lang)
    {
        String cur_str;
        int resId;
        String tpl = readAsset("template.html");
        String packageName = getPackageName();
        Resources res = getResources();
        String[] translatable_strings = {
            "help",
            "add",
            "back",
            "title",
            "add_clocks",
            "help_text",
            "clocks",
            "time_zones"
        };
        String result = tpl;
        for (int i=0; i<translatable_strings.length; i++)
        {
            cur_str = translatable_strings[i];
            resId = res.getIdentifier(cur_str, "string", packageName);
            result = replace(result, "{{"+cur_str+"}}", getString(resId));
        }
        return result;
    }

    private String readAsset(String name)
    {
        int ch;
        StringBuffer strContent = new StringBuffer("");
        FileInputStream fin = null;
        try {
            InputStream input = getAssets().open(name);
            Reader reader = new InputStreamReader(input, "UTF-8");
            while ((ch = reader.read()) != -1)
                strContent.append((char) ch);
            reader.close();
        } catch (Exception e) {
            return "Template reading error: "+e.getMessage();
        }
        return strContent.toString();
    }

    static String replace(String str, String pattern, String replace) {
        int start = 0;
        int end = 0;
        StringBuffer result = new StringBuffer();
        while ((end = str.indexOf(pattern, start)) >= 0) {
            result.append(str.substring(start, end));
            result.append(replace);
            start = end+pattern.length();
        }
        result.append(str.substring(start));
        str=result.toString();
        return str;
    }
}