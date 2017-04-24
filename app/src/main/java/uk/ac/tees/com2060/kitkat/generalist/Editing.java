package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Intent;
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

public class Editing extends AppCompatActivity {

    String catResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);


        //Adds a Toolbar to this page and gives it a title
        Toolbar editBar = (Toolbar) findViewById(R.id.editBar);
        setSupportActionBar(editBar);
        editBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.edit_item);
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

        //Setting buttons and editTexts
        Button svBtn = (Button) findViewById(R.id.save_button);
        Button cnclBtn = (Button) findViewById(R.id.cancel_button);
        final EditText name = (EditText) findViewById(R.id.editTextName);
        final EditText contents = (EditText) findViewById(R.id.editTextContents);

        // final EditText category = (EditText) findViewById(R.id.editTextCat);

        Spinner mySpinner = (Spinner) findViewById(R.id.ContentsSpinner); //Creating the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Editing.this, //Setting the array adapter on the spinner
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

        Intent intent = getIntent();//Create new getIntent
        final int position = intent.getIntExtra("position", 0); //Use it to pass the position from "ViewListActivity"


        final DatabaseHandler dh = new DatabaseHandler(this);
        List<ListInfo> item; //Create a new List that will hold the current item
        item = dh.getOne(position); //Pass the single item from the position into the List
        for (ListInfo li : item) { //Create ListInfo class and for each item

            //get the name, cat and contents
            String dbName = li.getName();
            String dbCat = li.getCategory();
            String dbCont = li.getContents();

            //Set the values to the current ExitText
            name.setText(dbName);
            contents.setText(dbCont);

            //Compares the value with the category pulled from DB and then sets it on the spinner
            mySpinner.setAdapter(adapter);
            if (!dbCat.equals(null)) {
                int spinnerPosition = adapter.getPosition(dbCat); //If
                mySpinner.setSelection(spinnerPosition);
            }
        }
        //Saves current values to db
        svBtn.setOnClickListener(

                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        //Should there be a try catch around this?

                        Log.d("Database:", "Updating Entry...");  //For personal testing
                        //Position +1 because array list starts at 0. Getting all EditTexts and adding into db
                        dh.updateByID(position + 1, name.getText().toString(), contents.getText().toString(), catResult);
                        finish();


                    }
                }
        );

        cnclBtn.setOnClickListener( //Cancel current activity

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

    }
}
