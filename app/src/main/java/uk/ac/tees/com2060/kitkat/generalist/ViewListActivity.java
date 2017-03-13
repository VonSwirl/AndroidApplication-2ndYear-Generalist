package uk.ac.tees.com2060.kitkat.generalist;

/* Created by Jerome Hurley (Q5094757) 12-Mar-2016 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewListActivity extends AppCompatActivity {

    ListView displayThisList;
    DatabaseHandler dataH;

    public ViewListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_list);

        displayThisList = (ListView) findViewById(R.id.list);

        dataH = new DatabaseHandler(this);

        final List<ListInfo> value = dataH.getAll();

        int loc = 0;

        final ArrayList<String> entries = new ArrayList<>();
        final ArrayList<String> entry = new ArrayList<>();

        for (ListInfo li : value) {
            String log = li.getName();

            entries.add(loc, log);
            loc++;
        }

        ArrayAdapter<String> listInfoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, entries);

        displayThisList.setAdapter(listInfoAdapter);

        displayThisList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ListInfo> item = dataH.getOne(position);

                for (ListInfo li : item) {
                    String log = li.getName();

                    entry.add(0, log);
                }
                Toast.makeText(ViewListActivity.this, entry.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }
}