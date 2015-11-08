package tw.tii.hackjunction.passengerapp.fragment;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tw.tii.hackjunction.passengerapp.R;
import tw.tii.hackjunction.passengerapp.Utils;


/**
 * Created by ggm on 11/7/15.
 */
public class SelectLocationFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private LatLng currentLanLng = new LatLng(60.203621, 24.937150);
    private Marker marker;

    private String currentAddress = "Messukeskus";
    private TextView textAddress;

    private boolean forceUpdate = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
            fragment.getMapAsync(this);
        }

        getActivity().setTitle("Select Pick Location");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_location,
                container, false);

        textAddress = (TextView) view.findViewById(R.id.text_address);

        return view;
    }


    private void updateMyLocation(LatLng newLatLng) {
        currentLanLng = newLatLng;
        marker = mGoogleMap.addMarker(new MarkerOptions().position(currentLanLng)
                .title("MyLocation").draggable(true));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLanLng, 15));

        GeoCodingTask task = new GeoCodingTask();
        task.execute(currentLanLng.latitude + "," + currentLanLng.longitude);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney and move the camera
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                forceUpdate = true;
                return false;
            }
        });

        mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                LatLng newLanLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (marker == null) {
                    updateMyLocation(newLanLng);

                } else if (forceUpdate == true) {
                    marker.remove();
                    updateMyLocation(newLanLng);
                    forceUpdate = false;
                }
            }
        });

        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.remove();
                updateMyLocation(latLng);
            }
        });
    }

    @Override
    public void putAllData() {
        putData("address", currentAddress);
        putData("location", currentLanLng.latitude + "," + currentLanLng.longitude);
    }

    private class GeoCodingTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = Utils.getGEOUrl(params[0]);
            String json = new String(Utils.urlToBytes(url));
            String latLng = Utils.getAddressFromJSON(json);

            return latLng;
        }

        @Override
        protected void onPostExecute(String address) {
            currentAddress = address;
            textAddress.setText(address);
        }
    }
}
