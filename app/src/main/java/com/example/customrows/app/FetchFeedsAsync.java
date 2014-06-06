package com.example.customrows.app;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FetchFeedsAsync extends AsyncTask<Void,Void, ArrayList<FeedRow>> {
    FetchFeedCallback fetchFeedCallback;
    Integer rangeStart;
    Integer rangeEnd;
    public interface FetchFeedCallback {
        void onPostExecute(ArrayList<FeedRow> feedRows);
    }

    public FetchFeedsAsync(int rangeStart, int rangeEnd, FetchFeedCallback fetchFeedCallback){
        this.fetchFeedCallback = fetchFeedCallback;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    @Override
    protected void onPostExecute(ArrayList<FeedRow> feedRows) {
        this.fetchFeedCallback.onPostExecute(feedRows);

    }

    @Override
    protected ArrayList<FeedRow> doInBackground(Void... voids) {
        ArrayList<FeedRow> feedRows = null;
        try {
            feedRows = getFeeds();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedRows;
    }

    private ArrayList<FeedRow> getFeeds() throws JSONException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://Home.dns1.biz/FeedFilter.General.Web/API/Mobile.aspx");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("Command", "MainPage"));
            nameValuePairs.add(new BasicNameValuePair("AppVersion", "0.1"));
            nameValuePairs.add(new BasicNameValuePair("PlatformID", "Android"));
            nameValuePairs.add(new BasicNameValuePair("DeviceId", "234sew42343243"));
            nameValuePairs.add(new BasicNameValuePair("RangeStart", this.rangeStart.toString()));
            nameValuePairs.add(new BasicNameValuePair("RangeEnd", this.rangeEnd.toString()));


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(builder.toString());
            JSONObject finalResult = new JSONObject(tokener);

            JSONObject feeds = finalResult.getJSONObject("content").getJSONObject("feeds");

            JSONObject feedRaw;
            FeedRow feedrow;
            ArrayList<FeedRow> feedRows = new ArrayList<FeedRow>();
            if(feeds != null){
                for(Iterator<String> iter = feeds.keys();iter.hasNext();) {
                    String key = iter.next();
                    feedRaw = feeds.getJSONObject(key);
                    feedrow = FeedRow.FromJSON(feedRaw);
                    if(feedrow != null) {
                        feedRows.add(feedrow);
                    }
                }
            }

            //.get("374").get("feedId").toString()

            return feedRows;


        } catch (JSONException e){
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
