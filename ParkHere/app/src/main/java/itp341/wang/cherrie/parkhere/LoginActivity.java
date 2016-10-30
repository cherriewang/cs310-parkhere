package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button enterButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPassword;

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

    private void listeners(){
        // ENTER BUTTON LISTENER
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                boolean validUser = true;
                // verify email + password, return bool

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
}
