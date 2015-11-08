package tw.tii.hackjunction.passengerapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.Random;

/**
 * Created by ggm on 11/7/15.
 */
public class PassengerApp extends Application {

    private static final String[] names = {"David", "Joseph", "Michael", "Moshe", "Daniel",
            "Benjamin", "James", "Jacob", "Jack", "Alexander"};


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "y7K3CapPOCqhs5zz3cafXIOX2d30yrV6bUMfsFZo",
                "P1Qj5hYLZW1rUm5NoyHh1tbKjcEz6aQ4rKBK30cI");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("BaggageScanner");
        ParsePush.subscribeInBackground(Utils.getDeviceId(getApplicationContext()));

        SharedPreferences sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.getString("username", null) == null) {
            editor.putString("username", names[new Random().nextInt(names.length)]);
            editor.commit();
        }
    }
}
