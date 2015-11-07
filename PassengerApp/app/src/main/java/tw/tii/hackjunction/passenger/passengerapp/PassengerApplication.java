package tw.tii.hackjunction.passenger.passengerapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by ggm on 11/7/15.
 */
public class PassengerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "y7K3CapPOCqhs5zz3cafXIOX2d30yrV6bUMfsFZo",
                "P1Qj5hYLZW1rUm5NoyHh1tbKjcEz6aQ4rKBK30cI");
    }
}
