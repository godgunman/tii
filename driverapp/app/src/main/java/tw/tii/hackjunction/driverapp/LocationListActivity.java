package tw.tii.hackjunction.driverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        listView = (ListView) findViewById(R.id.listView);
        initListView();
    }

    private void initListView() {

        Intent intent = getIntent();
        String[] orderIdList = intent.getStringArrayExtra("order_id_list");
        String[] addressList = intent.getStringArrayExtra("address_list");
        String[] nameList = intent.getStringArrayExtra("name_list");

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < orderIdList.length; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("order_id", orderIdList[i]);
            item.put("address", addressList[i]);
            item.put("name", nameList[i]);
            data.add(item);
        }

        String[] from = {"order_id", "address", "name"};
        int[] to = {R.id.text_order_id, R.id.text_address, R.id.text_name};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listview_item, from, to);

        listView.setAdapter(adapter);
    }
}
