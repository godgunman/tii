package tw.tii.scanner.baggagescanner;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by yi on 2015/11/7.
 */
public class ScannerApplication extends Application {
    private static final String APP_ID = "y7K3CapPOCqhs5zz3cafXIOX2d30yrV6bUMfsFZo";
    private static final String CLIENT_KEY = "P1Qj5hYLZW1rUm5NoyHh1tbKjcEz6aQ4rKBK30cI";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APP_ID, CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
