package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tw.tii.hackjunction.passengerapp.R;


/**
 * Created by ggm on 11/7/15.
 */
public class SelectLocationFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private LatLng currentLanLng = new LatLng(60.203621, 24.937150);
    private String currentAddress = "Messukeskus";


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_location,
                container, false);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney and move the camera
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(currentLanLng).title(currentAddress));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLanLng, 15));

    }

    @Override
    public void putAllData() {
        putData("location_info", currentLanLng.toString() + "," + currentAddress);
    }

}
