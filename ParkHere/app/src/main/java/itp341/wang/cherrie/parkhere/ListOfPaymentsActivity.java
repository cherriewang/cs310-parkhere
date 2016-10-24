package itp341.wang.cherrie.parkhere;

/**
 * Created by glarencezhao on 10/24/16.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;

public class ListOfPaymentsActivity extends AppCompatActivity{

    private Button addCardButton;
    private final int CREATE_NEW_CARD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_payments);

        initialize();
        listeners();

    }

    private void initialize(){

        addCardButton = (Button) findViewById(R.id.add_card_button);

        getSupportActionBar().setTitle("Payment");

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
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            LinearLayout cardContainer = (LinearLayout) findViewById(R.id.card_container);

            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if(reqCode == CREATE_NEW_CARD) {

                System.out.println("CREATED NEW CARD");

                CreditCardView creditCardView = new CreditCardView(this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(name);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);

                cardContainer.addView(creditCardView);
                int index = cardContainer.getChildCount() - 1;
                addCardListener(index, creditCardView);

            }
            else {

                System.out.println("EDITED EXISTING CARD " + reqCode);

                CreditCardView creditCardView = (CreditCardView) cardContainer.getChildAt(reqCode);

                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                creditCardView.setCardHolderName(name);
                creditCardView.setCVV(cvv);

            }
        }

    }

}
