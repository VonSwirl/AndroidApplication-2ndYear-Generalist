package uk.ac.tees.com2060.kitkat.generalist;

/* Created by Jerome Hurley (Q5094757) 12-Mar-2016 */

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.util.Log;

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


    private DatabaseHandler dh;
    final Context context = this;

    
    MyListAdapter adapter;


    public ViewListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        //Adds a Toolbar to this page and gives it a title
        Toolbar listBar = (Toolbar) findViewById(R.id.listBar);
        setSupportActionBar(listBar);
        listBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.view_lists);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //This button is added to the toolbar as a add item icon, see XML attached
        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Links the class to the intended place to go
                Intent intent = new Intent(context, Add.class);

                //Starts that activity
                startActivity(intent);
            }
        });
        //This button is added to the toolbar as a home icon, see XML attached
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to Home Screen
                finish();
            }
        });

        ListView lv = (ListView) findViewById(R.id.list); //Creating the list view

        //Database handling, getting all the items adding them to an array list
        dh = new DatabaseHandler(this);
        final List<ListInfo> value = dh.getAll();
        int loc = 0;
        final ArrayList<String> entries = new ArrayList<>();
        final ArrayList<String> entry = new ArrayList<>();

        //Puts data into view list
        for (ListInfo li : value) {
            String log = li.getName();

            entries.add(loc, log);
            loc++;
        }

        //Setting the adapter for the view list (so each row)
       // lv.setAdapter(new MyListAdapter(this, R.layout.view_row, entries));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.view_row, entries);

        adapter = new MyListAdapter(this, R.layout.view_row, entries);
        lv.setAdapter(adapter);


        //Listener for when the list has been pressed, This will show up what is in the list currently
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ListInfo> item = dh.getOne(position);

                for (ListInfo li : item) {
                    String log = li.getContents();

                    entry.add(0, log);
                }

                startActivity(new Intent(ViewListActivity.this, Popup.class));
                Toast.makeText(ViewListActivity.this, entry.get(0), Toast.LENGTH_SHORT).show();
            }
        });

    }


private class MyListAdapter extends ArrayAdapter<String>{


        private int layout;
        private List<String> mObjects;

        //Default constructor
        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }


        //Used for getting the view and creating the buttons etc
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if (convertView == null) {
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
                   // Toast.makeText(getContext(), "The EDIT IMAGE was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Editing.class); //Links the class to the intended place to go
                    intent.putExtra("position",position);
                    startActivity(intent); //Starts that activity
                    //TODO: When the save button is pressed this also dynamically updates the list
                }
            });
            mainViewholder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "The DELETE IMAGE was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    //TODO: Get delete button working and dynamically updating the list
                    //dh.deleteItem(position);
                    //Have to use a method like these to update the current listview?
                    //notifyDataSetChanged();
                    //adapter.notifyDataSetChanged();

                  /*  if(position == 0){

                        Log.d("test", "delete at " + position);
                    }
                    else {*/
                      //  dh.deleteList(position + 1);//This works for now but if you delete the last item it will crash because no +1 exists
                   // }
                }
            });

            mainViewholder.title.setText(getItem(position));
            return convertView;
        }

    }

    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        ImageButton editBtn;
        ImageButton delBtn;
    }


}