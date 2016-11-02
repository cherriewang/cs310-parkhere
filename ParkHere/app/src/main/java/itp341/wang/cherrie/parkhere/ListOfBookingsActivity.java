package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.Booking;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    private ListView bookingsListView;
    private BookingAdapter mBookingAdapter;

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
        ArrayList<Booking> currentListings = new ArrayList<Booking>();
        Booking booking1 = new Booking();
        booking1.setBookingTitle("Booking");
        booking1.setBookingOwner("Owner");
        Booking booking2 = new Booking();
        booking2.setBookingTitle("Booking2");
        booking2.setBookingOwner("Owner2");
        currentListings.add(booking1);
        currentListings.add(booking2);
        mBookingAdapter = new BookingAdapter(getApplicationContext(), 0, currentListings);
        bookingsListView.setAdapter(mBookingAdapter);

        //Past listings
        //ArrayList<Card> pastListings;

    }

    private void listeners(){
        bookingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
