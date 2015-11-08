package tw.tii.hackjunction.passengerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ggm on 11/7/15.
 */
public abstract class BaseFragment extends Fragment {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    void putData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sp.getString(key, "");

    }

    public abstract void putAllData();
}
