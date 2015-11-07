package tw.tii.hackjunction.passengerapp.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class SelectFlightFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_flight,
                container, false);
        return view;
    }

    public void putAllData() {
        String flightNumber =
                ((TextView)getView().findViewById(R.id.text_flight_number)).getText().toString();
        putData("flight_info", flightNumber);
    }
}
