package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.w3c.dom.Text;

import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.Card;
import itp341.wang.cherrie.parkhere.model.User;

public class SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private Button authorizeButton;
    private Button saveButton;
    private SimpleDraweeView userProfPicView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private User myUser;
    private Card myCard;
    private double amountDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
        listeners();

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void initialize(){
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        authorizeButton = (Button) findViewById(R.id.buttonAuthorize);
        saveButton = (Button) findViewById(R.id.buttonSave);
        userProfPicView = (SimpleDraweeView) findViewById(R.id.userProfPicView);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        populate();
    }

    private void populate(){
        //firstNameEditText.setHint(myUser.getmFirstName());
        //lastNameEditText.setHint(myUser.getmLastName());
    }

    private void listeners(){
        // SEND EMAIL BUTTON LISTENER
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                // log the user out
                // set intent to return to welcome activity

            }
        });

        // SEND EMAIL BUTTON LISTENER
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                // log the user out
                // set intent to return to welcome activity
                Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                setResult(Activity.RESULT_OK, welcomeIntent);
                //log out code
                startActivity(welcomeIntent);
            }
        });

        // ALLOW PAYMENTS TO HAPPEN LISTENER
        authorizeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("transaction-tracker");
                Debug.printToast("Your pending payments have been authorized", getApplicationContext());
                // finds the card object associated with my user email
                // myCard = that card
                // myCard.setApproved(true);
            }
        });

        // CHANGE PICTURE
        userProfPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent intent = new Intent(SettingsActivity.this, ImagePickerActivity.class);

                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, new ArrayList<Image>());
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Albums");
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Select an Image");
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        HomeActivity.setNavDrawerToHome();
    }

    private void notSeekerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.dialog_title_payment));
        builder.setMessage(getString(R.string.dialog_message_payment));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the balance due from transaction tracker under username

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        // update the accountBalance of person to be paid
                        // reset firebase with new users
                        // delete transaction on transaction tracker
                        Debug.printToast("Your pending payments have been authorized", getApplicationContext());
                        myRef.child("transaction-tracker").child(myUser.getmNormalizedEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user information
                                myCard  = dataSnapshot.getValue(Card.class);
                                amountDue = myCard.getBalance();
                                System.out.println(myCard.getBalance());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        myRef.child("users").child(myCard.getListingOwner()).child("accountBalance").setValue(amountDue);
//                        myRef.child("users").child(myCard.getListingOwner).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                // Get user information
//                                myCard  = dataSnapshot.getValue(Card.class);
//                                amountDue = myCard.getBalance();
//                                System.out.println(myCard.getBalance());
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(0): {
                // Return from HomeActivity
            }
            break;
        }

        if(requestCode == 0 && resultCode == RESULT_OK && data != null){
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            Bitmap image = BitmapFactory.decodeFile(images.get(0).getPath());
            userProfPicView.setImageBitmap(image);
            //userProfPicView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }


}
