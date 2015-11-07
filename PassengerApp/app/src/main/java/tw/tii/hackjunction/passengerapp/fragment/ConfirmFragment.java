package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}
