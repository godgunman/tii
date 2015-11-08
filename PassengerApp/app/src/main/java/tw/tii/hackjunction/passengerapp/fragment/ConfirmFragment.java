package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class ConfirmFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm,
                container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_summary);
        List<Map<String, String>> data = new ArrayList<>();

        String[] titles = {"Flight Info", "Baggage Status", "Pickup Time", "Pick Location"};
        String[] contents = {getString("flight_info"), getString("baggage_info"),
                getString("pickup_datetime"), getString("location_info")};

        for (int i = 0; i < titles.length; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("title", titles[i]);
            item.put("content", contents[i]);
            data.add(item);
        }

        String[] from = {"title", "content"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2, from, to);

        listView.setAdapter(adapter);

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
