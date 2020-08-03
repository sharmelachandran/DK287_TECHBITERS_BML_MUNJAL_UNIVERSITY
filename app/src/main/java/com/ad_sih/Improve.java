package com.ad_sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
public class Improve extends AppCompatActivity {
Button maintain,play,precaution,map;
 MediaPlayer m;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_improve);
        m= MediaPlayer.create(getApplicationContext(),R.raw.m2);
        m.start();

        maintain=findViewById(R.id.main);
        play=findViewById(R.id.play);
        precaution=findViewById(R.id.pre);
        map=findViewById(R.id.map);
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {m.stop();
                Intent i = new Intent(getApplicationContext(),Chooselevel.class);
                startActivity(i);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {m.stop();
                Intent i = new Intent(getApplicationContext(),theme.class);
                startActivity(i);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {m.stop();
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.google.co.in/maps/search/nearby+neurologist/"));
                startActivity(viewIntent);
                //Intent i = new Intent(getApplicationContext(), Doctor_map.class);
                //startActivity(i);finish();
            }
        });
        precaution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {m.stop();
                setContentView(R.layout.webview);
                webView = (WebView) findViewById(R.id.webview);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAppCacheEnabled(true);
                webView.loadUrl("https://adfinder1.blogspot.com/p/dietary-and-lifestyle-guidelines-for.html");
            }
        });

    }
    @Override
    public void onBackPressed () {
        if(webView.canGoBack())
            webView.goBack();

        else
        {
            restartApp();
            super.onBackPressed();}

    }
    public void restartApp()
    {
        Intent i=new Intent(getApplicationContext(),Improve.class);
        startActivity(i);
        finish();
    }

}
