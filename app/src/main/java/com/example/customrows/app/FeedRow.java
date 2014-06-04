package com.example.customrows.app;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class FeedRow {
    public static final String MESSAGE = "message";
    public static final String PICTURE_MAIN = "pictureMain";
    public static final String PRICE = "price";

    public String message = "";
    public String pictureMain = "";
    public BigDecimal price;

    public FeedRow(String message, String pictureMain, BigDecimal price){
        this.message = message;
        this.pictureMain = pictureMain;
        this.price = price;
    }

    public static FeedRow FromJSON(JSONObject feedData){
        FeedRow feedRow = null;
        try {
            String message = feedData.getString(MESSAGE);
            String pictureMain = feedData.getString(PICTURE_MAIN);
            BigDecimal price = new BigDecimal(feedData.getString(PRICE));
            feedRow = new FeedRow(message,pictureMain,price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedRow;
    }
}
