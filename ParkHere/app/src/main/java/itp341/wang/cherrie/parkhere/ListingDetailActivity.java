package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.Card;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;
import itp341.wang.cherrie.parkhere.model.User;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by glarencezhao on 10/24/16.
 */

public class ListingDetailActivity extends AppCompatActivity{

    private TextView listingTitleTextView;
    private TextView listingAddressTextView;
    private TextView priceTextView;
    private TextView ownerTextView;
    private TextView aboutTextView;
    private TextView moreReviewsTextView;
    private TextView selectPaymentTextView;
    private TextView paymentMethodTextView;
    private TextView totalPriceTextView;
    private Button confirmBookingButton;
    private MaterialRatingBar listingRatingBar;
    private MaterialRatingBar ownerRatingBar;
    private TagView categoryTags;
    private SimpleDraweeView reviewProfPicView;
    private TextView reviewUserNameTextView;
    private TextView reviewDateTextView;
    private TextView reviewContentTextView;
    private TextView availibilityTextView;
    private TextView cancellationTextView;

    private List<Listing> myListings;
    private Listing myListing = new Listing();
    private User myUser;

    public static final int SELECT_PAYMENT_REQUEST_CODE = 0;
    public static final String SELECTING_PAYMENT = "Selecting Payment";
    public static final String MORE_REVIEWS_INTENT_KEY = "Sending selected listing to display more reviews";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        Intent i = getIntent();
        if(i.hasExtra(ListingAdapter.LISTING_DETAIL_INTENT_KEY))
            myListing = (Listing)i.getSerializableExtra(ListingAdapter.LISTING_DETAIL_INTENT_KEY);
        else
            myListing = null;

        myUser = ((ParkHereApplication) this.getApplication()).getMyUser();
        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        listingTitleTextView = (TextView)findViewById(R.id.listingTitleTextView);
        listingAddressTextView = (TextView)findViewById(R.id.listingAddressTextView);
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        ownerTextView = (TextView)findViewById(R.id.ownerTextView);
        aboutTextView = (TextView)findViewById(R.id.aboutTextView);
        moreReviewsTextView = (TextView)findViewById(R.id.moreReviewsTextView);
        selectPaymentTextView = (TextView)findViewById(R.id.selectPaymentTextView);
        paymentMethodTextView = (TextView)findViewById(R.id.paymentMethodTextView);
        totalPriceTextView = (TextView)findViewById(R.id.totalPriceTextView);
        confirmBookingButton = (Button)findViewById(R.id.confirmBookingButton);
        listingRatingBar = (MaterialRatingBar)findViewById(R.id.listingRatingBar);
        ownerRatingBar = (MaterialRatingBar)findViewById(R.id.ownerRatingBar);
        reviewProfPicView = (SimpleDraweeView)findViewById(R.id.reviewProfPicView);
        reviewUserNameTextView = (TextView)findViewById(R.id.reviewUserNameTextView);
        reviewDateTextView = (TextView)findViewById(R.id.reviewDateTextView);
        reviewContentTextView = (TextView)findViewById(R.id.reviewContentTextView);
        availibilityTextView = (TextView)findViewById(R.id.availibilityTextView);
        cancellationTextView = (TextView)findViewById(R.id.cancellationTextView);

        //Availibility
        //Set text for availibilityTextView to yes or no
        //if no
            //confirmBookingButton.setEnabled(false);

        populate();
    }

    private void listeners(){
        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookListing();
                Debug.printToast("Booking listing!", getApplicationContext());
                Intent i = new Intent(ListingDetailActivity.this, ListOfBookingsActivity.class);
                startActivity(i);
            }
        });
        ownerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListingDetailActivity.this, UserProfileActivity.class);
                //put owner info in i
                startActivity(i);
            }
        });
        moreReviewsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListingDetailActivity.this, ListOfRatingsActivity.class);
                i.putExtra(MORE_REVIEWS_INTENT_KEY, myListing);
                getIntent().putExtra("LISTING", myListing);
                startActivity(i);
            }
        });
        selectPaymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListingDetailActivity.this, ListOfPaymentsActivity.class);
                i.putExtra(SELECTING_PAYMENT, true);
                startActivityForResult(i, SELECT_PAYMENT_REQUEST_CODE);
            }
        });
        reviewProfPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListingDetailActivity.this, UserProfileActivity.class);
                //pass user object
                startActivity(i);
            }
        });
    }

    private void BookListing() {
        Booking b = new Booking(myListing, myUser.getmNormalizedEmail());

        Card c = new Card();
        c.setBalance(myListing.getPrice());
        c.setListingOwner(myListing.getListingOwner());
        c.setPayer(myUser.getmNormalizedEmail());
        c.setApproved(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("listings").child(b.getOwner().getListingTitle()).child("bookings").child(b.getBookingOwner()).setValue(b);
        myRef.child("users").child(myUser.getmNormalizedEmail()).child("mBookings").child(b.getBookingTitle()).setValue(b);
        myRef.child("transaction-tracker").child(c.getListingOwner()).setValue(c); // HELP
    }

    private void populate(){
        //Populate details
        listingTitleTextView.setText(myListing.getListingTitle());
        listingAddressTextView.setText(myListing.getLocation());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        priceTextView.setText("$" + decimalFormat.format(myListing.getPrice()) + "/hr");
        ownerTextView.setText(myListing.getListingOwner());
        aboutTextView.setText(myListing.getAbout());
        //totalPriceTextView.setText(myListing.getPrice() + "");
        listingRatingBar.setRating(myListing.getAverageRating());
        //ownerRatingBar.setRating(myListing.getOwner().getAverageRating()); //need to fix so we can retrieve owner's rating
        paymentMethodTextView.setText("");
        Review latestReview = myListing.getLatestReview();
        if(latestReview == null){
            reviewUserNameTextView.setText("N/A");
            reviewDateTextView.setText("N/A");
            reviewContentTextView.setText("N/A");
        }
        else{
            reviewUserNameTextView.setText(latestReview.getReviewer());
            //reviewDateTextView.setText(latestReview.getDate);
            reviewContentTextView.setText(latestReview.getReviewText());
        }
        availibilityTextView.setText("Yes");

        if(myListing.getCancellationPolicy() == 0){ // Strict
            cancellationTextView.setText(getResources().getString(R.string.cancel_string_strict));
        } else if (myListing.getCancellationPolicy() == 1){ // Moderate
            cancellationTextView.setText(getResources().getString(R.string.cancel_string_moderate));
        } else if (myListing.getCancellationPolicy() == 2) { // Flexible
            cancellationTextView.setText(getResources().getString(R.string.cancel_string_flexible));
        }

        //To display review user image, same code for listing detail image
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        //SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        //draweeView.setImageURI(uri);

        setCategoryTags();
    }

    private void setCategoryTags(){
        categoryTags = (TagView)findViewById(R.id.categoryTags);
        Tag handicappedTag = new Tag("Handicapped");
        handicappedTag.radius = 10f;
        if(myListing.isHandicapped())
            handicappedTag.layoutColor = getResources().getColor(R.color.handicapped_tag_color);
        else
            handicappedTag.layoutColor = getResources().getColor(R.color.muted_tag_color);
        Tag tandemTag = new Tag("Tandem");
        tandemTag.radius = 10f;
        if(myListing.isTandem())
            tandemTag.layoutColor = getResources().getColor(R.color.tandem_tag_color);
        else
            tandemTag.layoutColor = getResources().getColor(R.color.muted_tag_color);
        Tag coveredTag = new Tag("Covered");
        coveredTag.radius = 10f;
        if(myListing.isCovered())
            coveredTag.layoutColor = getResources().getColor(R.color.covered_tag_color);
        else
            coveredTag.layoutColor = getResources().getColor(R.color.muted_tag_color);
        Tag suvTag = new Tag("SUV");
        suvTag.radius = 10f;
        if(myListing.isSuv())
            suvTag.layoutColor = getResources().getColor(R.color.suv_tag_color);
        else
            suvTag.layoutColor = getResources().getColor(R.color.muted_tag_color);
        categoryTags.addTag(handicappedTag);
        categoryTags.addTag(tandemTag);
        categoryTags.addTag(coveredTag);
        categoryTags.addTag(suvTag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PAYMENT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Debug.printToast("Saved card details", getApplicationContext());
                //save card details
                //paymentMethodTextView.setText("Credit Card or PayPal");
            }
        }
    }
}
