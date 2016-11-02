package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_listings_actionbar_title));
//
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        //Owned listings
        ArrayList<Card> ownedListingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, ownedListingCards);
        CardListView currentListingsListView = (CardListView) this.findViewById(R.id.listingsCardListView);
        if (currentListingsListView != null){
            currentListingsListView.setAdapter(mCardArrayAdapter);
        }
    }

    private ArrayList<Card> createCards() {
        ArrayList<Card> cards = new ArrayList<>();

        // Create a Listing Card
        for (Listing l : myUser.getmListings()) {
            // TODO: Setup card layout here
            Card bookingCard = new Card(this, R.layout.row_listing_layout);
            bookingCard.setTitle(l.getListingTitle());
            cards.add(bookingCard);
        }
        Debug.printToast(cards.size() + "", getApplicationContext());
        return cards;
    }

    private void listeners(){

    }
}
