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
public class ConfirmFragment extends BaseFragment {

    private TextView flightInfo;
    private TextView baggageInfo;
    private TextView datetimeInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm,
                container, false);

        flightInfo = (TextView) view.findViewById(R.id.text_flight_info);
        flightInfo.setText(getString("flight_info"));

        baggageInfo = (TextView) view.findViewById(R.id.text_baggage_info);
        baggageInfo.setText(getString("baggage_info"));

        datetimeInfo = (TextView) view.findViewById(R.id.text_datetime_info);
        datetimeInfo.setText(getString("pickup_datetime"));

        return view;
    }

    public ParseObject getData() {
        String flightInfoStr = flightInfo.getText().toString();
        String baggageInfoStr = baggageInfo.getText().toString();
        String datetimeInfoStr = datetimeInfo.getText().toString();

        ParseObject request = new ParseObject("Request");
        request.put("flight_info", flightInfoStr);
        request.put("baggage_info", baggageInfoStr);
        request.put("pickup_datetime", datetimeInfoStr);

        return request;
    }

    @Override
    public void putAllData() {

    }
}
