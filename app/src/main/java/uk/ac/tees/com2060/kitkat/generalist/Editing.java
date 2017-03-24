package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

public class Editing extends AppCompatActivity {

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
        final EditText category = (EditText) findViewById(R.id.editTextCat);
        final EditText contents = (EditText) findViewById(R.id.editTextContents);

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
            category.setText(dbCat);
            contents.setText(dbCont);
        }
        //Saves current values to db
        svBtn.setOnClickListener(

                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        //Should there be a try catch around this?

                        Log.d("Database:", "Updating Entry...");  //For personal testing
                        //Position +1 because array list starts at 0. Getting all EditTexts and adding into db
                        dh.updateByID(position + 1, name.getText().toString(), contents.getText().toString(), category.getText().toString());
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
