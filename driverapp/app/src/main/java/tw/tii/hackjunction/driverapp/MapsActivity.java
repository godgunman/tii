package tw.tii.hackjunction.driverapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView locationPick;
    private TextView baggagePick;
    private Map<Marker, Boolean> isMarkerPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        baggagePick = (TextView) findViewById(R.id.text_buggage_pick);
        locationPick = (TextView) findViewById(R.id.text_location_pick);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        isMarkerPicker = new HashMap<>();
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                List<MarkerOptions> markerOptionsList = new ArrayList<>();

                for (ParseObject object : objects) {
                    ParseGeoPoint point = object.getParseGeoPoint("location");
                    if (point != null) {
                        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                        String username = object.getString("username");
                        markerOptionsList.add(new MarkerOptions()
                                .position(latLng)
                                .title(username)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        );
                    }
                }

                for (MarkerOptions markerOption : markerOptionsList) {
                    mMap.addMarker(markerOption);
                }

                if (markerOptionsList.size() != 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            markerOptionsList.get(0).getPosition(), 15));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int locationPickInt =
                                Integer.parseInt(locationPick.getText().toString());
                        int baggagePickInt =
                                Integer.parseInt(baggagePick.getText().toString());

                        if (isMarkerPicker.containsKey(marker) == false ||
                                isMarkerPicker.get(marker) == Boolean.FALSE) {
                            marker.setIcon(BitmapDescriptorFactory.
                                    defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            isMarkerPicker.put(marker, Boolean.TRUE);
                            locationPickInt++;
                            baggagePickInt++;
                        } else {
                            marker.setIcon(BitmapDescriptorFactory.
                                    defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            isMarkerPicker.put(marker, Boolean.FALSE);
                            locationPickInt--;
                            baggagePickInt--;
                        }

                        locationPick.setText(String.valueOf(locationPickInt));
                        baggagePick.setText(String.valueOf(baggagePickInt));

                        return false;
                    }
                });
            }
        });
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

        if (id == R.id.action_go) {
            //TODO
            // Go To LocationListActivity
            // Push Notification To Passenger

        } else if (id == R.id.action_init) {
            Utils.initParseData();
        }

        return super.onOptionsItemSelected(item);
    }
}
