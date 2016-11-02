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

import com.blackcat.currencyedittext.CurrencyEditText;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.Listing;

/**
 * Created by glarencezhao on 10/24/16.
 */

public class CreateEditListingActivity extends AppCompatActivity {

    private ImageView listingImageView;
    private EditText listingTitleEditText;
    private CheckBox handicappedCheckBox;
    private CheckBox tandemCheckBox;
    private CheckBox suvCheckBox;
    private CheckBox coveredCheckBox;
    private EditText locationEditText;
    private EditText aboutEditText;
    private CurrencyEditText priceEditText;
    private Button createListingButton;
    private Listing myListing;

    private String listingTitle = "";
    private String location = "";
    private String about = "";
    private long price = 0;
    private boolean isTandem = false;
    private boolean isHandicapped = false;
    private boolean isSUV = false;
    private boolean isCovered = false;

    public final static int CREATE_EDIT_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_listing);

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
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        aboutEditText = (EditText) findViewById(R.id.aboutEditText);
        priceEditText = (CurrencyEditText) findViewById(R.id.priceEditText);
        createListingButton = (Button) findViewById(R.id.createListingButton);

        myListing = new Listing();
    }

    //Used to fill up info if editing a listing
    private void populate(){
        //listingTitle =
        //location =
        //about =
        //price =
        //isTandem =
        //isHandicapped =

        //listingEditText.setText(listingTitle);
        //locationEditText.setText(location);
        //aboutEditText.setText(about);
        //priceEditText.setText(Long.toString(price));
        //tandemCheckBox.setChecked(isTandem);
        //handicappedCheckBox.setChecked(isHandicapped);
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
                    price = priceEditText.getRawValue();
                    isTandem = tandemCheckBox.isChecked();
                    isHandicapped = handicappedCheckBox.isChecked();
                    isSUV = suvCheckBox.isChecked();
                    isCovered = coveredCheckBox.isChecked();

                    // CREATE LISTING OBJECT
                    myListing.setListingTitle(listingTitle);
                    //myListing.setListingOwner();
                    myListing.setAbout(about);
                    myListing.setPrice(price);
                    myListing.setTandem(isTandem);
                    myListing.setHandicapped(isHandicapped);
                    myListing.setSuv(isSUV);
                    myListing.setCovered(isCovered);

                    // TODO: Listing object now created, need to have to passed to database under the owner

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        listingTitleEditText.addTextChangedListener(new EditTextListener(listingTitleEditText.getId()));
        locationEditText.addTextChangedListener(new EditTextListener(locationEditText.getId()));
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
    }

    private boolean isPriceEditTextEmpty(){
        return priceEditText.getText().toString().isEmpty();
    }

    class EditTextListener implements TextWatcher{

        private int id;

        public EditTextListener(int id){
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(id == R.id.listingTitleEditText){ listingTitle = s.toString(); }
            else if(id == R.id.locationEditText){ location = s.toString(); }
            else if(id == R.id.aboutEditText){ about = s.toString(); }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
