package itp341.wang.cherrie.parkhere;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

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
    private TextView totalPriceTextView;
    private Button confirmBookingButton;
    private RatingBar listingRatingBar;
    private RatingBar ownerRatingBar;
    private TagView categoryTags;

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
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        ownerTextView = (TextView)findViewById(R.id.ownerTextView);
        aboutTextView = (TextView)findViewById(R.id.aboutTextView);
        moreReviewsTextView = (TextView)findViewById(R.id.moreReviewsTextView);
        selectPaymentTextView = (TextView)findViewById(R.id.selectPaymentTextView);
        totalPriceTextView = (TextView)findViewById(R.id.totalPriceTextView);
        confirmBookingButton = (Button)findViewById(R.id.confirmBookingButton);
        listingRatingBar = (RatingBar)findViewById(R.id.listingRatingBar);
        ownerRatingBar = (RatingBar)findViewById(R.id.ownerRatingBar);

        listingRatingBar.setRating((float)3.0);
        listingRatingBar.setIsIndicator(true);
        ownerRatingBar.setRating((float)3.0);
        ownerRatingBar.setIsIndicator(true);

        //To display review user image, same code for listing detail image
        //Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        //SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        //draweeView.setImageURI(uri);

        setCategoryTags();
    }

    private void listeners(){
        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent for confirm
            }
        });
    }

    private void setCategoryTags(){
        categoryTags = (TagView)findViewById(R.id.categoryTags);
        Tag handicappedTag = new Tag("Handicapped");
        handicappedTag.radius = 10f;
        handicappedTag.layoutColor = Color.MAGENTA;
        Tag tandemTag = new Tag("Tandem");
        tandemTag.radius = 10f;
        tandemTag.layoutColor = Color.CYAN;
        categoryTags.addTag(handicappedTag);
        categoryTags.addTag(tandemTag);
    }

}
