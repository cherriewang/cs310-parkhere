package itp341.wang.cherrie.parkhere;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import itp341.wang.cherrie.parkhere.model.User;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by glarencezhao on 10/29/16.
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private AccountHeader headerResult = null;
    private Drawer navDrawer = null;
    private FloatingSearchView mSearchView = null;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private User myUser;

    //Test markers
    private LatLng glarenceAPT = new LatLng(34.024652, -118.280782);

    //Advanced dialog variables
    //selected Date
    //selected Time
    private boolean isCovered = false;
    private boolean isSUV = false;
    private boolean isHandicapped = false;
    private boolean isTandem = false;
    //Filter dialog variables
    private long fromPriceRange = 0;
    private long toPriceRange = 0;
    private int distance = 0;
    private float minListingRating = 0;
    private float minOwnerRating = 0;
    //LatLong search dialog variables
    private float latitude = 0;
    private float longitude = 0;


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
        // Create a few sample profile
        //final IProfile profile = new ProfileDrawerItem().withName(myUser.getmFirstName() + " " + myUser.getmLastName()).withEmail(myUser.getmEmail()).withIcon(R.drawable.placeholder_prof_pic);
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabledForSingleProfile(false)
                //.addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        //Create the drawer
        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_home).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.bookings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_align_left).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.listings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_align_right).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.create_listing_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_pencil_square).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.payment_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_credit_card_alt).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_cog).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.help_drawer_item_string).withIcon(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_question).sizeDp(24).color(getResources().getColor(R.color.colorPrimary))).withIdentifier(7)
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
                                // DIALOG BOX OPENS IF USER IS NOT AN OWNER
                                if (myUser.isOwner()) {
                                    Intent i = new Intent(HomeActivity.this, CreateEditListingActivity.class);
                                    startActivity(i);
                                }
                                else {
                                    showLocationDialog();
                                }
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

        //Set up floating search bar
        mSearchView = (FloatingSearchView)findViewById(R.id.floating_search_view);
        mSearchView.attachNavigationDrawerToMenuButton(navDrawer.getDrawerLayout());

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
                        Debug.printToast("Signed up as owner", getApplicationContext());

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Instead of title, put price for marker title
        // Create an Icon object for the marker to use
        mMap.addMarker(new MarkerOptions().position(glarenceAPT).title("Shrine Habitat")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
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
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                boolean wrapInScrollView = false;
                if(id == R.id.action_advanced){
                    MaterialDialog advancedDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_advanced_layout,
                            wrapInScrollView).positiveText("Set").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO
                        }
                    }).show();
                    dialogListeners(advancedDialog, id);
                }
                if(id == R.id.action_filters){
                    MaterialDialog filtersDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_filters_layout,
                            wrapInScrollView).positiveText("Set").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO
                        }
                    }).show();
                    dialogListeners(filtersDialog, id);
                }
                if(id == R.id.action_latlong_search){
                    MaterialDialog searchDialog = new MaterialDialog.Builder(HomeActivity.this).customView(R.layout.dialog_latlong_search_layout,
                            wrapInScrollView).positiveText("Search").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO
                        }
                    }).show();
                    dialogListeners(searchDialog, id);

                }
            }
        });
    }

    private void dialogListeners(MaterialDialog dialog, int id){
        View view = dialog.getCustomView();
        if(id == R.id.action_advanced){
            Button selectDateButton = (Button)view.findViewById(R.id.dateButton);
            selectDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Button selectTimeButton = (Button)view.findViewById(R.id.timeButton);
            selectTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            CheckBox coveredCheckBox = (CheckBox)view.findViewById(R.id.coveredCheckBox);
            coveredCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            CheckBox suvCheckBox = (CheckBox)view.findViewById(R.id.suvCheckBox);
            suvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            CheckBox handicappedCheckBox = (CheckBox)view.findViewById(R.id.handicappedCheckBox);
            handicappedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            CheckBox tandemCheckBox = (CheckBox)view.findViewById(R.id.tandemCheckBox);
            tandemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
        if(id == R.id.action_filters){
            EditText fromPriceEditText = (EditText)view.findViewById(R.id.fromPriceEditText);
            fromPriceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //save price
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            EditText toPriceEditText = (EditText)view.findViewById(R.id.toPriceEditText);
            fromPriceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //save price
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            SeekBar distanceSeekBar = (SeekBar)view.findViewById(R.id.distanceSeekBar);
            distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //save distance
                }
            });
            MaterialRatingBar minListingRatingBar = (MaterialRatingBar)view.findViewById(R.id.minListingRatingBar);
            minListingRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    //save rating
                }
            });
            MaterialRatingBar minOwnerRatingBar = (MaterialRatingBar)view.findViewById(R.id.minOwnerRatingBar);
            minOwnerRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    //save rating
                }
            });
        }
        if(id == R.id.action_latlong_search){
            EditText latitudeEditText = (EditText)view.findViewById(R.id.latitudeEditText);
            latitudeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //save latitude
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            EditText longitudeEditText = (EditText)view.findViewById(R.id.longitudeEditText);
            longitudeEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //save longitude
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }
}
