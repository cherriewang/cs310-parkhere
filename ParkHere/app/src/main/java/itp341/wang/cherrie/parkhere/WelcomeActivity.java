package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class WelcomeActivity extends AppCompatActivity {
    private Button signUpButton;
    private Button loginButton;
    private Button mFacebookLoginButton;
    private CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        initialize();
        listeners();
    }

    private void initialize(){
        signUpButton = (Button) findViewById(R.id.buttonSignup);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        mFacebookLoginButton = (Button) findViewById(R.id.buttonFacebook);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
    }

    private void listeners(){
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

        // Button Listener for Facebook login
        mFacebookLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                // Call Facebook login auth API ?
            }
        });
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

