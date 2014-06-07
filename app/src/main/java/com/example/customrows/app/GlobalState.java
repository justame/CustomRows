package com.example.customrows.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class GlobalState extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);

        Parse.initialize(this, "xVMwKQZ7unJqqW3rIapOd3PRhoBFHYOFLfEXZgn1", "bx9IqOFkorUXcHeixOtd9HKjNx4jR0lIst9cfWo9");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        if (ParseUser.getCurrentUser() == null) {
            ParseUser.enableAutomaticUser();
        };
    }
}
