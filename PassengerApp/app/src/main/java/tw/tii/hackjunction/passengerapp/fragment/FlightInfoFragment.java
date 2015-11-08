package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by yi on 2015/11/7.
 */
public class FlightInfoFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_info,
                container, false);
        return view;
    }

    @Override
    public void putAllData() {
        // Do nothing.
    }
}
