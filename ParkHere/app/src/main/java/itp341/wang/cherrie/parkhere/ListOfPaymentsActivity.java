package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.HashMap;

import itp341.wang.cherrie.parkhere.model.CreditCard;
import itp341.wang.cherrie.parkhere.model.Transaction;
import itp341.wang.cherrie.parkhere.model.User;

/**
 * Created by glarencezhao on 10/23/16.
 */

public class ListOfPaymentsActivity extends AppCompatActivity{

    private Button addCardButton;
    private final int CREATE_NEW_CARD = 0;
    private LinearLayout cardContainer;
    private TextView balanceTextView;
    private User myUser;
    private HashMap<String, CreditCard> creditCardRecord;
    private boolean isSelectingPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_payments);

        Intent i = getIntent();
        if(i != null)
            isSelectingPayment = i.getBooleanExtra(ListingDetailActivity.SELECTING_PAYMENT, false);

        initialize();
        listeners();
    }

    private void initialize(){
        addCardButton = (Button) findViewById(R.id.add_card_button);
        cardContainer = (LinearLayout) findViewById(R.id.card_container);
        balanceTextView = (TextView) findViewById(R.id.textViewBalance);

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        creditCardRecord = myUser.getmCreditCards();
        getSupportActionBar().setTitle("Payment");
        populate();
    }

    private void populate(){
        CreditCardView sampleCreditCardView = new CreditCardView(this);

        String name = "Glarence Zhao";
        String cvv = "420";
        String expiry = "01/18";
        String cardNumber = "4242424242424242";

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        balanceTextView.setText(String.valueOf(formatter.format(myUser.getAccountBalance())));

        cardContainer.addView(sampleCreditCardView);
        int index = cardContainer.getChildCount() - 1;
        addCardListener(index, sampleCreditCardView);
    }

    private void listeners(){
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListOfPaymentsActivity.this, CardEditActivity.class);
                startActivityForResult(intent, CREATE_NEW_CARD);
            }
        });
    }

    private void addCardListener(final int index, CreditCardView creditCardView){
        creditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectingPayment){
                    //pass payment details
                    setResult(RESULT_OK);
                    //finishActivity(ListingDetailActivity.SELECT_PAYMENT_REQUEST_CODE);
                    finish();
                }
                else{
                    System.out.println("EDITING CARD " + index);

                    CreditCardView creditCardView = (CreditCardView) v;
                    String cardNumber = creditCardView.getCardNumber();
                    String expiry = creditCardView.getExpiry();
                    String cardHolderName = creditCardView.getCardHolderName();
                    String cvv = creditCardView.getCVV();

                    Intent intent = new Intent(ListOfPaymentsActivity.this, CardEditActivity.class);
                    intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName);
                    intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardNumber);
                    intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry);
                    intent.putExtra(CreditCardUtils.EXTRA_CARD_CVV, cvv);
                    intent.putExtra(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_FRONT);
                    intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, true);

                    startActivityForResult(intent, index);
                }
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            Debug.printToast("Result Code is OK", getApplicationContext());

            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if(reqCode == CREATE_NEW_CARD) {
                Debug.printToast("CREATED NEW CARD", getApplicationContext());

                CreditCardView creditCardView = new CreditCardView(this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(name);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);

                CreditCard newCreditCard = new CreditCard();
                newCreditCard.setCvv(cvv);
                newCreditCard.setCardHolderName(name);
                newCreditCard.setExpiry(expiry);
                newCreditCard.setCardNumber(cardNumber);
                myUser.appendCreditCard(newCreditCard);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("users").child(myUser.getmNormalizedEmail()).child("mCreditCards").setValue(myUser.getmCreditCards());

                cardContainer.addView(creditCardView);
                int index = cardContainer.getChildCount() - 1;
                addCardListener(index, creditCardView);

            }
            else {
                Debug.printToast("EDITED EXISTING CARD " + reqCode, getApplicationContext());

                CreditCardView creditCardView = (CreditCardView) cardContainer.getChildAt(reqCode);

                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                creditCardView.setCardHolderName(name);
                creditCardView.setCVV(cvv);

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        HomeActivity.setNavDrawerToHome();
    }

}
