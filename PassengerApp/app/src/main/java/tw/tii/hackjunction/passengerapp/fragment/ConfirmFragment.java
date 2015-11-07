package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class ConfirmFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm,
                container, false);
        return view;
    }

    public ParseObject getData() {
        View view = getView();

        String flightInfo = ((TextView)view.findViewById(R.id.text_flight_info)).toString();
        String baggageInfo = ((TextView)view.findViewById(R.id.text_baggage_info)).toString();
        String datetimeInfo = ((TextView)view.findViewById(R.id.text_datetime_info)).toString();

        ParseObject request = new ParseObject("Request");
        request.put("flight_info", flightInfo);
        request.put("baggage_info", baggageInfo);
        request.put("pickup_datetime", datetimeInfo);

        return request;
    }
}
