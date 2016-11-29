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
import java.util.List;

import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/28/16.
 * View All Listings not just yours
 */

public class ListOfListingsActivity extends AppCompatActivity {
    private User myUser;
    private List<Listing> myListings;
    private ListView listingsListView;
    private ListingAdapter mListingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_listings);
        myListings = new ArrayList<>();

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().setTitle(getResources().getString(R.string.list_of_listings_actionbar_title));
        listingsListView = (ListView) findViewById(R.id.listingsListView);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void createCards() {
        List<Listing> currentListings = myListings;
        mListingAdapter = new ListingAdapter(getApplicationContext(), 0, (ArrayList<Listing>) currentListings, this);
        listingsListView.setAdapter(mListingAdapter);
    }

    private void listeners(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference refMyListings = myRef.child("users").child(myUser.getmNormalizedEmail()).child("mListings");

        refMyListings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myListings.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Listing listing = postSnapshot.getValue(Listing.class);
                    myListings.add(listing);
                }

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
