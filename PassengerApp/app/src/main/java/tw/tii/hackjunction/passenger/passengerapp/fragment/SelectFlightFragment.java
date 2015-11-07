package tw.tii.hackjunction.passenger.passengerapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tw.tii.hackjunction.passenger.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class SelectFlightFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_flight, container, false);
        return view;

    }

}
