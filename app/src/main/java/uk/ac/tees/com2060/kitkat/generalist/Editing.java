package uk.ac.tees.com2060.kitkat.generalist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class Editing extends AppCompatActivity {

    private String catResult = "";
    public TextView dateView;
    public static String returnName = "RETURNAME";
    public long dbEpochDate;

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

        //Setting buttons and editTexts
        Button svBtn = (Button) findViewById(R.id.save_button);
        Button cnclBtn = (Button) findViewById(R.id.cancel_button);
        final EditText name = (EditText) findViewById(R.id.editTextName);
        final EditText contents = (EditText) findViewById(R.id.editTextContents);
        dateView = (TextView) findViewById(R.id.viewDateedit);

        final Intent intent = getIntent();//Create new getIntent
        final int id = intent.getIntExtra("id", 0); //Use it to pass the id from "ViewListActivity
        final int arrayIndex = intent.getIntExtra("arrayIndex", 0); //Get the array index
        final DatabaseHandler dh = new DatabaseHandler(this);

        //This button is added to the toolbar as a home icon, see XML attached
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to Home Screen
                finish();
            }
        });

        //Creating the spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.ContentsSpinner);

        //Setting the array adapter on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Editing.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));

        //Using standard android layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        ListInfo li = dh.getOne(id).get(0);

        //get the name, cat and contents & date.
        String dbName = li.getName();
        String dbCat = li.getCategory();
        String dbCont = li.getContents();
        dbEpochDate = li.getEpochDate();
        name.setText(dbName);
        contents.setText(dbCont);

        //Converts the database epoch date to a user readable format.
        String dateInString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(dbEpochDate * 1000));
        String[] dismantle = dateInString.split("/");
        String dayStr = dismantle[0];
        String monthStr = dismantle[1];
        String yearStr = dismantle[2];
        int day = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        month = month + 1;
        int year = Integer.parseInt(yearStr);

        //This date is used to display date to the user via the xml dateView
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

        //Compares the value with the category pulled from DB and then sets it on the spinner
        mySpinner.setAdapter(adapter);
        if (!dbCat.equals(null)) {
            int spinnerPosition = adapter.getPosition(dbCat);
            mySpinner.setSelection(spinnerPosition);
        }

        //Saves current values to db
        svBtn.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dh.updateByID(id, name.getText().toString(), contents.getText().toString(), catResult, dbEpochDate);
                        Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_LONG).show();
                        returnName = ((EditText) findViewById(R.id.editTextName)).getText().toString(); //Get the current name
                        Intent returnIntent = new Intent(); //Create a new return intent and pass the name and id

                        //Returns intents once method has completed.
                        returnIntent.putExtra("updatedName", returnName);
                        returnIntent.putExtra("id", id);
                        returnIntent.putExtra("arrayIndex", arrayIndex);
                        setResult(RESULT_OK, returnIntent); //set the result and pass the intent with the values
                        dh.close();
                        finish();
                    }
                }
        );

        //Cancel current activity
        cnclBtn.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        String dateInString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(dbEpochDate * 1000));
        String[] dismantle = dateInString.split("/");
        String dayStr = dismantle[0];
        String monthStr = dismantle[1];
        String yearStr = dismantle[2];
        int day = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);

        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    showDate(year, month, day);
                }
            };

    private void showDate(int y, int m, int d) {
        dateView.setText(new StringBuilder().append(d).append("/")
                .append(m + 1 % 12).append("/").append(y));
        String dateShow = d + "/" + m + "/" + y;

        //Convert from human readable date to epoch
        try {
            dbEpochDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dateShow).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}