package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import itp341.wang.cherrie.parkhere.model.Transaction;
import itp341.wang.cherrie.parkhere.model.User;

public class   SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private Button authorizeButton;
    private Button saveButton;
    private SimpleDraweeView userProfPicView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private HashMap<String, Transaction> transactionTracker;
    private User myUser;
    private double amountDue;
    private double existingBalance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
        listeners();
    }

    private void initialize(){
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        authorizeButton = (Button) findViewById(R.id.buttonAuthorize);
        saveButton = (Button) findViewById(R.id.buttonSave);
        userProfPicView = (SimpleDraweeView) findViewById(R.id.userProfPicView);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();

        populate();
    }

    private void populate(){
        firstNameEditText.setHint(myUser.getmFirstName());
        lastNameEditText.setHint(myUser.getmLastName());
    }

    private void listeners(){
        // SEND EMAIL BUTTON LISTENER
        final SettingsActivity _this = this;
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                if(!firstName.isEmpty() && !lastName.isEmpty()){
                    myUser.setmFirstName(firstName);
                    myUser.setmLastName(lastName);

                    ((ParkHereApplication) _this.getApplication()).setMyUser(myUser);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    myRef.child("users").child(myUser.getmNormalizedEmail()).child("mFirstName").setValue(firstName);
                    myRef.child("users").child(myUser.getmNormalizedEmail()).child("mLastName").setValue(lastName);

                    Debug.printToast("Successfully edited first and last name", getApplicationContext());

                    firstNameEditText.setText("");
                    lastNameEditText.setText("");
                    populate();
                }
                else{
                    Debug.printToast("Missing either first name and/or last name", getApplicationContext());
                }
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
                authorizeDialog();

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

    private void authorizeDialog() {
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


                        // finds the card object associated with my user email
                        transactionTracker = myUser.getmTransactions();
                        if (transactionTracker == null){
                            Debug.printToast("You don't have any current pending payments", getApplicationContext());
                        } else {
                            // update the accountBalance of person to be paid
                            // reset firebase with new users
                            // delete transaction on transaction tracker
                            for (String key : transactionTracker.keySet()) {
                                Transaction currTrans = transactionTracker.get(key);

                                Calendar now = Calendar.getInstance();
                                if(now.after(currTrans.getTransactionCalendar())){
                                    // it's completed

                                    final DatabaseReference usersRef = database.getReference("user");
                                    usersRef.orderByChild(currTrans.getListingOwner().replace(".", "%2E")).addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                            User listingOwner = dataSnapshot.getValue(User.class);
                                            existingBalance = listingOwner.getAccountBalance();
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {}

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    });

                                    // look up ownerToUpdate and add to ListingOwner balance
                                    myRef.child("users").child(currTrans.getListingOwner().replace(".", "%2E")).child("accountBalance").setValue(existingBalance+currTrans.getBalance());
                                    // change boolean to notify Owner that he/she has been paid
                                    Debug.printToast("The listing Owner is: "+currTrans.getListingOwner(), getApplicationContext());
                                    myRef.child("users").child(currTrans.getListingOwner().replace(".", "%2E")).child("recentTransactionApproved").setValue(true);
                                    // subtract from your balance
                                    myRef.child("users").child(myUser.getmNormalizedEmail()).child("accountBalance").setValue(myUser.getAccountBalance()-currTrans.getBalance());
                                    //myUser.setAccountBalance(myUser.getAccountBalance()-currTrans.getBalance());
                                    Log.e("SettingsActivity", "Is it false: "+myUser.hasRecentTransactionApproved());
                                    // removes it from the hashtable once we're done'
                                    Debug.printToast(currTrans.getListingName()+" has been authorized for payment.", getApplicationContext());
                                    transactionTracker.remove(key);
                                } else {
                                    Debug.printToast(currTrans.getListingName()+" has not yet passed. You can only authorize payments for completed bookings.", getApplicationContext());
                                }

                            }
                            // clear all transactions
                            //transactionTracker.clear();

                            // set myUser transactions to the updated map (with the completed ones removed)
                            myRef.child("users").child(myUser.getmNormalizedEmail()).child("mTransactions").setValue(transactionTracker);

                            Debug.printToast("All eligible pending payments have been authorized", getApplicationContext());
                        }

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
