package uk.ac.tees.com2060.kitkat.generalist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


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

        Log.d("Database: ", "Reading all lists..." );
        List<ListInfo> list = dh.getAll();

        for (ListInfo li : list) {
            String log = "ID:" + li.getID() + "Name : " + li.getName() + " Contents: " + li.getContents() + " Category: " +li.getCategory();
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


        //This method kills the activity
        cnclBtn.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v){
                        finish();
                    }
                }
        );





        //dh.removeAll();


        //dh.addList(new ListInfo("List 1 (shoppin)", "eggs, cheese and milk", "Shopping"));
        //dh.addList(new ListInfo("List 2 (shoppin)", "eggs", "Shopping"));
        //dh.addList(new ListInfo("List 3 (pharmacy)", "2100/5, paracetamol", "Pharmacy"));
        //dh.addList(new ListInfo("List 4 (clothes)", "jean", "Clothing"));



    }
}