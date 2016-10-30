package itp341.wang.cherrie.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by glarencezhao on 10/30/16.
 */

public class UserProfileActivity extends AppCompatActivity {

    private SimpleDraweeView ownerProfPic;
    private TextView ownerNameTextView;
    private MaterialRatingBar ownerRatingBar;
    private TextView reviewsLinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        initialize();
        listeners();
    }

    private void initialize(){
        getSupportActionBar().hide();

        ownerProfPic = (SimpleDraweeView) findViewById(R.id.ownerProfPic);
        ownerNameTextView = (TextView) findViewById(R.id.ownerNameTextView);
        ownerRatingBar = (MaterialRatingBar) findViewById(R.id.ownerRatingBar);
        reviewsLinkTextView = (TextView) findViewById(R.id.reviewsLinkTextView);

        //change picture
        //change first name
        ownerNameTextView.setText("Glarence");
        //set rating
        ownerRatingBar.setRating((float)3.5);
    }

    private void listeners(){
        reviewsLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, ListOfRatingsActivity.class);
                startActivity(i);
            }
        });
    }
}
