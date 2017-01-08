package com.pressviewer;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TabHost;

/**
 * Created by CHATHUSANKA on 8/5/2015.
 */
public class Entertainment extends TabActivity {
    Button gossip,movies,political,songs,sports,business;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        setContentView(R.layout.entertainment);
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
              // Tab for  business
        TabHost.TabSpec tabbusiness = tabHost.newTabSpec("AllBusinessActivity");
        // setting Title and Icon for the Tab
        tabbusiness.setIndicator("Business", getResources().getDrawable(R.drawable.business));
        Intent businessIntent = new Intent(this, AllBusinessActivity.class);
        tabbusiness.setContent(businessIntent);
        // Tab for  gossip
        TabHost.TabSpec tabgossip = tabHost.newTabSpec("AllGossipActivity");
        // setting Title and Icon for the Tab
        tabgossip.setIndicator("Gossip", getResources().getDrawable(R.drawable.business));
        Intent gossipIntent = new Intent(this, AllGossipActivity.class);
        tabgossip.setContent(gossipIntent);
        // Tab for  movie
        TabHost.TabSpec tabmovie = tabHost.newTabSpec("AllMoviesActivity");
        // setting Title and Icon for the Tab
        tabmovie.setIndicator("Movies", getResources().getDrawable(R.drawable.business));
        Intent movieIntent = new Intent(this, AllMoviesActivity.class);
        tabmovie.setContent(movieIntent);

        // Tab for politcal
        TabHost.TabSpec tabpolitcal = tabHost.newTabSpec("AllPoliticalActivity");
        // setting Title and Icon for the Tab
        tabpolitcal.setIndicator("Political", getResources().getDrawable(R.drawable.business));
        Intent politcalIntent = new Intent(this, AllPoliticalActivity.class);
        tabpolitcal.setContent(politcalIntent);

        TabHost.TabSpec tabSongs = tabHost.newTabSpec("AllSongsActivity");
        // setting Title and Icon for the Tab
        tabSongs.setIndicator("Songs", getResources().getDrawable(R.drawable.business));
        Intent SongsIntent = new Intent(this, AllSongsActivity.class);
        tabSongs.setContent(SongsIntent);

        TabHost.TabSpec tabSports = tabHost.newTabSpec("AllSportsActivity");
        // setting Title and Icon for the Tab
        tabSports.setIndicator("Sports", getResources().getDrawable(R.drawable.business));
        Intent SportsIntent = new Intent(this, AllSportsActivity.class);
        tabSports.setContent(SportsIntent);



        // Adding all TabSpec to TabHost
        tabHost.addTab(tabbusiness); // Adding photos tab
        tabHost.addTab(tabgossip); // Adding photos tab
        tabHost.addTab(tabmovie); // Adding photos tab
        tabHost.addTab(tabpolitcal); // Adding photos tab
        tabHost.addTab(tabSongs); // Adding photos tab
        tabHost.addTab(tabSports); // Adding photos tab

    }
}
