package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.ParkingSpot;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 11/28/16.
 */

public class CreateEditParkingSpotActivity extends AppCompatActivity{

    private ImageView parkingSpotImageView;
    private EditText parkingSpotNameEditText;
    private CheckBox handicappedCheckBox;
    private CheckBox tandemCheckBox;
    private CheckBox suvCheckBox;
    private CheckBox coveredCheckBox;
    private TextView locationTextView;
    private EditText aboutEditText;
    private Button createParkingSpotButton;

    private boolean isTandem = false;
    private boolean isHandicapped = false;
    private boolean isSUV = false;
    private boolean isCovered = false;
    private String location = "";
    private String about = "";
    private double latitude = 0;
    private double longitude = 0;
    private String parkingSpotOwner = "";
    private String parkingSpotName = "";

    private boolean isEditing = false;

    private User myUser;

    private ParkingSpot myParkingSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_parking_spot);

        Intent i = getIntent();
        if(i.hasExtra(ParkingSpotAdapter.PARKING_SPOT_EDIT_INTENT_KEY)) {
            myParkingSpot = (ParkingSpot) i.getSerializableExtra(ParkingSpotAdapter.PARKING_SPOT_EDIT_INTENT_KEY);
            isEditing = true;
        }

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        parkingSpotImageView = (ImageView) findViewById(R.id.parkingSpotImageView);
        parkingSpotNameEditText = (EditText) findViewById(R.id.parkingSpotNameEditText);
        handicappedCheckBox = (CheckBox) findViewById(R.id.handicappedCheckBox);
        tandemCheckBox = (CheckBox) findViewById(R.id.tandemCheckBox);
        suvCheckBox = (CheckBox) findViewById(R.id.suvCheckBox);
        coveredCheckBox = (CheckBox) findViewById(R.id.coveredCheckBox);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        aboutEditText = (EditText) findViewById(R.id.aboutEditText);
        createParkingSpotButton = (Button) findViewById(R.id.createParkingSpotButton);
        if(isEditing)
            createParkingSpotButton.setText("Edit");

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();

        populate();
    }

    //Used to fill up info if editing a listing
    private void populate(){
        if (myParkingSpot != null) {
            Debug.printToast("Parking spot will be populated!", getApplicationContext());
            parkingSpotName = myParkingSpot.getParkingSpotName();
            parkingSpotOwner = myParkingSpot.getParkingSpotOwner();
            location = myParkingSpot.getLocation();
            about = myParkingSpot.getAbout();
            isTandem = myParkingSpot.isTandem();
            isHandicapped = myParkingSpot.isHandicapped();
            isSUV = myParkingSpot.isSuv();
            isCovered = myParkingSpot.isCovered();
            parkingSpotImageView.setImageBitmap(base64ToBitmap(myParkingSpot.getParkingSpotImageString()));

            parkingSpotNameEditText.setText(parkingSpotName);
            locationTextView.setText(location);
            aboutEditText.setText(about);
            tandemCheckBox.setChecked(isTandem);
            handicappedCheckBox.setChecked(isHandicapped);
            suvCheckBox.setChecked(isSUV);
            coveredCheckBox.setChecked(isCovered);
        }
        else
            myParkingSpot = new ParkingSpot();
    }

    private void listeners(){
        parkingSpotImageView.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent intent = new Intent(CreateEditParkingSpotActivity.this, ImagePickerActivity.class);

                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, new ArrayList<Image>());
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Albums");
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Select an Image");
                        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

                        startActivityForResult(intent, CreateEditListingActivity.CREATE_EDIT_REQUEST_CODE);

                        break;
                    }
                }
                return true;
            }
        });
        createParkingSpotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting all values from the input fields
                isTandem = tandemCheckBox.isChecked();
                isHandicapped = handicappedCheckBox.isChecked();
                isSUV = suvCheckBox.isChecked();
                isCovered = coveredCheckBox.isChecked();

                // getting image from the image input
                parkingSpotImageView.setDrawingCacheEnabled(true);
                parkingSpotImageView.buildDrawingCache();
                Bitmap bitmap = parkingSpotImageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                String imageString = Base64.encodeToString(data, Base64.DEFAULT);

                System.out.print("compressed image");

                // CREATE LISTING OBJECT
                myParkingSpot.setParkingSpotName(parkingSpotName);
                myParkingSpot.setParkingSpotOwner(myUser.getmNormalizedEmail()); // should have a global user here
                myParkingSpot.setLocation(location);
                myParkingSpot.setAbout(about);

                //Setting listing categories
                myParkingSpot.setTandem(isTandem);
                myParkingSpot.setHandicapped(isHandicapped);
                myParkingSpot.setSuv(isSUV);
                myParkingSpot.setCovered(isCovered);

                //Setting Lat Lng to retrieve for a listing
                myParkingSpot.setLatitude(latitude);
                myParkingSpot.setLongitude(longitude);

                //Setting parking spot image to retrieve for a listing
                myParkingSpot.setParkingSpotImageString(imageString);

                myUser.appendParkingSpot(myParkingSpot);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("parking spots").child(myParkingSpot.getParkingSpotName()).setValue(myParkingSpot);
                myRef.child("users").child(myUser.getmNormalizedEmail()).child("mParkingSpots")
                        .child(myParkingSpot.getParkingSpotName()).setValue(myParkingSpot);

                setResult(RESULT_OK);
                finish();
            }
        });
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CreateEditParkingSpotActivity.this), CreateEditListingActivity.PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        parkingSpotNameEditText.addTextChangedListener(new EditTextListener(parkingSpotNameEditText.getId()));
        aboutEditText.addTextChangedListener(new EditTextListener(aboutEditText.getId()));
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    class EditTextListener implements TextWatcher {

        private int id;

        public EditTextListener(int id){
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (id == R.id.parkingSpotNameEditText) { parkingSpotName = s.toString(); }
            else if (id == R.id.aboutEditText) { about = s.toString(); }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Selecting image
        if (requestCode == CreateEditListingActivity.CREATE_EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            Bitmap image = BitmapFactory.decodeFile(images.get(0).getPath());
            parkingSpotImageView.setImageBitmap(image);
        }
        else if (requestCode == CreateEditListingActivity.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                location = String.valueOf(place.getAddress());
                locationTextView.setText(place.getName().toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        HomeActivity.setNavDrawerToHome();
    }

}
