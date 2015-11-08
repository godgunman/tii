package tw.tii.hackjunction.driverapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.ParsePush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationListActivity extends AppCompatActivity {

    private ListView listView;
    private String[] pushChannelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        listView = (ListView) findViewById(R.id.listView);
        initListView();

        pushChannelList = getIntent().getStringArrayExtra("push_channel_list");
    }

    private void initListView() {

        Intent intent = getIntent();
        String[] orderIdList = intent.getStringArrayExtra("order_id_list");
        String[] addressList = intent.getStringArrayExtra("address_list");
        String[] nameList = intent.getStringArrayExtra("name_list");

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < orderIdList.length; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("order_id", "Order No.: " + orderIdList[i].toUpperCase().substring(0, 5));
            item.put("address", addressList[i]);
            item.put("name", nameList[i]);
            data.add(item);
        }

        String[] from = {"order_id", "address", "name"};
        int[] to = {R.id.text_order_id, R.id.text_address, R.id.text_name};
        SimpleAdapter adapter = new MySimpleAdapter(this, data, R.layout.listview_item, from, to);

        listView.setAdapter(adapter);
    }

    class MySimpleAdapter extends SimpleAdapter {

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            final CheckBox checkBoxDone = (CheckBox) view.findViewById(R.id.check_done);
            checkBoxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (pushChannelList[position] != null) {
                            Toast.makeText(LocationListActivity.this,
                                    "Passenger will be notified that their baggage have been picked up!", Toast.LENGTH_SHORT).show();

                            ParsePush push = new ParsePush();
                            push.setChannel(pushChannelList[position]);
                            push.setMessage("Your baggage has been picked up!");
                            push.sendInBackground();
                        }
                    } else {
                        checkBoxDone.setChecked(true);
                    }
                }
            });

            return view;
        }
    }
}
