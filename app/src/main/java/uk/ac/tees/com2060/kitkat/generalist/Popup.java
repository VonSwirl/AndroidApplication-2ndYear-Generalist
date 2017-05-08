package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.List;

public class Popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        //TODO: Create a cancel button at the bottom of the screen
        //TODO: When there are mutliple values in the list stop it going off the screen (DO THE SAME FOR EDIT AND ADD)!
        //TODO: Sort the padding so it is equal on both the x and y axis

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .85), (int) (height * .75)); //Sets the size of the popup window

        final TextView viewContents = (TextView) findViewById(R.id.view_contents);
       // Button cnclBtn = (Button) findViewById(R.id.cancel_button);

        Intent intent = getIntent();//Create new getIntent
        final String content = intent.getStringExtra("position"); //Use it to pass the position from "ViewListActivity"

       viewContents.setText(content);


    }
}
