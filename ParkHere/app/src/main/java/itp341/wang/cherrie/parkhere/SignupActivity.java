package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import itp341.wang.cherrie.parkhere.model.User;

public class SignupActivity extends AppCompatActivity {

    private User myUser;
    private Button enterButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    // ImageView upload??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstNameEditText = (EditText) findViewById(R.id.editTextFirst);
        lastNameEditText = (EditText) findViewById(R.id.editTextLast);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        passwordEditText = (EditText) findViewById(R.id.editTextPass);
        enterButton = (Button) findViewById(R.id.buttonEnter);

        myUser = new User();

        // enterButtonListener()
        // Override onClick with an Intent to start HomeActivity if sign up is successful
        // SEND EMAIL BUTTON LISTENER
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                myUser.setmFirstName(firstNameEditText.getText().toString());
                myUser.setmLastName(lastNameEditText.getText().toString());
                myUser.setmEmail(emailEditText.getText().toString());
                myUser.setmPhoneNumber(phoneEditText.getText().toString());
                myUser.setmHashedPassword(passwordEditText.getText().toString());
                // store User in database
            }
        });
    }
}
