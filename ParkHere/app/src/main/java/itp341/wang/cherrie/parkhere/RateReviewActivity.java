package itp341.wang.cherrie.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;
import itp341.wang.cherrie.parkhere.model.User;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by glarencezhao on 10/28/16.
 */

public class RateReviewActivity extends AppCompatActivity {

    private MaterialRatingBar ownerRatingBar;
    private MaterialRatingBar listingRatingBar;
    private EditText reviewEditText;
    private Button submitButton;
    private SimpleDraweeView ownerReviewProfPic;
    private SimpleDraweeView listingImageView;
    private Review myReview;
    private Listing myListing;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_review);


        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        // Create a Review Card
        Card reviewCard = new Card(this, R.layout.card_review_inner_layout);
        //Set listing title
        reviewCard.setTitle("Listing Title");
        // Set card in the cardView
        CardView cardView = (CardView) findViewById(R.id.reviewCard);
        cardView.setCard(reviewCard);

        ownerRatingBar = (MaterialRatingBar) findViewById(R.id.ownerRatingBar);
        listingRatingBar = (MaterialRatingBar) findViewById(R.id.listingRatingBar);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        submitButton = (Button) findViewById(R.id.submitReviewButton);
        ownerReviewProfPic = (SimpleDraweeView) findViewById(R.id.ownerReviewProfPic);
        //load owner picture
        listingImageView = (SimpleDraweeView) findViewById(R.id.listingImageView);
        //load listing picture

        myReview = new Review();
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void listeners(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.printToast("Submit Button clicked", getApplicationContext());
            }
        });
        RatingBarListener RBListener = new RatingBarListener();
        ownerRatingBar.setOnRatingChangeListener(RBListener);
        listingRatingBar.setOnRatingChangeListener(RBListener);
        reviewEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get review from here
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // CREATE REVIEW
//        myReview.setOwner();
        myReview.setReviewer(myUser.getmEmail());
        myReview.setReviewText(reviewEditText.getText().toString());
        myReview.setListingImage(listingImageView.getDrawingCache());
        myReview.setOwnerReviewImage(ownerReviewProfPic.getDrawingCache());
        // TODO: put this into the database
    }

    class RatingBarListener implements MaterialRatingBar.OnRatingChangeListener {
        @Override
        public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
            int id = ratingBar.getId();
            if(id == R.id.ownerRatingBar){
                Debug.printToast("Rating is: " + rating, getApplicationContext());
            }
            if(id == R.id.listingRatingBar){
                Debug.printToast("Rating is: " + rating, getApplicationContext());
            }
        }
    }
}