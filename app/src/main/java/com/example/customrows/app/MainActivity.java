package com.example.customrows.app;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import java.util.ArrayList;
import java.util.Observable;


public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_menu);

        getSlidingMenu().setBehindOffset(100);
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

    public void getFeeds(View v){
        final ListView listView = (ListView)findViewById(R.id.listView);
        final CustomRowArrayAdapter customRowArrayAdapter = new CustomRowArrayAdapter(getApplicationContext(),R.layout.custom_row, new ArrayList<CustomRowAdapter>());
        listView.setAdapter(customRowArrayAdapter);

        final GetFeedsAsync getFeedsAsync = new GetFeedsAsync(this,listView, new GetFeedsAsync.GetFeedCallback(){
            @Override
            public void onPostExecute(ArrayList<FeedRow> feedRows) {
                ArrayList<CustomRowAdapter> listRows = new ArrayList<CustomRowAdapter>();
                for (FeedRow feedRow : feedRows){
                    CustomRowAdapter customRowAdapter = new CustomRowAdapter(getApplicationContext(),feedRow);
                    customRowArrayAdapter.add(customRowAdapter);
                }
                customRowArrayAdapter.notifyDataSetChanged();
            }
        }
        );
        getFeedsAsync.execute(this);








    }
}
