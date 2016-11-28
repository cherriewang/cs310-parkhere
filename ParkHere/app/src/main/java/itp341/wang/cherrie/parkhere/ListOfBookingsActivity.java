package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/27/16.
 * View only your bookings
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    private List<Booking> myBookings;
    private ListView bookingsListView;
    private BookingAdapter mBookingAdapter;
    private User myUser;
    private boolean cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);
        myBookings = new ArrayList<>();

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_bookings_actionbar_title));
        bookingsListView = (ListView) findViewById(R.id.listingsListView);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        cache = true;
    }

    private void createCards() {
        //Current bookings
        List<Booking> currentBookings = myBookings;
        mBookingAdapter = new BookingAdapter(getApplicationContext(), 0, (ArrayList<Booking>) currentBookings, this);
        bookingsListView.setAdapter(mBookingAdapter);
    }

    private void listeners(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference refMyBookings = myRef.child("users").child(myUser.getmNormalizedEmail()).child("mBookings");

        refMyBookings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myBookings.clear();
                long initialTime = System.nanoTime();
                if (cache == false) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Booking booking = postSnapshot.getValue(Booking.class);
                        myBookings.add(booking);
                    }
                } else { //rely on User object for adding bookings
                    for (String key : myUser.getmBookings().keySet()) {
                        myBookings.add(myUser.getmBookings().get(key));
                    }
                }
                long endingTime = System.nanoTime();
                System.out.print("Benchmark time for getting bookings: ");
                System.out.println(endingTime - initialTime);

                updateCardUi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void updateCardUi() {
        createCards();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        HomeActivity.setNavDrawerToHome();
    }
}
