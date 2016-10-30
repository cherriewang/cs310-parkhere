package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

/**
 * Created by glarencezhao on 10/29/16.
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private AccountHeader headerResult = null;
    private Drawer navDrawer = null;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    //Test markers
    private LatLng glarenceAPT = new LatLng(34.024652, -118.280782);

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(HomeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Setup Navigation Drawer
        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Testing Nav Drawer");
        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Glarence Zhao").withEmail("glarencez@gmail.com").withIcon(R.drawable.placeholder_prof_pic);
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();
        //Create the drawer
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home_drawer_item_string).withIdentifier(1), //withIcon(FontAwesome.Icon.faw_home) before withIdentifier
                        new PrimaryDrawerItem().withName(R.string.bookings_drawer_item_string).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.listings_drawer_item_string).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.create_listing_drawer_item_string).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.payment_drawer_item_string).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings_drawer_item_string).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.help_drawer_item_string).withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            //Home
                            if(drawerItem.getIdentifier() == 1){}
                            //List of Bookings
                            else if(drawerItem.getIdentifier() == 2){
                                Intent i = new Intent(HomeActivity.this, ListOfBookingsActivity.class);
                                startActivity(i);
                            }
                            //List of Listings
                            else if(drawerItem.getIdentifier() == 3){
                                Intent i = new Intent(HomeActivity.this, ListOfListingsActivity.class);
                                startActivity(i);
                            }
                            //Create Listing
                            else if(drawerItem.getIdentifier() == 4){
                                Intent i = new Intent(HomeActivity.this, CreateEditListingActivity.class);
                                startActivity(i);
                            }
                            //Payment
                            else if(drawerItem.getIdentifier() == 5){
                                Intent i = new Intent(HomeActivity.this, ListOfPaymentsActivity.class);
                                i.putExtra(ListingDetailActivity.SELECTING_PAYMENT, false);
                                startActivity(i);
                            }
                            //Settings
                            else if(drawerItem.getIdentifier() == 6){
                                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                                startActivity(i);
                            }
                            //Help
                            else if(drawerItem.getIdentifier() == 7){
                                Intent i = new Intent(HomeActivity.this, HelpActivity.class);
                                startActivity(i);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 5
        if (savedInstanceState == null) {
            navDrawer.setSelection(1, false);
        }

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        initialize();
        listeners();
    }

    private void initialize(){
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Set up Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(glarenceAPT).title("Shrine Habitat"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(glarenceAPT));
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        enableMyLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = navDrawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (navDrawer != null && navDrawer.isDrawerOpen()) {
            navDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void enableMyLocation(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            new TedPermission(this).setPermissionListener(permissionListener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        }
        else if(mMap != null){
            //Only after enabling location permissions
            mMap.setMyLocationEnabled(true);

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // Getting last location and zooming to that level w/o animation
            //if(mLastLocation == null)
                //Debug.printToast("Location is null", getApplicationContext());
            CameraPosition currentPosition = new CameraPosition.Builder()
                    //.target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                    .target(glarenceAPT)
                    .zoom(16) // this is the zoom level
                    .bearing(35)   // this is the rotation angle
                    .tilt(40)   // this is the degree of elevation
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPosition));
            mMap.animateCamera(CameraUpdateFactory.scrollBy(-100,-50));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Connected
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Not connected anymore
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Tried to connect but it failed
    }

    private void listeners(){

    }
}
