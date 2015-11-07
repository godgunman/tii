package tw.tii.hackjunction.passengerapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by ggm on 11/7/15.
 */
public class PassengerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "y7K3CapPOCqhs5zz3cafXIOX2d30yrV6bUMfsFZo",
                "P1Qj5hYLZW1rUm5NoyHh1tbKjcEz6aQ4rKBK30cI");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
