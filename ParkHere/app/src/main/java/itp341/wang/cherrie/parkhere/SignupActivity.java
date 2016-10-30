package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import itp341.wang.cherrie.parkhere.model.User;

public class SignupActivity extends AppCompatActivity {

    private User myUser;
    private Button enterButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    String URL_ADDRESS = "http://localhost:8080/signup";
    private HttpURLConnection client;
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

                HttpURLConnection urlConnection = null;

                // store User in database
                try {
                    URL url = new URL(URL_ADDRESS);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    writeStream(out);

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                } catch(Exception e){
                    String except = new String("Exception: " + e.getMessage());
                    Debug.printToast(except, getApplicationContext());
                }finally {
                    urlConnection.disconnect();
                }

                // from the readStream
                if(true) {
                    // Intent to HomeActivity
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivityForResult(homeIntent,0);
                } else {
                    // Toast unsuccessful, clear input fields
                    Debug.printToast("Signup Unsuccessful", getApplicationContext());
                }

            }
        });
    }

    private void writeStream(OutputStream out){
        String output = "Hello world";
        try {
            out.write(output.getBytes());
            out.flush();
        }  catch(Exception e) {
            String except = new String("Exception: " + e.getMessage());
            Debug.printToast(except, getApplicationContext());
        }
    }

    // Temp readStream function that assumes we're reading a String response back
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line = r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
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
    }
}
