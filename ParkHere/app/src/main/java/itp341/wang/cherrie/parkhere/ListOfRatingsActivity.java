package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;

/**
 * Created by cherriewang on 10/28/16.
 */

public class ListOfRatingsActivity extends AppCompatActivity {
    private Listing myListing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ratings);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                myListing = null;
            } else {
                myListing = (Listing)extras.getSerializable("LISTING");
            }
        } else {
            myListing = (Listing) savedInstanceState.getSerializable("LISTING");
        }

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_ratings_actionbar_title));

        ArrayList<Card> ownedListingCards = createCards();
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, ownedListingCards);
        CardListView currentListingsListView = (CardListView) this.findViewById(R.id.ratingsListView);
        if (currentListingsListView!=null){
            currentListingsListView.setAdapter(mCardArrayAdapter);
        }
    }

    private ArrayList<Card> createCards(){
        ArrayList<Card> cards = new ArrayList<>();

        // Create a Listing Card
        for (Review r : myListing.getReviews()) {
            // TODO: Setup card layout here
            Card reviewCard = new Card(this, R.layout.row_listing_layout);
            reviewCard.setTitle(r.getReviewText());
            cards.add(reviewCard);
        }

        Debug.printToast(cards.size() + "", getApplicationContext());
        return cards;
    }

    private void listeners(){

    }
}
