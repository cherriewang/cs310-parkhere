package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    private ListView bookingsListView;
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

        ArrayList<Card> bookingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, bookingCards);
        CardListView bookingListView = (CardListView) this.findViewById();
        if (bookingListView != null){
            bookingListView.setAdapter(mCardArrayAdapter);
        }


    }
    private ArrayList<Card> createCards() {
        ArrayList<Card> cards = new ArrayList<>();

        // Create a Listing Card
        for (Booking b : myUser.getmBookings()) {
            // TODO: Setup card layout here
            Card bookingCard = new Card(this, R.layout.row_booking_layout);
            bookingCard.setTitle(b.getBookingTitle());
            cards.add(bookingCard);
        }
        Debug.printToast(cards.size() + "", getApplicationContext());
        return cards;
    }

    private void listeners(){

    }
}
