package tw.tii.hackjunction.passengerapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import tw.tii.hackjunction.passengerapp.fragment.BaggageInfoFragment;
import tw.tii.hackjunction.passengerapp.fragment.BaseFragment;
import tw.tii.hackjunction.passengerapp.fragment.ConfirmFragment;
import tw.tii.hackjunction.passengerapp.fragment.FlightInfoFragment;
import tw.tii.hackjunction.passengerapp.fragment.LogInFragment;
import tw.tii.hackjunction.passengerapp.fragment.SelectDatetimeFragment;
import tw.tii.hackjunction.passengerapp.fragment.SelectFlightFragment;
import tw.tii.hackjunction.passengerapp.fragment.SelectLocationFragment;
import tw.tii.hackjunction.passengerapp.fragment.SummaryFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new SelectFlightFragment()).commit();
        }
        fm = getSupportFragmentManager();

    }

    private void storeFragment() {
        BaseFragment fragment = (BaseFragment) fm.findFragmentById(R.id.fragment_container);
        fragment.putAllData();
    }

    public void nextToSelectFlight(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SelectFlightFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToShowFlightInfo(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new FlightInfoFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToSelectDatetime(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SelectDatetimeFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToBaggageInfo(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new BaggageInfoFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToSelectLocation(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SelectLocationFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToConfirm(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new ConfirmFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToSummary(View view) {
        storeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SummaryFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void confirm(final View view) {
        ConfirmFragment confirmFragment = (ConfirmFragment)
                fm.findFragmentById(R.id.fragment_container);

        ParseObject object = confirmFragment.getData();
        object.put("push_channel", Utils.getDeviceId(getApplicationContext()));
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    nextToSummary(view);
                }
            }
        });
    }
}
