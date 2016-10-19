package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.editTextPass);
        forgotPassword = (TextView) findViewById(R.id.textForgotPassword);
        enterButton = (Button) findViewById(R.id.buttonEnter);

        // enterButtonListener()
        // Override onClick with an Intent to start HomeActivity if log in is successful

        // forgotPasswordListener()
        // Override onClick with an Intent to start ForgotPasswordActivity
    }
}
