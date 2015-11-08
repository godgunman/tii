package tw.tii.hackjunction.passengerapp.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by ggm on 11/7/15.
 */
public class BaggageInfoFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_baggage_info,
                container, false);

        getActivity().setTitle("Baggage Information");

        view.findViewById(R.id.btn_plus1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.value_big_baggage);
                tv.setText(String.valueOf(Integer.valueOf(tv.getText().toString()) + 1));
            }
        });
        view.findViewById(R.id.btn_plus2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.value_medium_baggage);
                tv.setText(String.valueOf(Integer.valueOf(tv.getText().toString()) + 1));
            }
        });
        view.findViewById(R.id.btn_plus3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.value_small_baggage);
                tv.setText(String.valueOf(Integer.valueOf(tv.getText().toString()) + 1));
            }
        });
        view.findViewById(R.id.btn_minus1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.value_big_baggage);
                int value = Integer.valueOf(tv.getText().toString());
                if (value > 0)
                    tv.setText(String.valueOf(value - 1));
            }
        });
        view.findViewById(R.id.btn_minus2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.value_medium_baggage);
                int value = Integer.valueOf(tv.getText().toString());
                if (value > 0)
                    tv.setText(String.valueOf(value - 1));
            }
        });
        view.findViewById(R.id.btn_minus3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.value_small_baggage);
                int value = Integer.valueOf(tv.getText().toString());
                if (value > 0)
                    tv.setText(String.valueOf(value - 1));
            }
        });


        return view;
    }

    @Override
    public void putAllData() {
        View view = getView();
        putData("baggage_info",
                "big: " + Integer.valueOf(((TextView)view.findViewById(R.id.value_big_baggage)).getText().toString()) +
                ", medium: " + Integer.valueOf(((TextView)view.findViewById(R.id.value_medium_baggage)).getText().toString()) +
                ", small: " + Integer.valueOf(((TextView)view.findViewById(R.id.value_small_baggage)).getText().toString())
        );
    }

}
