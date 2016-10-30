package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;

public class HomeActivity extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer navDrawer = null;

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

    private void listeners(){

    }
}
