package com.example.customrows.app;


import org.json.JSONException;
import org.json.JSONObject;

public class FeedRow {
    public static final String MESSAGE = "message";
    public static final String PICTURE_MAIN = "pictureMain";

    public String message = "";
    public String pictureMain = "";

    public FeedRow(String message, String pictureMain){
        this.message = message;
        this.pictureMain = pictureMain;
    }

    public static FeedRow FromJSON(JSONObject feedData){
        FeedRow feedRow = null;
        try {
            String message = feedData.getString(MESSAGE);
            String pictureMain = feedData.getString(PICTURE_MAIN);
            feedRow = new FeedRow(message,pictureMain);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedRow;
    }
}
