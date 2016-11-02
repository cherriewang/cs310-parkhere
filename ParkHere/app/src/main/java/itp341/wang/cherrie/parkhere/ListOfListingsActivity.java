package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/28/16.
 */

public class ListOfListingsActivity extends AppCompatActivity {
    private User myUser;
    private List<Listing> myListings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);
        myListings = new ArrayList<>();
        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_listings_actionbar_title));
//
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();

    }

    private ArrayList<Card> createCards() {
        ArrayList<Card> cards = new ArrayList<>();

        // Create a Listing Card
        for (Listing l : myListings) {
            // TODO: Setup card layout here
            Card bookingCard = new Card(this, R.layout.row_listing_layout);
            bookingCard.setTitle(l.getListingTitle());
            cards.add(bookingCard);
        }
        Debug.printToast(cards.size() + "", getApplicationContext());
        return cards;
    }

    private void listeners(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("listings");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Listing listing = postSnapshot.getValue(Listing.class);
                    myListings.add(listing);
                }

                updateCardUi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    private void updateCardUi() {
        ArrayList<Card> ownedListingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, ownedListingCards);
        CardListView currentListingsListView = (CardListView) this.findViewById(R.id.listingsCardListView);
        if (currentListingsListView != null) {
            currentListingsListView.setAdapter(mCardArrayAdapter);
        }
    }
}
