package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by glarencezhao on 10/28/16.
 */

public class ListOfListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_listings_actionbar_title));

        //Owned listings
        ArrayList<Card> ownedListingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, ownedListingCards);
        CardListView currentListingsListView = (CardListView) this.findViewById(R.id.listingsListView);
        if (currentListingsListView!=null){
            currentListingsListView.setAdapter(mCardArrayAdapter);
        }
    }

    private ArrayList<Card> createCards(){
        ArrayList<Card> cards = new ArrayList<Card>();

        // Create a Listing Card
        Card bookingCard = new Card(this, R.layout.row_listing_layout);
        //Set listing title
        bookingCard.setTitle("Listing Title");
        // Add card to array
        cards.add(bookingCard);

        Card testCard = new Card(this, R.layout.row_listing_layout);
        testCard.setTitle("Test");
        cards.add(testCard);

        return cards;
    }

    private void listeners(){

    }
}
