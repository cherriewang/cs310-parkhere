package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.wang.cherrie.parkhere.model.User;

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
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(emailAddress);

                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                setResult(Activity.RESULT_OK, loginIntent);
                finish();
            }
        });
    }

}
