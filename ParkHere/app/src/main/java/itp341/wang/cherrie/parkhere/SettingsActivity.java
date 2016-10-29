package itp341.wang.cherrie.parkhere;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private TextView savedHome;
    private TextView savedWork;
    private EditText homeEditText;
    private EditText workEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logoutButton = (Button) findViewById(R.id.buttonLogout);
        savedHome = (TextView) findViewById(R.id.textViewHome);
        savedWork = (TextView) findViewById(R.id.textViewWork);
        homeEditText = (EditText) findViewById(R.id.editTextHome);
        workEditText = (EditText) findViewById(R.id.editTextWork);

        // TODO: set listeners for EditText to update the TextViews

        // SEND EMAIL BUTTON LISTENER
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                // log the user out
                // set intent to return to welcome activity
                Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                setResult(Activity.RESULT_OK, welcomeIntent);
                finish();
            }
        });
    }

}
