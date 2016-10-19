package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button signUpButton;
    private Button loginButton;
    private Button mFacebookLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        signUpButton = (Button) findViewById(R.id.buttonSignup);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        mFacebookLoginButton = (Button) findViewById(R.id.buttonFacebook);

        // Button Listener for Sign up
        // signupButtonListener()

        // Button Listener for Login
        // loginButtonListener()

        // Button Listener for Facebook login
        //facebookLoginButtonListener()
    }
}
