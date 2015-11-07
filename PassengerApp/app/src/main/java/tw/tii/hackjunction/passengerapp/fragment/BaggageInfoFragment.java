package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class BaggageInfoFragment extends BaseFragment {

    private TimePicker timePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baggage_info,
                container, false);
        return view;
    }

    public void putAllData() {
//        timePicker = (TimePicker) getView().findViewById(R.id.timePicker);
//        putData("pickup_datetime", timePicker.toString());
    }

}
