package uk.ac.tees.com2060.kitkat.generalist;

/* Created by Jerome Hurley (Q5094757) 12-Mar-2016 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class ChangeComparator implements Comparator<ListInfo>
{
    public int compare(ListInfo o1, ListInfo o2) {
        if(o1.getEpochDate() >= o2.getEpochDate()){
            return 1;
        }
        return -1;
    }
}

public class ViewListActivity extends AppCompatActivity {

    final Context context = this;
    MyListAdapter adapter;
    DatabaseHandler dh;
    ArrayList<ListInfo> entries = new ArrayList<>();

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

       // SORT BY DATE UNCOMMENT
       // Collections.sort(value, new ChangeComparator());

        final ArrayList<ListInfo> entry = new ArrayList<>();

        //Puts data into view list
        for (ListInfo li : value) {
            int active = li.getActive();
            int checked = li.getChecked();

            if (active == 1) {
                entries.add(li);
            }
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

                List<ListInfo> item = dh.getOne(adapter.getItem(position).getID());

                for (ListInfo li : item) {
                    entry.add(0, li);
                }

                Intent intent = new Intent(ViewListActivity.this, Popup.class);
                intent.putExtra("position", adapter.getItem(position).getContents());
                startActivity(intent);
                //Toast.makeText(ViewListActivity.this, entry.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<ListInfo> {

        private int layout;
        private List<ListInfo> mObjects;

        //Default constructor
        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ListInfo> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        public void showList() {
            Iterator<ListInfo> li = mObjects.iterator();
            for (int i = 0; i < mObjects.size(); i++) {
                System.out.println("\n\nZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ == "
                        + " list item " + mObjects.get(i).getName() + "  indexposition   " + i + "\nchecked: " + mObjects.get(i).getChecked());
            }
        }

        //Used for getting the view and creating the buttons etc
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            showList();
            ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_tickbox);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.list_item_editBtn);
                viewHolder.delBtn = (ImageButton) convertView.findViewById(R.id.list_item_delBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Editing.class); //Links the class to the intended place to go
                    intent.putExtra("id", mObjects.get(position).getID()); //Passes in the position to be used
                    intent.putExtra("arrayIndex", position);
                    startActivityForResult(intent, 1); //Start the activity and pass 1 as a resultCode
                }
            });

            mainViewholder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "DELETE clicked @ " + position, Toast.LENGTH_SHORT).show();

                    ListInfo toRemove = mObjects.get(position);

                    System.out.println("i am here " + toRemove.toString());
                    dh.deleteItem(toRemove.getID());
                    mObjects.remove(position);

                    Log.d("test", "delete at " + position);

                    adapter.notifyDataSetChanged();
                }
            });

            //Check box listener
            mainViewholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton Checkbutton, boolean checked) {
                    if(Checkbutton.isChecked())
                    {
                        System.out.println("CheckBox ticked at " + position);
                        dh.updateChecked(mObjects.get(position).getID(), 1); //Updated the checked box in the data base when the tickbox at position is pressed
                    }
                    else if (!Checkbutton.isChecked())
                    {
                        System.out.println("CheckBox unticked at " + position);
                        dh.updateChecked(mObjects.get(position).getID(), 0); //Updated the checked box in the data base when the tickbox at position is pressed
                    }
                }
            });
            //Check if the database value and set the checkbox accordingly
            if (mObjects.get(position).getChecked() == 1) {
                mainViewholder.checkBox.setChecked(true);
            }
            else if(mObjects.get(position).getChecked() == 0){
                mainViewholder.checkBox.setChecked(false);
            }

            mainViewholder.title.setText(getItem(position).toString());
            return convertView;
        }
    }

    //Gets the result from a returned activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String updatedName = data.getStringExtra("updatedName"); //Get the updated name from the edit class
            String updatedContents = data.getStringExtra("updatedContents"); //Get the updated contents from the edit class
            int id = data.getIntExtra("id", 0); //Get the current position it was editing
            int arrayIndex = data.getIntExtra("arrayIndex", 0);

            adapter.mObjects.get(arrayIndex).setName(updatedName); //Set the new name where the current position is
            adapter.mObjects.get(arrayIndex).setContents(updatedContents);

            adapter.notifyDataSetChanged(); //update the viewlist
        }
    }

    public class ViewHolder {
        //ImageView thumbnail;
        TextView title;
        ImageButton editBtn;
        ImageButton delBtn;
        CheckBox checkBox;
    }
}