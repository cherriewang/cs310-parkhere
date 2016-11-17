package itp341.wang.cherrie.parkhere;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/29/16.
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener,
        com.borax12.materialdaterangepicker.time.TimePickerDialog.OnTimeSetListener, GoogleMap.OnInfoWindowClickListener,
        LocationListener {

    private AccountHeader headerResult = null;
    private static Drawer navDrawer = null;
    private FloatingSearchView mSearchView = null;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private User myUser;

    //Test markers
    //private LatLng glarenceAPT = new LatLng(34.024652, -118.280782);

    //Advanced dialog variables
    private int fromYear = 0;
    private int fromMonthOfYear = 0;
    private int fromDayOfMonth = 0;
    private int toYear = 0;
    private int toMonthOfYear = 0;
    private int toDayOfMonth = 0;
    private String fromHourString = "";
    private String fromMinuteString = "";
    private String toHourString = "";
    private String toMinuteString = "";
    private boolean shortTermParking = false;
    //Parking spot type booleans
    private boolean searchCovered = false;
    private boolean searchSUV = false;
    private boolean searchHandicapped = false;
    private boolean searchTandem = false;
    //Filter dialog variables
    private long fromPriceRange = 0;
    private long toPriceRange = 0;
    private boolean sortDistance = false;
    private boolean sortListingRating = false;
    private boolean sortOwnerRating = false;
    //LatLong search dialog variables
    private float latitude = 0;
    private float longitude = 0;
    //Dialogs
    private MaterialDialog advancedDialog;
    private MaterialDialog filtersDialog;
    private MaterialDialog searchDialog;

    //Location tracking variables
    private Location mLastLocation;
    private LatLng searchLatLng;

    private final float THREE_MILES = (float) 4828.03;

    private ArrayList<Listing> allListingsInFireBase = new ArrayList<>();
    private ArrayList<Listing> allListingsToDisplay = new ArrayList<>();
    private HashMap<Marker, Listing> markerListingHashmap = new HashMap<>();

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private int CREATE_LISTING_REQUEST_CODE = 2;

    public final static String USER_PROFILE_INTENT_KEY = "Sending user object to detail user profile activity";

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
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        //Setup Navigation Drawer
        final IProfile profile = new ProfileDrawerItem().withName(myUser.getmFirstName() + " " + myUser.getmLastName()).withEmail(myUser.getmEmail()).withIcon(R.drawable.placeholder_prof_pic);
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Intent i = new Intent(HomeActivity.this, UserProfileActivity.class);
                        i.putExtra(USER_PROFILE_INTENT_KEY, myUser);
                        startActivity(i);

                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();
        //Create the drawer
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Search").withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_search).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.home_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_home).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.bookings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_align_left).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.listings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_align_right).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.create_listing_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_pencil_square).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.payment_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_credit_card_alt).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(6),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_cog).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(7),
                        new SecondaryDrawerItem().withName(R.string.help_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_question).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(8)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            //Search
                            if (drawerItem.getIdentifier() == 1) {
                                try {
                                    if (myUser.isSeeker()) {
                                        Intent intent =
                                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                                        .build(HomeActivity.this);
                                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                                    } else {
                                        notSeekerDialog();
                                    }
                                } catch (GooglePlayServicesRepairableException e) {
                                } catch (GooglePlayServicesNotAvailableException e) {
                                }
                            }
                            //Home
                            if (drawerItem.getIdentifier() == 2) {
                            }
                            //List of Bookings
                            else if (drawerItem.getIdentifier() == 3) {
                                Intent i = new Intent(HomeActivity.this, ListOfBookingsActivity.class);
                                startActivity(i);
                            }
                            //List of Listings
                            else if (drawerItem.getIdentifier() == 4) {
                                Intent i = new Intent(HomeActivity.this, ListOfListingsActivity.class);
                                startActivity(i);
                            }
                            //Create Listing
                            else if (drawerItem.getIdentifier() == 5) {
                                // DIALOG BOX OPENS IF USER IS NOT AN OWNER
                                if (myUser.isOwner()) {
                                    Intent i = new Intent(HomeActivity.this, CreateEditListingActivity.class);
                                    startActivityForResult(i, CREATE_LISTING_REQUEST_CODE);
                                } else {
                                    showLocationDialog();
                                }
                            }
                            //Payment
                            else if (drawerItem.getIdentifier() == 6) {
                                Intent i = new Intent(HomeActivity.this, ListOfPaymentsActivity.class);
                                i.putExtra(ListingDetailActivity.SELECTING_PAYMENT, false);
                                startActivity(i);
                            }
                            //Settings
                            else if (drawerItem.getIdentifier() == 7) {
                                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                                startActivity(i);
                            }
                            //Help
                            else if (drawerItem.getIdentifier() == 8) {
                                Intent i = new Intent(HomeActivity.this, HelpActivity.class);
                                startActivity(i);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 2 for home
        if (savedInstanceState == null) {
            navDrawer.setSelection(2, false);
        }

        //Set up floating search bar
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchView.attachNavigationDrawerToMenuButton(navDrawer.getDrawerLayout());

        initialize();
        listeners();

        Log.e("HomeActivity", "Is it true: "+myUser.isRecentTransactionApproved());
        Log.e("HomeActivity", "Who is my User:  "+myUser.getmNormalizedEmail());
        // if a seeker has recently received a transaction for one of your listings
        if (myUser.isRecentTransactionApproved()){
            //Debug.printToast("ALERT: You have a recent completed transaction. Your account balance has been updated to reflect the change.", getApplicationContext());
            // set back to false...
            Log.e("HomeActivity", "We are approved!!!");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("users").child(myUser.getmNormalizedEmail()).child("recentTransactionApproved").setValue(false);
            transactionDialog();
        }
    }

    private void initialize() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }

        //Set up Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference refListings = myRef.child("listings");
        refListings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allListingsInFireBase.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Listing listing = postSnapshot.getValue(Listing.class);
                    allListingsInFireBase.add(listing);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        initializeDateAndTime();
    }

    private void initializeDateAndTime() {
        final Calendar c = Calendar.getInstance();

        //Both today
        fromYear = c.get(Calendar.YEAR);
        fromMonthOfYear = c.get(Calendar.MONTH) + 1; //indexed from 0
        fromDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        toYear = fromYear;
        toMonthOfYear = fromMonthOfYear;
        toDayOfMonth = fromDayOfMonth;

        //Default one hour apart
        fromHourString = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        fromMinuteString = Integer.toString(c.get(Calendar.MINUTE));
        //account for 23 to 00
        toHourString = Integer.toString(c.get(Calendar.HOUR_OF_DAY) + 1);
        toMinuteString = fromMinuteString;
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_message));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myUser.setOwner(true);
                        Debug.printToast("Signed up as Owner", getApplicationContext());

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("users").child(myUser.getmNormalizedEmail()).setValue(myUser);
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void notSeekerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.dialog_title_seeker));
        builder.setMessage(getString(R.string.dialog_message_seeker));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myUser.setSeeker(true);
                        Debug.printToast("Signed up as Seeker", getApplicationContext());

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("users").child(myUser.getmNormalizedEmail()).setValue(myUser);
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void transactionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.MyDialogTheme);
        builder.setTitle("Transaction Approved");
        builder.setMessage("A recent listing was just completed and the parking Seeker has approved the transaction. Your account balance has been updated to reflect the change.");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.setPadding(0, 180, 0, 0);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();
    }

    private Marker addListingMarker(LatLng latLng, String listingTitle) {
        //Instead of title, put price for marker title
        return mMap.addMarker(new MarkerOptions().position(latLng).title(listingTitle)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
    }

    private void addSearchMarker(LatLng latLng, String listingTitle) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title(listingTitle)
                .icon(BitmapDescriptorFactory.fromBitmap(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_map_pin).
                        sizeDp(30).color(getResources().getColor(R.color.colorAccent)).toBitmap())));
        //Update to search marker
        CameraPosition currentPosition = new CameraPosition.Builder()
                //.target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                .target(latLng)
                .zoom(16) // this is the zoom level
                .bearing(35)   // this is the rotation angle
                .tilt(40)   // this is the degree of elevation
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPosition));
        mMap.animateCamera(CameraUpdateFactory.scrollBy(-100, -50));

        populateListings(latLng);
    }

    private void clearMarkers() {
        //clear everything and populate
        allListingsToDisplay.clear();
        for (Marker marker : markerListingHashmap.keySet())
            marker.remove();
        markerListingHashmap.clear();
    }

    private void addMarkers() {
        //add markers
        for (Listing listing : allListingsToDisplay) {
            LatLng listingLatLng = new LatLng(listing.getLatitude(), listing.getLongitude());
            Marker listingMarker = addListingMarker(listingLatLng, listing.getListingTitle());
            markerListingHashmap.put(listingMarker, listing);
        }
    }

    private void populateListings(LatLng searchMarker) {
        if (searchMarker != null) {
            clearMarkers();

            Location searchLocation = new Location("Search marker");
            searchLocation.setLatitude(searchMarker.latitude);
            searchLocation.setLongitude(searchMarker.longitude);

            //find listings to display
            for (Listing listing : allListingsInFireBase) {
                Location listingLocation = new Location("Listing Location");
                listingLocation.setLatitude(listing.getLatitude());
                listingLocation.setLongitude(listing.getLongitude());

                float distance = searchLocation.distanceTo(listingLocation);

                //Returned results must be less than three miles
                if (distance <= THREE_MILES) {
                    allListingsToDisplay.add(listing);
                }
            }

            advancedSearch();

            addMarkers();
        }
    }

    private void advancedSearch() {
        for (Iterator<Listing> iterator = allListingsToDisplay.iterator(); iterator.hasNext(); ) {
            Listing listingToCheck = iterator.next();

            //Short term date and time check

            //Parking spot types check
            if (searchSUV) {
                if (!listingToCheck.isSuv()) {
                    iterator.remove();
                    continue;
                }
            }
            if (searchTandem) {
                if (!listingToCheck.isTandem()) {
                    iterator.remove();
                    continue;
                }
            }
            if (searchCovered) {
                if (!listingToCheck.isCovered()) {
                    iterator.remove();
                    continue;
                }
            }
            if (searchHandicapped) {
                if (!listingToCheck.isHandicapped()) {
                    iterator.remove();
                    continue;
                }
            }
        }
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
        }
    }

    public static void setNavDrawerToHome() {
        //Back to home activity so highlight home nav drawer item
        navDrawer.setSelection(2, false);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void enableMyLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Not connected anymore
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Tried to connect but it failed
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null){
            Debug.printToast("Can't get current location!", getApplicationContext());
        } else {
                mLastLocation = location;
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.animateCamera(update);
        }
    }

    private void listeners(){
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                boolean wrapInScrollView = false;
                if(id == R.id.action_advanced){
                    advancedDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_advanced_layout,
                            wrapInScrollView).positiveText("Set").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            populateListings(searchLatLng);
                        }
                    }).show();
                    dialogListeners(id);
                }
                if(id == R.id.action_filters){
                    filtersDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_filters_layout,
                            wrapInScrollView).positiveText("Display Results").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            final ArrayList<Listing> listingsToSort;

                            double toPrice = ((double)toPriceRange)/100;
                            double fromPrice = ((double)fromPriceRange)/100;

                            if(toPrice > fromPrice){
                                listingsToSort = new ArrayList<>();
                                for(Listing listing : allListingsInFireBase){
                                    if(listing.getPrice() >= fromPrice && listing.getPrice() <= toPrice){
                                        listingsToSort.add(listing);
                                    }
                                }
                                //Price high to low
                                Collections.sort(listingsToSort, new Comparator<Listing>() {
                                    @Override
                                    public int compare(Listing listing1, Listing listing2) {
                                        if(listing1.getPrice() < listing2.getPrice())
                                            return -1;
                                        if(listing1.getPrice() > listing2.getPrice())
                                            return 1;

                                        return 0;
                                    }
                                });
                            }
                            else{
                                listingsToSort = allListingsInFireBase;
                            }

                            if(sortDistance){
                                Debug.printToast("Sorting by distance", getApplicationContext());
                                Collections.sort(listingsToSort, new Comparator<Listing>() {
                                    @Override
                                    public int compare(Listing listing1, Listing listing2) {
                                        if(mLastLocation != null){
                                            Location listing1Location = new Location("Listing 1 location");
                                            listing1Location.setLatitude(listing1.getLatitude());
                                            listing1Location.setLongitude(listing1.getLongitude());

                                            Location listing2Location = new Location("Listing 2 location");
                                            listing2Location.setLatitude(listing2.getLatitude());
                                            listing2Location.setLongitude(listing2.getLongitude());

                                            float listing1Distance = mLastLocation.distanceTo(listing1Location);
                                            float listing2Distance = mLastLocation.distanceTo(listing2Location);

                                            if(listing1Distance < listing2Distance)
                                                return -1;
                                            if(listing1Distance > listing2Distance)
                                                return 1;
                                        }

                                        return 0;
                                    }
                                });
                            }

                            //Can't test since doing test values from Firebase doens't work, all listing ratings are 0 (reviews are null)
                            if(sortListingRating){
                                Debug.printToast("Sorting by listing rating", getApplicationContext());
                                Collections.sort(listingsToSort, new Comparator<Listing>() {
                                    @Override
                                    public int compare(Listing listing1, Listing listing2) {
                                        if(listing1.getAverageRating() > listing2.getAverageRating())
                                            return 1;
                                        if(listing1.getAverageRating() < listing2.getAverageRating())
                                            return -1;

                                        return 0;
                                    }
                                });
                            }

                            /*for(Listing listing : listingsToSort){
                                System.out.println("Listing " + listing.getListingTitle() + " has a rating of  " + listing.getAverageRating());
                            }*/

                            if(sortOwnerRating){
                                Debug.printToast("Sorting by owner rating", getApplicationContext());
                                Collections.sort(listingsToSort, new Comparator<Listing>() {
                                    @Override
                                    public int compare(Listing listing1, Listing listing2) {
                                        if(listing1.getOwner().getAverageRating() > listing2.getOwner().getAverageRating())
                                            return 1;
                                        if(listing1.getOwner().getAverageRating() < listing2.getOwner().getAverageRating())
                                            return -1;
                                        return 0;
                                    }
                                });
                            }

                            //get array of listing string titles for list dialog
                            ArrayList<String> listingTitles = new ArrayList<>();
                            for(Listing listing : listingsToSort){
                                listingTitles.add(listing.getListingTitle());
                            }

                            new MaterialDialog.Builder(HomeActivity.this).title("Sorted Listings")
                                        .items(listingTitles).itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        Listing listingSelected = listingsToSort.get(position);

                                        Intent i = new Intent(HomeActivity.this, ListingDetailActivity.class);
                                        i.putExtra(ListingAdapter.LISTING_DETAIL_INTENT_KEY, listingSelected);
                                        startActivity(i);
                                    }
                                }).show();
                        }
                    }).show();
                    dialogListeners(id);
                }
                if(id == R.id.action_latlong_search){

                    if(myUser.isSeeker()){
                        searchDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_latlong_search_layout,
                                wrapInScrollView).positiveText("Search").onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                searchLatLng = new LatLng(latitude, longitude);
                                addSearchMarker(searchLatLng, "Lat Long Search");
                                Debug.printToast("Latitude is " + latitude, getApplicationContext());
                                Debug.printToast("Longitude is " + longitude, getApplicationContext());
                            }
                        }).show();
                    } else {
                        // Display a Dialog that asks if they would like to sign up as a Seeker
                        notSeekerDialog();

                    }
                    dialogListeners(id);

                }
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if(mLastLocation != null){
                    LatLngBounds bounds = toBounds(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 8);
                }
                /*PendingResult<AutocompletePredictionBuffer> result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, newQuery, bounds,
                                                                                new AutocompleteFilter.Builder()
                                                                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                                                                                .build());*/
            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {}

            @Override
            public void onSearchAction(String currentQuery) {
                Debug.printToast(currentQuery, getApplicationContext());
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Listing listingSelected = markerListingHashmap.get(marker);

        Intent i = new Intent(HomeActivity.this, ListingDetailActivity.class);
        i.putExtra(ListingAdapter.LISTING_DETAIL_INTENT_KEY, listingSelected);
        startActivity(i);
    }

    //for calculating bounds of user's location for autocompletion predictions
    public LatLngBounds toBounds(LatLng center, double radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    private Button selectTimeButton;

    private void dialogListeners(int id){

        if(id == R.id.action_advanced){
            final View view = advancedDialog.getCustomView();
            //populate time
            TextView fromDateTextView = (TextView)view.findViewById(R.id.fromDateTextView);
            fromDateTextView.setText(fromMonthOfYear + "/" + fromDayOfMonth + "/" + fromYear);
            TextView toDateTextView = (TextView)view.findViewById(R.id.toDateTextView);
            toDateTextView.setText(toMonthOfYear + "/" + toDayOfMonth + "/" + toYear);
            TextView fromTimeTextView = (TextView)view.findViewById(R.id.fromTimeTextView);
            fromTimeTextView.setText(fromHourString + ":" + fromMinuteString);
            TextView toTimeTextView = (TextView)view.findViewById(R.id.toTimeTextView);
            toTimeTextView.setText(toHourString + ":" + toMinuteString);

            Button selectDateButton = (Button)view.findViewById(R.id.dateButton);
            selectDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    com.borax12.materialdaterangepicker.date.DatePickerDialog datePickerDialog = com.borax12.materialdaterangepicker.date.DatePickerDialog
                            .newInstance(
                            HomeActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.show(getFragmentManager(), "Select the Dates");
                }
            });
            selectTimeButton = (Button)view.findViewById(R.id.timeButton);
            selectTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    com.borax12.materialdaterangepicker.time.TimePickerDialog timePickerDialog = com.borax12.materialdaterangepicker.time.TimePickerDialog
                            .newInstance(
                            HomeActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            false
                    );
                    timePickerDialog.setEndTime(Integer.parseInt(toHourString), Integer.parseInt(toMinuteString));
                    timePickerDialog.show(getFragmentManager(), "Select the Time");
                }
            });
            CheckBox shortTermParkingCheckBox = (CheckBox)view.findViewById(R.id.shortTermParkingCheckBox);
            shortTermParkingCheckBox.setChecked(shortTermParking);
            if(!shortTermParkingCheckBox.isChecked()) {
                selectTimeButton.setEnabled(false);
            }
            else
                selectTimeButton.setEnabled(true);
            shortTermParkingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    shortTermParking = isChecked;
                    if(isChecked) {
                        selectTimeButton.setEnabled(false);
                    }
                    else
                        selectTimeButton.setEnabled(true);
                }
            });
            CheckBox coveredCheckBox = (CheckBox)view.findViewById(R.id.coveredCheckBox);
            coveredCheckBox.setChecked(searchCovered);
            coveredCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    searchCovered = isChecked;
                }
            });
            CheckBox suvCheckBox = (CheckBox)view.findViewById(R.id.suvCheckBox);
            suvCheckBox.setChecked(searchSUV);
            suvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    searchSUV = isChecked;
                }
            });
            CheckBox handicappedCheckBox = (CheckBox)view.findViewById(R.id.handicappedCheckBox);
            handicappedCheckBox.setChecked(searchHandicapped);
            handicappedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    searchHandicapped = isChecked;
                }
            });
            CheckBox tandemCheckBox = (CheckBox)view.findViewById(R.id.tandemCheckBox);
            tandemCheckBox.setChecked(searchTandem);
            tandemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    searchTandem = isChecked;
                }
            });
        }
        if(id == R.id.action_filters){
            final View view = filtersDialog.getCustomView();
            final CurrencyEditText fromPriceEditText = (CurrencyEditText)view.findViewById(R.id.fromPriceEditText);
            fromPriceEditText.setText(fromPriceRange + "");
            fromPriceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(count != 0)
                        fromPriceRange = fromPriceEditText.getRawValue();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            final CurrencyEditText toPriceEditText = (CurrencyEditText) view.findViewById(R.id.toPriceEditText);
            toPriceEditText.setText(toPriceRange + "");
            toPriceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(count != 0)
                        toPriceRange = toPriceEditText.getRawValue();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            CheckBox sortDistanceCheckBox = (CheckBox) view.findViewById(R.id.sortDistanceCheckBox);
            sortDistanceCheckBox.setChecked(sortDistance);
            sortDistanceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sortDistance = isChecked;
                }
            });
            CheckBox sortListingCheckBox = (CheckBox) view.findViewById(R.id.sortListingRatingCheckBox);
            sortListingCheckBox.setChecked(sortListingRating);
            sortListingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sortListingRating = isChecked;
                }
            });
            CheckBox sortOwnerCheckBox = (CheckBox) view.findViewById(R.id.sortOwnerRatingCheckBox);
            sortOwnerCheckBox.setChecked(sortOwnerRating);
            sortOwnerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sortOwnerRating = isChecked;
                }
            });
        }
        if(id == R.id.action_latlong_search){
            final View view = searchDialog.getCustomView();
            EditText latitudeEditText = (EditText)view.findViewById(R.id.latitudeEditText);
            latitudeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String latitudeString = s.toString();
                    if(latitudeString.charAt(0) == '-'){
                        if(latitudeString.length() > 1) {
                            latitude = Float.parseFloat(latitudeString.substring(1));
                            latitude *= -1;
                        }
                    }
                    else
                        latitude = Float.parseFloat(latitudeString);
                }
            });
            EditText longitudeEditText = (EditText)view.findViewById(R.id.longitudeEditText);
            longitudeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String longitudeString = s.toString();
                    if(longitudeString.charAt(0) == '-'){
                        if(longitudeString.length() > 1) {
                            longitude = Float.parseFloat(longitudeString.substring(1));
                            longitude *= -1;
                        }
                    }
                    else
                        longitude = Float.parseFloat(longitudeString);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                searchLatLng = place.getLatLng();
                addSearchMarker(searchLatLng, place.getName().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            setNavDrawerToHome();
        }
        if (requestCode == CREATE_LISTING_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Debug.printToast("Added listing!", getApplicationContext());
                setNavDrawerToHome();
            }
        }
    }

    @Override
    public void onDateSet(com.borax12.materialdaterangepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        View dialogView = advancedDialog.getCustomView();

        fromYear = year;
        fromMonthOfYear = ++monthOfYear;
        fromDayOfMonth = dayOfMonth;
        toYear = yearEnd;
        toMonthOfYear = ++monthOfYearEnd;
        toDayOfMonth = dayOfMonthEnd;

        TextView fromTimeTextView = (TextView) dialogView.findViewById(R.id.fromDateTextView);
        fromTimeTextView.setText(fromMonthOfYear + "/" + fromDayOfMonth + "/" + fromYear);
        TextView toTimeTextView = (TextView) dialogView.findViewById(R.id.toDateTextView);
        toTimeTextView.setText(toMonthOfYear + "/" + toDayOfMonth + "/" + toYear);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        View dialogView = advancedDialog.getCustomView();

        fromHourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        fromMinuteString = minute < 10 ? "0"+minute : ""+minute;
        toHourString = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        toMinuteString = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;

        TextView fromDateTextView = (TextView) dialogView.findViewById(R.id.fromTimeTextView);
        fromDateTextView.setText(fromHourString + ":" + fromMinuteString);
        TextView toDateTextView = (TextView) dialogView.findViewById(R.id.toTimeTextView);
        toDateTextView.setText(toHourString + ":" + toMinuteString);
    }
}
