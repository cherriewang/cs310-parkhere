package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    private ListView bookingsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_bookings_actionbar_title));

        bookingsListView = (ListView) findViewById(R.id.listingsListView);

        //Current listings
        //ArrayList<Card> currentListing;

        //Past listings
        //ArrayList<Card> pastListings;

    }

    private void listeners(){

    }
}
