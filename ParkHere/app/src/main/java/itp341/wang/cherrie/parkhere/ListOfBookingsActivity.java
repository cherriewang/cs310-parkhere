package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/27/16.
 * View only your bookings
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    private ListView bookingsListView;
    private BookingAdapter mBookingAdapter;
    private User myUser;

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
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_listings_actionbar_title));
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        //createCards();
        //test code for hover layout
        List<Booking> currentBookings = new ArrayList<Booking>();
        Booking booking1 = new Booking();
        booking1.setBookingTitle("Booking1");
        currentBookings.add(booking1);
        if (currentBookings != null) {
            mBookingAdapter = new BookingAdapter(getApplicationContext(), 0, (ArrayList<Booking>) currentBookings);
            bookingsListView.setAdapter(mBookingAdapter);
        }
    }

    private void createCards() {
        //Current listings
        List<Booking> currentBookings = myUser.getmBookings();
        if (currentBookings != null) {
            mBookingAdapter = new BookingAdapter(getApplicationContext(), 0, (ArrayList<Booking>) currentBookings);
            bookingsListView.setAdapter(mBookingAdapter);
        }
    }

    private void listeners(){
        bookingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
