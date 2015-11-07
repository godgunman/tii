package tw.tii.hackjunction.passengerapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tw.tii.hackjunction.passengerapp.fragment.BaggageInfoFragment;
import tw.tii.hackjunction.passengerapp.fragment.ConfirmFragment;
import tw.tii.hackjunction.passengerapp.fragment.LogInFragment;
import tw.tii.hackjunction.passengerapp.fragment.SelectDatetimeFragment;
import tw.tii.hackjunction.passengerapp.fragment.SelectFlightFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LogInFragment()).commit();
        }

    }

    public void nextToSelectFlight(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SelectFlightFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToBaggageInfo(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new BaggageInfoFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToSelectDatetime(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new SelectDatetimeFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void nextToConfirm(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new ConfirmFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void confirm(View view) {

    }
}
