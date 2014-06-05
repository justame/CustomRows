package com.example.customrows.app;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import java.util.ArrayList;


public class MainActivity extends SlidingActivity {

    ListView feedListView;
    CustomRowArrayAdapter customRowArrayAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_menu);

        getSlidingMenu().setBehindOffset(100);

        feedListView = (ListView)findViewById(R.id.listView);
        initFeeds();
//        feedListView.setAdapter(customRowArrayAdapter);
//        feedListView.setOnScrollListener(new EndlessScrollListener() {
//            // Triggered only when new data needs to be appended to the list
//            // Add whatever code is needed to append new items to your AdapterView
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void initFeeds(){
        final Context context =  getApplicationContext();

        customRowArrayAdapter =  new CustomRowArrayAdapter(getApplicationContext(),R.layout.custom_row, new ArrayList<CustomRowAdapter>());
        feedListView.setAdapter(customRowArrayAdapter);

        fetchFeeds(1, 10, new OnFetchFeedsListener() {
            @Override
            public void onFetchFeedsSuccess(ArrayList<FeedRow> feeds) {
                applyFeeds(feeds);
            }
        });

        feedListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                int rangeEndIndex = (page * 10);
                int rangeStartIndex = rangeEndIndex - 10 +1;
//                TextView loader = new TextView(context);
//                loader.setText("Loading");

                Log.i("setOnScrollListener","position: " + feedListView.getLastVisiblePosition() + " totalItemsCount:" + Integer.toString(totalItemsCount) );

//                feedListView.addFooterView(loader);
                fetchFeeds(rangeStartIndex, rangeEndIndex, new OnFetchFeedsListener() {
                    @Override
                    public void onFetchFeedsSuccess(ArrayList<FeedRow> feeds) {
                        if (feeds != null) {
                            applyFeeds(feeds);
                        }
                    }
                });
            }
        });
    }

    public void fetchFeeds(int startRangeIndex, int endRangeIndex , final OnFetchFeedsListener onFetchFeedsListener){
        Log.i("fetchFeeds",Integer.toString(startRangeIndex) + "- " + Integer.toString(endRangeIndex) );

        FetchFeedsAsync fetchFeedsAsync = new FetchFeedsAsync(startRangeIndex,endRangeIndex, new FetchFeedsAsync.FetchFeedCallback(){
            @Override
            public void onPostExecute(ArrayList<FeedRow> feedRows) {
                onFetchFeedsListener.onFetchFeedsSuccess(feedRows);
            }
        }
        );
        fetchFeedsAsync.execute();
    }

    public void applyFeeds(ArrayList<FeedRow> feeds){
        final Context context = getApplicationContext();
        for (FeedRow feedRow : feeds) {
            CustomRowAdapter customRowAdapter = new CustomRowAdapter(context, feedRow);
            customRowArrayAdapter.add(customRowAdapter);
        }
        customRowArrayAdapter.notifyDataSetChanged();

    }

//    public void getFeeds(final OnFetchFeedsListener onFetchFeedsListener){
//        FetchFeedsAsync fetchFeedsAsync = new FetchFeedsAsync(startRangeIndex,startRangeIndex + 10, new FetchFeedsAsync.FetchFeedCallback(){
//            @Override
//            public void onPostExecute(ArrayList<FeedRow> feedRows) {
//                onFetchFeedsListener.onGetFeedsSuccess(feedRows);
//            }
//        }
//        );
//        fetchFeedsAsync.execute();
//        startRangeIndex += 10;
//
//    }
}
