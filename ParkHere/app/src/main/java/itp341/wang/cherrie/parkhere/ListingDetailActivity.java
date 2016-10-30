package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.facebook.drawee.view.SimpleDraweeView;

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

    public static final int SELECT_PAYMENT_REQUEST_CODE = 0;
    public static final String SELECTING_PAYMENT = "Selecting Payment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

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

        populate();
    }

    private void listeners(){
        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent for confirm
                Debug.printToast("Confirm button clicked!", getApplicationContext());
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
                //put listing in i
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

    private void populate(){
        //Populate details
        listingTitleTextView.setText("Shrine Habitat Parking");
        listingAddressTextView.setText("701 W 32nd St");
        priceTextView.setText("$11.50");
        ownerTextView.setText("Kanye");
        //aboutTextView.setText("");
        //totalPriceTextView.setText("");
        listingRatingBar.setRating((float)3.0);
        ownerRatingBar.setRating((float)3.0);
        paymentMethodTextView.setText("");
        //To display review user image, same code for listing detail image
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        //SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        //draweeView.setImageURI(uri);
        reviewUserNameTextView.setText("Justin");
        reviewDateTextView.setText("June 2016");
        //reviewContentTextView.setText("");
        availibilityTextView.setText("Yes");
        cancellationTextView.setText("Must book ahead of time by at least 24 hours and so and so");

        setCategoryTags();
    }

    private void setCategoryTags(){
        categoryTags = (TagView)findViewById(R.id.categoryTags);
        Tag handicappedTag = new Tag("Handicapped");
        handicappedTag.radius = 10f;
        handicappedTag.layoutColor = getResources().getColor(R.color.handicapped_tag_color);
        Tag tandemTag = new Tag("Tandem");
        tandemTag.radius = 10f;
        tandemTag.layoutColor = getResources().getColor(R.color.tandem_tag_color);
        Tag coveredTag = new Tag("Covered");
        coveredTag.radius = 10f;
        coveredTag.layoutColor = getResources().getColor(R.color.covered_tag_color);
        Tag suvTag = new Tag("SUV");
        suvTag.radius = 10f;
        suvTag.layoutColor = getResources().getColor(R.color.suv_tag_color);
        //if the category is not used, switch to R.color.muted_tag_color
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
