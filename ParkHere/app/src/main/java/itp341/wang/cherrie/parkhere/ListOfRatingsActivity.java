package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by cherriewang on 10/28/16.
 */

public class ListOfRatingsActivity extends AppCompatActivity {

    private User myUser;
    private ListView ratingsListView;
    private RatingAdapter mRatingAdapter;
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
        getSupportActionBar().setTitle(myListing.getListingTitle());
        ratingsListView = (ListView) findViewById(R.id.ratingsListView);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        createCards();
    }

    private void listeners(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("listings").child(myListing.getListingTitle());
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Listing listing = postSnapshot.getValue(Listing.class);
//                    myListings.add(listing);
//                }
//
//                updateCardUi();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }

    private void createCards() {
        mRatingAdapter = new RatingAdapter(getApplicationContext(), 0, (ArrayList<Review>) myListing.getReviews());
        ratingsListView.setAdapter(mRatingAdapter);
    }

}
