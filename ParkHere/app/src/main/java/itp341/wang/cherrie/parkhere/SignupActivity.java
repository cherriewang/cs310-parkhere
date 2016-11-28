package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import itp341.wang.cherrie.parkhere.ParkHereApplication;

import itp341.wang.cherrie.parkhere.model.User;

public class SignupActivity extends AppCompatActivity {

    private Button enterButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private boolean isSeeker = false;
    private boolean isOwner = false;
    private SimpleDraweeView userProfPicView;
    private User myUser;
    private CircularProgressView progressView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;



    public final static int SIGN_UP_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        initialize();
        listeners();
    }

    private boolean isFieldsEmpty(){
        if(!firstNameEditText.getText().toString().isEmpty()
                && !lastNameEditText.getText().toString().isEmpty()
                && !emailEditText.getText().toString().isEmpty()
                && !phoneEditText.getText().toString().isEmpty()
                && !passwordEditText.getText().toString().isEmpty())
            return false;
        return true;
    }

    private boolean isPasswordOK(){
        String password = passwordEditText.getText().toString();

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        boolean b = m.find();
        int length = password.length();
        //if 10 characters and at least one special character
        if(b && length >= 10){
            return true;
        }
        return false;
    }

    private void initialize(){
        firstNameEditText = (EditText) findViewById(R.id.editTextFirst);
        lastNameEditText = (EditText) findViewById(R.id.editTextLast);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        passwordEditText = (EditText) findViewById(R.id.editTextPass);
        enterButton = (Button) findViewById(R.id.buttonEnter);
        userProfPicView = (SimpleDraweeView) findViewById(R.id.userProfPicView);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);

        ((ParkHereApplication) this.getApplication()).setMyUser(new User());
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null && myUser != null && myUser.getmNormalizedEmail() != null) {
                    Debug.printToast("User is not null", getApplicationContext());
                    if (user.isEmailVerified()) {
                        Debug.printToast("Signup Successful", getApplicationContext());

                        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivityForResult(homeIntent, 0);
                    } else {
                        Debug.printToast("User is not verified, sending verification email", getApplicationContext());
                        user.sendEmailVerification();

                        progressView.stopAnimation();
                        progressView.setVisibility(View.GONE);

                        Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivityForResult(welcomeIntent, 0);
                    }

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    myRef.child("users").child(myUser.getmNormalizedEmail()).setValue(myUser);
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void signUp() {
        mAuth.createUserWithEmailAndPassword(myUser.getmEmail(), passwordEditText.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignupActivity.this, "Signed up: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Sign up unsuccessful", Toast.LENGTH_SHORT).show();
                        progressView.stopAnimation();
                        progressView.setVisibility(View.GONE);
                    }
                }
            });
    }

    private void listeners(){
        // enterButtonListener()
        // Override onClick with an Intent to start HomeActivity if sign up is successful
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                progressView.setVisibility(View.VISIBLE);
                progressView.startAnimation();
                // Populate User object
                myUser.setmFirstName(firstNameEditText.getText().toString());
                myUser.setmLastName(lastNameEditText.getText().toString());
                myUser.setmEmail(emailEditText.getText().toString());
                myUser.setmPhoneNumber(phoneEditText.getText().toString());
                myUser.setmHashedPassword(passwordEditText.getText().toString().hashCode());
                myUser.setOwner(isOwner);
                myUser.setSeeker(isSeeker);

                if(isFieldsEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields are empty, please fill them out",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordOK()) {
                    Toast.makeText(getApplicationContext(),
                            "Password must have at least 10 characters long with one special character",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // store User in database
                    signUp();
                }
            }
        });
        userProfPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent intent = new Intent(SignupActivity.this, ImagePickerActivity.class);

                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_SINGLE);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, new ArrayList<Image>());
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Albums");
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Select an Image");
                intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");

                startActivityForResult(intent, SIGN_UP_REQUEST_CODE);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_seeker:
                if (checked)
                    // seeker
                    isSeeker = true;
                    break;
            case R.id.radio_owner:
                if (checked)
                    // owner
                    isOwner = true;
                    break;
        }
    }
    // Handlers for return result Intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(0): {
                // Return from HomeActivity
            }
            break;
        }

        if(requestCode == SIGN_UP_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            Bitmap image = BitmapFactory.decodeFile(images.get(0).getPath());
            userProfPicView.setImageBitmap(image);
            //userProfPicView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
