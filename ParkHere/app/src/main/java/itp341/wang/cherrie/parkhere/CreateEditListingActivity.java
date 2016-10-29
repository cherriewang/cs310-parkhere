package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * Created by glarencezhao on 10/24/16.
 */

public class CreateEditListingActivity extends AppCompatActivity {

    private ImageView listingImageView;
    private EditText listingTitleEditText;
    private CheckBox handicappedCheckBox;
    private CheckBox tandemCheckBox;
    private EditText locationEditText;
    private EditText aboutEditText;
    private CurrencyEditText priceEditText;
    private Button createListingButton;

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
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        aboutEditText = (EditText) findViewById(R.id.aboutEditText);
        priceEditText = (CurrencyEditText) findViewById(R.id.priceEditText);
        createListingButton = (Button) findViewById(R.id.createListingButton);
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
}
