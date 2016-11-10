package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/24/16.
 */

public class CreateEditListingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private ImageView listingImageView;
    private EditText listingTitleEditText;
    private CheckBox handicappedCheckBox;
    private CheckBox tandemCheckBox;
    private CheckBox suvCheckBox;
    private CheckBox coveredCheckBox;
    private TextView locationTextView;
    private EditText aboutEditText;
    private CurrencyEditText priceEditText;
    private Button createListingButton;
    private CheckBox mondayCheckBox;
    private CheckBox tuesdayCheckBox;
    private CheckBox wednesdayCheckBox;
    private CheckBox thursdayCheckBox;
    private CheckBox fridayCheckBox;
    private CheckBox saturdayCheckBox;
    private CheckBox sundayCheckBox;
    private Button timeButton;
    private TextView fromTimeTextView;
    private TextView toTimeTextView;

    private String listingTitle = "";
    private String location = "";
    private String about = "";
    private double price = 0;
    private boolean isTandem = false;
    private boolean isHandicapped = false;
    private boolean isSUV = false;
    private boolean isCovered = false;
    private boolean isMonday = false;
    private boolean isTuesday = false;
    private boolean isWednesday = false;
    private boolean isThursday = false;
    private boolean isFriday = false;
    private boolean isSaturday = false;
    private boolean isSunday = false;
    private String fromHourString = "";
    private String fromMinuteString = "";
    private String toHourString = "";
    private String toMinuteString = "";

    private double latitude = 0;
    private double longitude = 0;

    private User myUser;

    private Listing myListing;

    public final static int CREATE_EDIT_REQUEST_CODE = 0;
    int PLACE_PICKER_REQUEST = 2;   //request code for google place picker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_listing);

        Intent i = getIntent();
        if(i.hasExtra(ListingAdapter.LISTING_EDIT_INTENT_KEY))
            myListing = (Listing)i.getSerializableExtra(ListingAdapter.LISTING_EDIT_INTENT_KEY);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        listingImageView = (ImageView) findViewById(R.id.listingImageView);
        listingTitleEditText = (EditText) findViewById(R.id.listingTitleEditText);
        handicappedCheckBox = (CheckBox) findViewById(R.id.handicappedCheckBox);
        tandemCheckBox = (CheckBox) findViewById(R.id.tandemCheckBox);
        suvCheckBox = (CheckBox) findViewById(R.id.suvCheckBox);
        coveredCheckBox = (CheckBox) findViewById(R.id.coveredCheckBox);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        aboutEditText = (EditText) findViewById(R.id.aboutEditText);
        priceEditText = (CurrencyEditText) findViewById(R.id.priceEditText);
        createListingButton = (Button) findViewById(R.id.createListingButton);
        mondayCheckBox = (CheckBox) findViewById(R.id.mondayCheckBox);
        tuesdayCheckBox = (CheckBox) findViewById(R.id.tuesdayCheckBox);
        wednesdayCheckBox = (CheckBox) findViewById(R.id.wednesdayCheckBox);
        thursdayCheckBox = (CheckBox) findViewById(R.id.thursdayCheckBox);
        fridayCheckBox = (CheckBox) findViewById(R.id.fridayCheckBox);
        saturdayCheckBox = (CheckBox) findViewById(R.id.saturdayCheckBox);
        sundayCheckBox = (CheckBox) findViewById(R.id.sundayCheckBox);
        timeButton = (Button) findViewById(R.id.timeButton);
        fromTimeTextView = (TextView) findViewById(R.id.fromTimeTextView);
        toTimeTextView = (TextView) findViewById(R.id.toTimeTextView);

        populate();

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    //Used to fill up info if editing a listing
    private void populate(){
        if (myListing != null) {
            Debug.printToast("Listing will be populated!", getApplicationContext());
            listingTitle = myListing.getListingTitle();
            location = myListing.getLocation();
            about = myListing.getAbout();
            price = myListing.getPrice();
            isTandem = myListing.isTandem();
            isHandicapped = myListing.isHandicapped();
            isSUV = myListing.isSuv();
            isCovered = myListing.isCovered();
            isMonday = myListing.isMonday();
            isTuesday = myListing.isTuesday();
            isWednesday = myListing.isWednesday();
            isThursday = myListing.isThursday();
            isFriday = myListing.isFriday();
            isSaturday = myListing.isSaturday();
            isSunday = myListing.isSunday();
            fromHourString = myListing.getFromHourString();
            fromMinuteString = myListing.getFromMinuteString();
            toHourString = myListing.getToHourString();
            toMinuteString = myListing.getToMinuteString();

            listingTitleEditText.setText(listingTitle);
            locationTextView.setText(location);
            aboutEditText.setText(about);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            priceEditText.setText(decimalFormat.format(price));
            tandemCheckBox.setChecked(isTandem);
            handicappedCheckBox.setChecked(isHandicapped);
            suvCheckBox.setChecked(isSUV);
            coveredCheckBox.setChecked(isCovered);
            mondayCheckBox.setChecked(isMonday);
            tuesdayCheckBox.setChecked(isTuesday);
            wednesdayCheckBox.setChecked(isWednesday);
            thursdayCheckBox.setChecked(isThursday);
            fridayCheckBox.setChecked(isFriday);
            saturdayCheckBox.setChecked(isSaturday);
            sundayCheckBox.setChecked(isSunday);
            fromTimeTextView.setText(fromHourString + ":" + fromHourString);
            toTimeTextView.setText(toHourString + ":" + toMinuteString);
        }
        else
            myListing = new Listing();
    }

    private void listeners(){
        listingImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())  {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        // argb
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        view.getDrawable().clearColorFilter();
                        view.invalidate();

                        //Select Image
                        Intent intent = new Intent(CreateEditListingActivity.this, ImagePickerActivity.class);

                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, new ArrayList<Image>());
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Albums");
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Select an Image");
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

                        startActivityForResult(intent, CREATE_EDIT_REQUEST_CODE);

                        break;
                    }
                }
                return true;
            }
        });
        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listingTitle.isEmpty() && !location.isEmpty() && !about.isEmpty() && !isPriceEditTextEmpty()){
                    // getting all values from the input fields
                    price = priceEditText.getRawValue()/100;
                    isTandem = tandemCheckBox.isChecked();
                    isHandicapped = handicappedCheckBox.isChecked();
                    isSUV = suvCheckBox.isChecked();
                    isCovered = coveredCheckBox.isChecked();
                    isMonday = mondayCheckBox.isChecked();
                    isTuesday = tuesdayCheckBox.isChecked();
                    isWednesday = wednesdayCheckBox.isChecked();
                    isThursday = thursdayCheckBox.isChecked();
                    isFriday = fridayCheckBox.isChecked();
                    isSaturday = saturdayCheckBox.isChecked();
                    isSunday = sundayCheckBox.isChecked();

                    // CREATE LISTING OBJECT
                    myListing.setListingTitle(listingTitle);
                    myListing.setListingOwner(myUser.getmFirstName()); // should have a global user here
                    //Setting user object for owner
                    //myListing.setOwner(myUser);
                    myListing.setLocation(location);
                    myListing.setAbout(about);
                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    myListing.setPrice(Double.parseDouble(decimalFormat.format(price)));
                    //Setting listing categories
                    myListing.setTandem(isTandem);
                    myListing.setHandicapped(isHandicapped);
                    myListing.setSuv(isSUV);
                    myListing.setCovered(isCovered);
                    //Setting listing availibilities
                    myListing.setSunday(isSunday);
                    myListing.setMonday(isMonday);
                    myListing.setTuesday(isTuesday);
                    myListing.setWednesday(isTuesday);
                    myListing.setThursday(isThursday);
                    myListing.setFriday(isFriday);
                    myListing.setSaturday(isSaturday);
                    myListing.setFromHourString(fromHourString);
                    myListing.setFromMinuteString(fromMinuteString);
                    myListing.setToHourString(toHourString);
                    myListing.setToMinuteString(toMinuteString);
                    //Setting Lat Lng for markers
                    myListing.setLatitude(latitude);
                    myListing.setLongitude(longitude);
                    myUser.appendListing(myListing);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    myRef.child("listings").child(listingTitle).setValue(myListing);
                    myRef.child("users").child(myUser.getmNormalizedEmail()).child("mListings").child(listingTitle).setValue(myListing);

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.borax12.materialdaterangepicker.time.TimePickerDialog timePickerDialog = com.borax12.materialdaterangepicker.time.TimePickerDialog
                        .newInstance(
                                CreateEditListingActivity.this,
                                now.get(Calendar.HOUR_OF_DAY),
                                now.get(Calendar.MINUTE),
                                false
                        );
                timePickerDialog.show(getFragmentManager(), "Select the Time");
            }
        });
        listingTitleEditText.addTextChangedListener(new EditTextListener(listingTitleEditText.getId()));
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CreateEditListingActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        aboutEditText.addTextChangedListener(new EditTextListener(aboutEditText.getId()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Selecting image
        if (requestCode == CREATE_EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            Bitmap image = BitmapFactory.decodeFile(images.get(0).getPath());
            listingImageView.setImageBitmap(image);
        }
        else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                location = String.valueOf(place.getAddress());
                locationTextView.setText(place.getName().toString());
            }
        }
    }

    private boolean isPriceEditTextEmpty(){
        return priceEditText.getText().toString().isEmpty();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        fromHourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        fromMinuteString = minute < 10 ? "0"+minute : ""+minute;
        toHourString = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        toMinuteString = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;

        TextView fromDateTextView = (TextView) findViewById(R.id.fromTimeTextView);
        fromDateTextView.setText(fromHourString + ":" + fromMinuteString);
        TextView toDateTextView = (TextView) findViewById(R.id.toTimeTextView);
        toDateTextView.setText(toHourString + ":" + toMinuteString);
    }

    class EditTextListener implements TextWatcher{

        private int id;

        public EditTextListener(int id){
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (id == R.id.listingTitleEditText) { listingTitle = s.toString(); }
            else if (id == R.id.aboutEditText) { about = s.toString(); }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        HomeActivity.setNavDrawerToHome();
    }
}
