package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)
        final Context context = this;
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
        //    final Context cont = this;
        //    Button calbut = (Button) findViewById(R.id.button5);

        //Add event listener to button
        //    calbut.setOnClickListener(
        //            new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    Intent intent = new Intent(cont, Calendar.class); //Links the class to the intended place to go
        //                    startActivity(intent); //Starts that activity

        //                }
        //            }

        //    );

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)
        final Context contxt = this;
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

