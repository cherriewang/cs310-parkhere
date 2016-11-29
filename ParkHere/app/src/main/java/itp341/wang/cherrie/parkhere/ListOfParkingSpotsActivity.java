package itp341.wang.cherrie.parkhere;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import itp341.wang.cherrie.parkhere.model.ParkingSpot;
import itp341.wang.cherrie.parkhere.model.User;

public class ListOfParkingSpotsActivity extends AppCompatActivity {
    private User myUser;
    private List<ParkingSpot> myParkingSpots;
    private ListView listingsListView;
    private ParkingSpotAdapter mParkingSpotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_parking_spots);
        myParkingSpots = new ArrayList<>();

        initialize();
        listeners();
    }

    private void initialize(){
        //getSupportActionBar().setTitle("Parking Spots"); TODO FIX NULL REFERENCE
        listingsListView = (ListView) findViewById(R.id.parkingSpotsListView);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void createCards() {
        List<ParkingSpot> parkingSpots = myParkingSpots;
        mParkingSpotAdapter = new ParkingSpotAdapter(getApplicationContext(), 0, (ArrayList<ParkingSpot>) parkingSpots, this);
        listingsListView.setAdapter(mParkingSpotAdapter);
    }

    private void listeners(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference refMyListings = myRef.child("users").child(myUser.getmNormalizedEmail()).child("mParkingSpots");

        refMyListings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myParkingSpots.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot parkingSpot = postSnapshot.getValue(ParkingSpot.class);
                    myParkingSpots.add(parkingSpot);
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
