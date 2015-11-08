package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class StatusCheckFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm,
                container, false);

        getActivity().setTitle("Your Order Status");

        return view;
    }

    public ParseObject getData() {
        String flightInfoStr = getString("flight_info");
        String baggageInfoStr = getString("baggage_info");
        String datetimeInfoStr = getString("pickup_datetime");
        String locationInfoStr = getString("location_info");

        ParseObject request = new ParseObject("Request");
        request.put("flight_info", flightInfoStr);
        request.put("baggage_info", baggageInfoStr);
        request.put("pickup_datetime", datetimeInfoStr);
        request.put("location_info", locationInfoStr);

        return request;
    }

    @Override
    public void putAllData() {

    }
}
