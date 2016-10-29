package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button sendEmailButton;
    private EditText emailInput;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sendEmailButton = (Button) findViewById(R.id.buttonSendEmail);
        emailInput = (EditText) findViewById(R.id.editTextEmail);

        // SEND EMAIL BUTTON LISTENER
        sendEmailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                emailAddress = emailInput.getText().toString();
                // verify that the email exists in the database
                // some function that sends email for password reset
                // return to login activity via intent for result
                Intent loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
                setResult(Activity.RESULT_OK, loginIntent);
                finish();
            }
        });
    }

}
