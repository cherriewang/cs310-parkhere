package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private EditText reviewTitleEditText;
    private Button submitButton;
    private SimpleDraweeView ownerReviewProfPic;
    private SimpleDraweeView listingImageView;
    private Review myReview;
    private Listing listingToReview;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_review);

        Intent i = getIntent();
        if(i.hasExtra(BookingAdapter.BOOKING_REVIEW_INTENT_KEY))
            listingToReview = (Listing) i.getSerializableExtra(BookingAdapter.BOOKING_REVIEW_INTENT_KEY);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        //Create a Review Card
        Card reviewCard = new Card(this, R.layout.card_review_inner_layout);
        reviewCard.setTitle(listingToReview.getListingTitle());
        //Set listing picture
        //Set listing owner picture
        CardView cardView = (CardView) findViewById(R.id.reviewCard);
        cardView.setCard(reviewCard);

        ownerRatingBar = (MaterialRatingBar) findViewById(R.id.ownerRatingBar);
        listingRatingBar = (MaterialRatingBar) findViewById(R.id.listingRatingBar);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        reviewTitleEditText = (EditText) findViewById(R.id.reviewTitleEditText);
        submitButton = (Button) findViewById(R.id.submitReviewButton);
        ownerReviewProfPic = (SimpleDraweeView) findViewById(R.id.ownerReviewProfPic);
        listingImageView = (SimpleDraweeView) findViewById(R.id.listingImageView);

        myReview = new Review();
        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
    }

    private void listeners(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReview.setReviewer(myUser.getmNormalizedEmail());
                if(!reviewEditText.getText().toString().isEmpty())
                    myReview.setReviewText(reviewEditText.getText().toString());
                //myReview Listing Image
                //myReview Owner Image
                myReview.setTitle(reviewTitleEditText.getText().toString());
//                myReview.setOwner(listingToReview);
                setReviewDate();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                listingToReview.addReview(myReview);
                myRef.child("listings").child(listingToReview.getListingTitle()).child("reviews").child(myUser.getmNormalizedEmail()).setValue(myReview);
                myRef.child("users").child(listingToReview.getListingOwner()).child("mListings").child(listingToReview.getListingTitle()).child("reviews").child(myUser.getmNormalizedEmail()).setValue(myReview);

                finish();

                Debug.printToast("Booking reviewed!", getApplicationContext());
            }
        });
        RatingBarListener RBListener = new RatingBarListener();
        ownerRatingBar.setOnRatingChangeListener(RBListener);
        listingRatingBar.setOnRatingChangeListener(RBListener);

        /*// CREATE REVIEW

        myReview.setListingImage(listingImageView.getDrawingCache());
        myReview.setOwnerReviewImage(ownerReviewProfPic.getDrawingCache());
        listingToReview.addReview(myReview);*/
    }

    private void setReviewDate(){
        Calendar c = Calendar.getInstance();
        String date = new SimpleDateFormat("MMMM").format(c.getTime()) + " " + c.get(Calendar.YEAR);
        myReview.setDate(date);
    }

    class RatingBarListener implements MaterialRatingBar.OnRatingChangeListener {
        @Override
        public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
            int id = ratingBar.getId();
            if(id == R.id.ownerRatingBar){
                myReview.setOwnerRating(rating);
            }
            if(id == R.id.listingRatingBar){
                myReview.setListingRating(rating);
            }
        }
    }
}