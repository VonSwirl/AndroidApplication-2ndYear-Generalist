package uk.ac.tees.com2060.kitkat.generalist;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Add extends AppCompatActivity {

    final int active = 1;
    String catResult = "";
    public TextView dateView;
    int year, month, day;
    public static String returnName = "RETURNAME";
    public long epochDate;
    private Boolean fromMainCalender = false;
    private Date dateObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        try {
            fromMainCalender = getIntent().getExtras().getBoolean("mainActDateBoolean");
            dateObj = new Date(getIntent().getExtras().getLong("mainActDate", 0));
        } catch (Exception e) {
            e.getStackTrace();
        }

        if (fromMainCalender) {
            //Date is equal to the main activity calender date if intent is from the mainActivity.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObj);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

        } else {
            //Date is equal to the current date if intent is from the add list
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        dateView = (TextView) findViewById(R.id.viewDateadd);

        dateView.setText(String.format("%s/%s/%s", day, (month + 1) % 12, year));
        String dateTxt = day + "/" + month + "/" + year;
        //Convert from human readable date to epoch
        try {
            epochDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dateTxt).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        showDate(year, month, day);

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
        final Intent intent = getIntent();//Create new getIntent
        final int position = intent.getIntExtra("position", 0); //Use it to pass the position from "ViewListActivity


        final Spinner mySpinner = (Spinner) findViewById(R.id.ContentsSpinner); //Creating the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add.this, //Setting the array adapter on the spinner
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Using standard android layout
        mySpinner.setAdapter(adapter);

        //Listener with will get the current text that is selected in the spinner
        mySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        //Sets the global variable to be the one selected, this is so it can be added into the DB
                        catResult = parent.getItemAtPosition(pos).toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        //Takes the text from the required fields and creates a new database entry with that information
        svBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Database:", "Inserting values..");  //For personal testing
                        Log.d("DatabaseTest", "adding + " + catResult);
                        dh.addList(new ListInfo(name.getText().toString(), contents.getText().toString(), catResult, active, epochDate));
                        dh.close();
                        returnName = ((EditText) findViewById(R.id.editTextName)).getText().toString(); //Get the current name
                        Intent returnIntent = new Intent(); //Create a new return intent and pass the name and position
                        returnIntent.putExtra("updatedName", returnName);
                        returnIntent.putExtra("position", position);
                        setResult(RESULT_OK, returnIntent); //set the result and pass the intent with the values
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

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2, arg3);
                }
            };

    private void showDate(int y, int m, int d) {
        dateView.setText(new StringBuilder().append(d).append("/")
                .append(m).append("/").append(y));
        int year = y;
        int month = m;
        int day = d;
        dateView.setText(new StringBuilder().append(day).append("/")
                .append((month + 1) % 12).append("/").append(year));
        String dateTxt = day + "/" + month + "/" + year;
        //Convert from human readable date to epoch
        try {
            epochDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dateTxt).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}