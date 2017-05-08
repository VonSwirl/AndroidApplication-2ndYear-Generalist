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

import java.util.Date;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    CompactCalendarView myMainCalender;
    private AlertDialog.Builder aDialogBox;
    private Context calenderContext;

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

        //Adding Events to the calender Example
        Event ev1 = new Event(Color.RED, 1495292844000L, "Mums Birthday");
        myMainCalender.addEvent(ev1);
        ev1 = new Event(Color.GREEN, 1495465644000L, "Get Pills");
        myMainCalender.addEvent(ev1);
        ev1 = new Event(Color.YELLOW, 1496070444000L, "Book Flights");
        myMainCalender.addEvent(ev1);


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
                aDialogBox = new AlertDialog.Builder(context);
                aDialogBox.setTitle("Created New List");
                aDialogBox.setMessage("Would you like to create a new list for this date?");

                // Setting Listener for "Yes" Button.
                aDialogBox.setPositiveButton("Yes Please", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Links the class to the intended place to go
                        Intent intent = new Intent(calenderContext, Add.class);
                        intent.putExtra("mainActDateBoolean", true);
                        intent.putExtra("mainActDate", dateClicked.getTime());

                        //Starts that activity.
                        startActivity(intent);
                    }
                });

                // Setting Listener for "NO" Button.
                aDialogBox.setNegativeButton("No Thanks,", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Displays the dialog to the user.
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
                        //Links the class to the intended place to go
                        Intent intent = new Intent(context, Add.class);

                        //Starts that activity
                        startActivity(intent);
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
                        //Links the class to the intended place to go
                        Intent intent = new Intent(contxt, ViewListActivity.class);

                        //Starts that activity
                        startActivity(intent);
                    }
                }
        );

        Button mapBtn = (Button) findViewById(R.id.view_Map_Button);

        //Add event listener to button
        mapBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Links the class to the intended place to go
                        Intent intent = new Intent(context, MapsActivity.class);


                        //Starts that activity
                        startActivity(intent);
                    }
                }
        );
    }
    //COMMENT BLOCK BELOW ARE TO ASSIST WITH THE EPOCH CONVERSION
                /*How to get the current epoch time in ...
                final long datePicked = System.currentTimeMillis() / 1000;

                Convert from epoch to human readable date
                String date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date(datePicked * 1000));

                Convert from human readable date to epoch
                long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970").getTime() / 1000;*/
}