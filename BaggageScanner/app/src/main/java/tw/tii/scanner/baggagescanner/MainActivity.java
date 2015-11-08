package tw.tii.scanner.baggagescanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {
    private static final int RING_REQUEST_CODE = 4831003;
    private void ring() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void blink() {
        final View view = findViewById(R.id.backgroundLayout);
        final Integer white = getResources().getColor(android.R.color.white);
        final Integer blink = getResources().getColor(R.color.colorBlink);
        view.setBackgroundColor(blink);
        ValueAnimator a1 = ValueAnimator.ofObject(new ArgbEvaluator(), blink, white);
        a1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        a1.setDuration(200);
        a1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setBackgroundColor(blink);
                ValueAnimator a2 = ValueAnimator.ofObject(new ArgbEvaluator(), blink, white);
                a2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        view.setBackgroundColor((Integer) animator.getAnimatedValue());
                    }
                });
                a2.setDuration(200);
                a2.start();
            }
        });
        a1.start();
    }

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
                        m_ListData.add(0, DateFormat.getDateTimeInstance().format(new Date()) + "\nPush notification sent to somebody.");

                        ParsePush push = new ParsePush();
                        push.setChannel(PUSH_CHANNEL);
                        push.setMessage("Your baggage has been checked-in.");
                        push.sendInBackground(new SendCallback() {
                            @Override
                            public void done(ParseException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        blink();

                                        TextView textView = (TextView)findViewById(R.id.value_push);
                                        textView.setText("true");

                                        ListView listView = (ListView)findViewById(R.id.listView);
                                        ((ArrayAdapter<String>)listView.getAdapter()).notifyDataSetChanged();
                                    }
                                });

                                ring();
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
    private ArrayList<String> m_ListData = new ArrayList<String>();

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

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m_ListData));

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
        if (id == R.id.action_reset) {
            m_RangeNotifier.reset();

            TextView textView = (TextView)findViewById(R.id.value_push);
            textView.setText("false");

            Toast.makeText(this, R.string.action_reset, Toast.LENGTH_SHORT).show();
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
