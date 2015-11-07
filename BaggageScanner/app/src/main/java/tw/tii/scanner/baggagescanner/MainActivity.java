package tw.tii.scanner.baggagescanner;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SendCallback;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.RangedBeacon;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    private class MyRangeNotifier implements RangeNotifier {
        private boolean checked;

        public MyRangeNotifier() {
            checked = false;
        }

        public void reset() {
            checked = false;
        }

        @Override
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            for (final Beacon beacon : beacons) {
                if (beacon.getId1().toString().equals(BEACON_ID)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView)findViewById(R.id.value_distance);
                            textView.setText(String.valueOf(beacon.getDistance()));
                        }
                    });

                    if ((!checked) && (beacon.getDistance() < 1)) {
                        ParsePush push = new ParsePush();
                        push.setChannel(PUSH_CHANNEL);
                        push.setMessage("Your baggage has been checked-in.");
                        push.sendInBackground(new SendCallback() {
                            @Override
                            public void done(ParseException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView textView = (TextView)findViewById(R.id.value_push);
                                        textView.setText("true");
                                    }
                                });

                            }
                        });
                        checked = true;
                    }
                }
            }
        }
    }

    private BeaconManager m_BeaconM;
    private MyRangeNotifier m_RangeNotifier;

    private final String BEACON_ID = "b9407f30-f5f8-466e-aff9-25556b57fe6d";
    private final Region SCAN_REGION = new Region("BAGGAGE_SCAN_REGION", null, null, null);
    private final String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN
    };
    private final String PUSH_CHANNEL = "BaggageScanner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_RangeNotifier.reset();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView)findViewById(R.id.value_push);
                        textView.setText("false");
                    }
                });
            }
        });

        ParsePush.subscribeInBackground(PUSH_CHANNEL);

        // Request permission for Android API level >= 23.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < PERMISSIONS.length; i++) {
                if (this.checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(PERMISSIONS, 0);
                    break;
                }
            }
        }


        m_BeaconM = BeaconManager.getInstanceForApplication(this);
        // Add parser for iBeacon.
        m_BeaconM.getBeaconParsers().add(
                new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        );
        // http://stackoverflow.com/a/25521588
        RangedBeacon.setSampleExpirationMilliseconds(6000);
        m_BeaconM.bind(this);

        m_RangeNotifier = new MyRangeNotifier();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParsePush.unsubscribeInBackground(PUSH_CHANNEL);
        m_BeaconM.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeaconServiceConnect() {
        m_BeaconM.setRangeNotifier(m_RangeNotifier);
        try {
            m_BeaconM.startRangingBeaconsInRegion(SCAN_REGION);
        } catch (RemoteException e) {
            // Do nothing.
        }
    }
}
