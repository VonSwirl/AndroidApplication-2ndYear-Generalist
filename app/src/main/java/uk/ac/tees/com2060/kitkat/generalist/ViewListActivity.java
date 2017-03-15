package uk.ac.tees.com2060.kitkat.generalist;

/* Created by Jerome Hurley (Q5094757) 12-Mar-2016 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewListActivity extends AppCompatActivity {

    DatabaseHandler dh;

    public ViewListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        ListView lv = (ListView) findViewById(R.id.list); //Creating the list view

        //Database handling, getting all the items adding them to an array list
        dh = new DatabaseHandler(this);
        final List<ListInfo> value = dh.getAll();
        int loc = 0;
        final ArrayList<String> entries = new ArrayList<>();
        final ArrayList<String> entry = new ArrayList<>();

        for (ListInfo li : value) {
            String log = li.getName();

            entries.add(loc, log);
            loc++;
        }

        lv.setAdapter(new MyListAdapter(this, R.layout.view_row, entries)); //Setting the adapter for the view list (so each row)

        //Listener for when the list has been pressed, This will show up what is in the list currently?
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ListInfo> item = dh.getOne(position);

                for (ListInfo li : item) {
                    String log = li.getContents();

                    entry.add(0, log);
                }
                Toast.makeText(ViewListActivity.this, entry.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<String>{

        private int layout;
        private List<String> mObjects;

        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.list_item_editBtn);
                viewHolder.delBtn = (ImageButton) convertView.findViewById(R.id.list_item_delBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "The EDIT IMAGE was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "The DELETE IMAGE was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });

            mainViewholder.title.setText(getItem(position));
            return convertView;
        }
    }

    public class ViewHolder{
        ImageView thumbnail;
        TextView title;
        ImageButton editBtn;
        ImageButton delBtn;
    }


}