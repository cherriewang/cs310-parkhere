package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    private List<Review> myReviews;

    private Listing myListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ratings);
        myReviews = new ArrayList<>();
        Intent i = getIntent();
        if(i != null)
            myListing = (Listing) i.getSerializableExtra(ListingDetailActivity.MORE_REVIEWS_INTENT_KEY);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();
        ratingsListView = (ListView) findViewById(R.id.ratingsListView);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void listeners(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("listings").child(myListing.getListingTitle()).child("reviews");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myReviews.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Review review = postSnapshot.getValue(Review.class);
                    myReviews.add(review);
                }

                createCards();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void createCards() {
//        List<ParkingSpot> parkingSpots = myParkingSpots;
        mRatingAdapter = new RatingAdapter(getApplicationContext(), 0, (ArrayList<Review>) myReviews);
        ratingsListView.setAdapter(mRatingAdapter);
    }
}
