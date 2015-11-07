package tw.tii.scanner.baggagescanner;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    private class MyRangeNotifier implements RangeNotifier {
        @Override
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            Log.d("YI", "# beacons: " + beacons.size());
            if (beacons.size() > 0) {
                Beacon beacon = beacons.iterator().next();
                Log.d("YI", "beacon ID: " + beacon.getId1() + ", beacon Distance: " + beacon.getDistance());
            }
        }
    }

    private BeaconManager m_BeaconM;

    private final String m_BeaconID = "b9407f30-f5f8-466e-aff9-25556b57fe6d";
    private final Beacon m_Beacon = new Beacon.Builder().setId1(m_BeaconID).build();
    private final Region m_Region = new Region("BAGGAGE_SCAN_REGION", m_Beacon.getId1(), null, null);
    private final String[] m_Permissions = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Request permission for Android API level >= 23.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < m_Permissions.length; i++) {
                if (this.checkSelfPermission(m_Permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(m_Permissions, 0);
                    break;
                }
            }
        }


        m_BeaconM = BeaconManager.getInstanceForApplication(this);

        // Add parser for iBeacon.
        m_BeaconM.getBeaconParsers().add(
                new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        );

        m_BeaconM.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        m_BeaconM.setRangeNotifier(new MyRangeNotifier());
        try {
            m_BeaconM.startRangingBeaconsInRegion(m_Region);
        }
        catch (RemoteException e) {
            // Do nothing.
        }
    }
}
