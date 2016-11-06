package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private Button authorizeButton;
    private Button saveButton;
    private TextView savedHome;
    private TextView savedWork;
    private EditText homeEditText;
    private EditText workEditText;

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

}
