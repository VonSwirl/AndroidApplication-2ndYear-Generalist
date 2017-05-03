package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    CompactCalendarView myMainCalender;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private AlertDialog.Builder aDialogBox;
    private Context calenderContext;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adds a Toolbar to this page and gives it a title
        Toolbar homeBar = (Toolbar) findViewById(R.id.homeBar);
        setSupportActionBar(homeBar);
        homeBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.home);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)
        final Context context = this;
        final Context contxt = this;

        myMainCalender = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        myMainCalender.setUseThreeLetterAbbreviation(true);

        //this should highlight the calender on  Sun, 21 May 2017 11:28:41 GMT
        //with the cyan color this is a test...
        Event ev1 = new Event(Color.BLUE, 1494334364L, "Jay testing 21 may");
        myMainCalender.addEvent(ev1);

        // Setting Dialog Message
        //alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.);


//READ BELOW DONT DELETE THIS BLOCK----JAY
//
//           Adds an event to be drawn as an indicator in the calendar.
//           If adding multiple events see {@link #addEvents(List)}} method.
//          @param event to be added to the calendar
//          @param shouldInvalidate true if the view should invalidate
//
//        public void addEvent(Event event, boolean shouldInvalidate){
//            compactCalendarController.addEvent(event);
//            if(shouldInvalidate){
//                invalidate();
//            }
//        }
//
//
//          Adds multiple events to the calendar and invalidates the view once all events are added.
//
//        public void addEvents(List<Event> events){
//            compactCalendarController.addEvents(events);
//            invalidate();
//
/// //As above

        myMainCalender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(final Date dateClicked) {
                calenderContext = getApplicationContext();

                //How to get the current epoch time in ...
                final long datePicked = System.currentTimeMillis() / 1000;

                //Convert from epoch to human readable date
                //String date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date(datePicked * 1000));

                //Convert from human readable date to epoch
                //long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970").getTime() / 1000;

                aDialogBox = new AlertDialog.Builder(context);
                aDialogBox.setTitle("Created New List");
                aDialogBox.setMessage("Would you like to create a new list for this date?");

                // Setting "Yes" Button
                aDialogBox.setPositiveButton("Yes Please", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Links the class to the intended place to go
                        Intent intent = new Intent(calenderContext, Add.class);
                        intent.putExtra("mainDate", datePicked);
                        //Starts that activity
                        startActivity(intent);
                    }
                });

                // Setting "NO" Button
                aDialogBox.setNegativeButton("No Thanks,", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                aDialogBox.show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        Button addBtn = (Button) findViewById(R.id.addButton);

        //Add event listener to button
        addBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Add.class); //Links the class to the intended place to go
                        startActivity(intent); //Starts that activity
                    }
                }
        );

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)

        Button viewBtn = (Button) findViewById(R.id.view_Button);

        //Add event listener to button
        viewBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(contxt, ViewListActivity.class); //Links the class to the intended place to go
                        startActivity(intent); //Starts that activity
                    }
                }
        );

   /*     class CalendarActivity extends AppCompatActivity {

            CalendarView calendar;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                calendar = (CalendarView) findViewById(R.id.calendar);
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                        Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }*/


        //Creating calendar for dashboard


        //Intent intent2 = new Intent(context, ViewList.class); //Links the class to the indened place to go
        //startActivity(intent2); //Starts that activity

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.

     public Action getIndexApiAction() {
     Thing object = new Thing.Builder()
     .setName("Home Page") // TODO: Define a title for the content shown.
     // TODO: Make sure this auto-generated URL is correct.
     .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
     .build();
     return new Action.Builder(Action.TYPE_VIEW)
     .setObject(object)
     .setActionStatus(Action.STATUS_TYPE_COMPLETED)
     .build();
     }

     @Override public void onStart() {
     super.onStart();

     // ATTENTION: This was auto-generated to implement the App Indexing API.
     // See https://g.co/AppIndexing/AndroidStudio for more information.
     client.connect();
     AppIndex.AppIndexApi.start(client, getIndexApiAction());
     }

     @Override public void onStop() {
     super.onStop();

     // ATTENTION: This was auto-generated to implement the App Indexing API.
     // See https://g.co/AppIndexing/AndroidStudio for more information.
     AppIndex.AppIndexApi.end(client, getIndexApiAction());
     client.disconnect();
     }*/
}