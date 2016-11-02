package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import itp341.wang.cherrie.parkhere.model.User;

public class LoginActivity extends AppCompatActivity {

    private Button enterButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPassword;
    private User candidateUser;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initialize();
        listeners();
    }

    private void initialize(){
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.editTextPass);
        forgotPassword = (TextView) findViewById(R.id.textForgotPassword);
        enterButton = (Button) findViewById(R.id.buttonEnter);
    }

    private boolean checkUser(User cUser, int hashedPassword) {
        if (cUser.getmHashedPassword() == hashedPassword) {
            ((ParkHereApplication) this.getApplication()).setMyUser(cUser);
            return true;
        } else {
            return false;
        }
    }

    private void listeners(){
        // ENTER BUTTON LISTENER
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                boolean validUser = false;

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                int hashedPassword = password.hashCode();

                UserLoginTask t = new UserLoginTask(email, hashedPassword);
                t.execute((Void) null);

                if (validUser){
                    // Intent to start HomeActivity
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivityForResult(homeIntent,0);
                }

            }
        });

        // forgotPasswordListener()
        // Override onClick with an Intent to start ForgotPasswordActivity
        // ENTER BUTTON LISTENER
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent forgotPassIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivityForResult(forgotPassIntent,1);
            }
        });
    }

    // Handlers for return result Intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(0): {
                // Return from HomeActivity
                // Logout?
            }
            break;
            case (1): {
                // Return from ForgotActivity
            }
            break;
        }
    }

    /**
     * Represents an asynchronous, multi-threaded login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final int mHashedPassword;
        private boolean authenticationStatus = true;
        UserLoginTask(String username, int password) {
            mEmail = username;
            mHashedPassword = password;
            authenticationStatus = doInBackground((Void) null);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //A firebaseError will occur whenever the authentication fails.
//            ref = new Firebase("https://anchronize.firebaseio.com");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users").child(mEmail.replace(".", "%2E"));

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    candidateUser = dataSnapshot.getValue(User.class);
                    checkUser(candidateUser, mHashedPassword);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });

            return authenticationStatus;

    }
}}
