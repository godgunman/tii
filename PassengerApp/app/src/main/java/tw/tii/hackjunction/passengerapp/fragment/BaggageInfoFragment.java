package tw.tii.hackjunction.passengerapp.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class BaggageInfoFragment extends BaseFragment {

    private Spinner baggageSize;
    private Spinner baggageNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baggage_info,
                container, false);

        baggageSize = (Spinner) view.findViewById(R.id.spinner_baggage_size);
        baggageNumber = (Spinner) view.findViewById(R.id.spinner_baggage_number);
        initSpinner();

        return view;
    }

    private void initSpinner() {
        Resources res = getActivity().getResources();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.size_of_baggage));
        baggageSize.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, res.getStringArray(R.array.number_of_baggage));
        baggageNumber.setAdapter(adapter2);
    }

    @Override
    public void putAllData() {
        View view = getView();
        String baggageNumberStr = (String) baggageNumber.getSelectedItem();
        String baggageSizeStr = (String) baggageSize.getSelectedItem();

        putData("baggage_info", baggageNumberStr + "," + baggageSizeStr);
    }

}
