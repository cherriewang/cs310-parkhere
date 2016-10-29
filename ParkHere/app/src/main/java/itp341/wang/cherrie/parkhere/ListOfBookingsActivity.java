package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class ListOfBookingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bookings);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_bookings_actionbar_title));

        //Current listings
        ArrayList<Card> currentListingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, currentListingCards);
        CardListView currentListingsListView = (CardListView) this.findViewById(R.id.listingsListView);
        CustomSectionAdapter sectionAdapter = new CustomSectionAdapter(this, mCardArrayAdapter);
        sectionAdapter.addCardSection(new CardSection(0, "Current"));
        sectionAdapter.addCardSection(new CardSection(currentListingCards.size(), "Past"));
        //Past listings
        ArrayList<Card> pastListingCards = createCards();
        mCardArrayAdapter.addAll(pastListingCards);
        sectionAdapter.notifyDataSetChanged();
        if (currentListingsListView!=null){
            currentListingsListView.setExternalAdapter(sectionAdapter, mCardArrayAdapter);
        }
    }

    private ArrayList<Card> createCards(){
        ArrayList<Card> cards = new ArrayList<Card>();

        // Create a Listing Card
        Card bookingCard = new Card(this, R.layout.booking_card);
        //Set listing title
        bookingCard.setTitle("Listing Title");
        // Add card to array
        cards.add(bookingCard);

        Card testCard = new Card(this, R.layout.booking_card);
        testCard.setTitle("Test");
        cards.add(testCard);

        return cards;
    }

    private void listeners(){

    }
}
