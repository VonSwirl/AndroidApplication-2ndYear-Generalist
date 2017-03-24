package uk.ac.tees.com2060.kitkat.generalist;

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

public class Add extends AppCompatActivity {

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

        // This section of commented out code was for initial testing of database insertion.
        // dh.removeAll();
        //Log.d("Database:", "Inserting values..");

        //dh.addList(new ListInfo("Shopping Test", "egg, banana, ham", "Shopping"));

        Button svBtn = (Button) findViewById(R.id.save_button);
        Button cnclBtn = (Button) findViewById(R.id.cancel_button);

        final EditText name = (EditText) findViewById(R.id.editTextName);
        final EditText contents = (EditText) findViewById(R.id.editTextContents);
        final EditText category = (EditText) findViewById(R.id.editTextCat);

        Log.d("Database: ", "Reading all lists...");
        List<ListInfo> list = dh.getAll();

        for (ListInfo li : list) {
            String log = "ID:" + li.getID() + "Name : " + li.getName() + " Contents: " + li.getContents() + " Category: " + li.getCategory();
            Log.d("Database", log);
        }

        //Takes the text from the required fields and creates a new database entry with that information
        svBtn.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        Log.d("Database:", "Inserting values..");  //For personal testing
                        dh.addList(new ListInfo(name.getText().toString(), contents.getText().toString(), category.getText().toString()));
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