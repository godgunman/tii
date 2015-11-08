package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.ParseObject;

import java.util.Random;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class SelectDatetimeFragment extends BaseFragment {

    private TimePicker timePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_datetime,
                container, false);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(new Random().nextInt(24));
        timePicker.setCurrentMinute(new Random().nextInt(60));

        getActivity().setTitle("Select Pick Time");

        return view;
    }

    @Override
    public void putAllData() {
        String time = String.format("%02d:%02d", timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());

        putData("pickup_datetime", time);
    }

}
