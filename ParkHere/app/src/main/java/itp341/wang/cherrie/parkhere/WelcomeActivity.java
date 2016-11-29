package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.wang.cherrie.parkhere.model.User;

public class WelcomeActivity extends AppCompatActivity {
    private Button signUpButton;
    private Button loginButton;
    private LoginButton mFacebookLoginButton;
    private CircularProgressView progressView;
    private FirebaseAuth mAuth;
    private User myUser;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        initialize();
        listeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Debug.printToast("handleFacebookAccessToken:" + token, getApplicationContext());

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Debug.printToast("signInWithCredential:onComplete:" + task.isSuccessful(), getApplicationContext());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Debug.printToast("signInWithCredential", getApplicationContext());
                            Debug.printToast("Authentication failed.", getApplicationContext());
                        }

                        // ...
                    }
                });
    }

    private void initialize(){
        mAuth = FirebaseAuth.getInstance();
        signUpButton = (Button) findViewById(R.id.buttonSignup);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        mFacebookLoginButton = (LoginButton) findViewById(R.id.buttonFacebook);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
    }

    private void listeners(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        // SIGN UP BUTTON LISTENER
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                progressView.startAnimation();
                progressView.setVisibility(View.VISIBLE);
                Intent signUpIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(signUpIntent,0);
            }
        });

        // Button Listener for Login
        // LOGIN BUTTON LISTENER
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                progressView.startAnimation();
                progressView.setVisibility(View.VISIBLE);
                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivityForResult(loginIntent,1);
            }
        });

        CallbackManager mCallbackManager = CallbackManager.Factory.create();

        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Debug.printToast("facebook:onSuccess:" + loginResult, getApplicationContext());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                // ...
            }
        });

        // Button Listener for Facebook login
//        mFacebookLoginButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            //On click function
//            public void onClick(View view) {
//                // Call Facebook login auth API ?
//
//            }
//        });
    }

    // Handlers for return result Intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(0): {
                // Return from SignupActivity
                progressView.stopAnimation();
                progressView.setVisibility(View.GONE);
            }
            break;
            case (1): {
                // Return from LoginActivity
                progressView.stopAnimation();
                progressView.setVisibility(View.GONE);
            }
            break;
        }
    }
}

