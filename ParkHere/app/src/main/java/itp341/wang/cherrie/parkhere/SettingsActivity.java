package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import itp341.wang.cherrie.parkhere.model.Card;
import itp341.wang.cherrie.parkhere.model.User;

public class SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private Button authorizeButton;
    private Button saveButton;
    private TextView savedHome;
    private TextView savedWork;
    private EditText homeEditText;
    private EditText workEditText;
    private User myUser;
    private Card myCard;
    private double amountDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logoutButton = (Button) findViewById(R.id.buttonLogout);
        authorizeButton = (Button) findViewById(R.id.buttonAuthorize);
        saveButton = (Button) findViewById(R.id.buttonSave);
        savedHome = (TextView) findViewById(R.id.textViewHome);
        savedWork = (TextView) findViewById(R.id.textViewWork);
        homeEditText = (EditText) findViewById(R.id.editTextHome);
        workEditText = (EditText) findViewById(R.id.editTextWork);
        savedWork = (TextView) findViewById(R.id.textViewWork);
        savedHome = (TextView) findViewById(R.id.textViewHome);

        // TODO: set listeners for EditText to update the TextViews
        listeners();

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void listeners(){
        // SEND EMAIL BUTTON LISTENER
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                // log the user out
                // set intent to return to welcome activity
                savedHome.setText(homeEditText.getText().toString());
                savedWork.setText(workEditText.getText().toString());

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

}
