package uk.ac.tees.com2060.kitkat.generalist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.List;

public class Add extends AppCompatActivity {

    String catResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        //Adds a Toolbar to this page and gives it a title
        Toolbar addBar = (Toolbar) findViewById(R.id.addBar);
        setSupportActionBar(addBar);
        addBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.add_item);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //This button is added to the toolbar as a home icon, see XML attached
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to Home Screen
                finish();
            }
        });

        final DatabaseHandler dh = new DatabaseHandler(this);

        Button svBtn = (Button) findViewById(R.id.save_button);
        Button cnclBtn = (Button) findViewById(R.id.cancel_button);

        final EditText name = (EditText) findViewById(R.id.editTextName);
        final EditText contents = (EditText) findViewById(R.id.editTextContents);
        //final EditText category = (EditText) findViewById(R.id.editTextCat);

        final Spinner mySpinner = (Spinner) findViewById(R.id.ContentsSpinner); //Creating the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add.this, //Setting the array adapter on the spinner
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Using standard android layout
        mySpinner.setAdapter(adapter);

        //Listener with will get the current text that is selected in the spinner
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Sets the global variable to be the one selected, this is so it can be added into the DB
                catResult = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Takes the text from the required fields and creates a new database entry with that information
        svBtn.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        Log.d("Database:", "Inserting values..");  //For personal testing
                        Log.d("DatabaseTest", "adding + " + catResult);
                        dh.addList(new ListInfo(name.getText().toString(), contents.getText().toString(), catResult));
                        finish();
                    }
                }
        );

        cnclBtn.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }


}

//This doesnt need to be done?
//        Log.d("Database: ", "Reading all lists...");
//        List<ListInfo> list = dh.getAll();
//
//        for (ListInfo li : list) {
//            String log = "ID:" + li.getID() + "Name : " + li.getName() + " Contents: " + li.getContents() + " Category: " + li.getCategory();
//            Log.d("Database", log);
//        }